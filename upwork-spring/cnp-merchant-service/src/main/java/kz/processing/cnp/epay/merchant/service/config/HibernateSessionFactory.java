package kz.processing.cnp.epay.merchant.service.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.lf5.util.Resource;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a class that is used to ensure that access to the hibernate system is
 * accessed centrally and that the hibernate application is initialised correctly
 * prior to its use.
 * It extends the google code so that it can be used as the interceptor but still manage
 * to ensure users are created correctly on initialization
 * 
 * @author Christopher Dawes
 * @version 1.0
 * @created 27-Nov-2009 10:31:02
 */
public class HibernateSessionFactory {
	/** Has the utility been initialised successfully? */
	private static boolean initialized=false;

	/**
	 * '/hibernate.cfg.xml' file default location
	 */
	public static final String DEFAULT_HIBERATE_CONFIGFILE = "/hibernate.cfg.xml";
	/** The default factory to use */
	private static String defaultSessionFactory = null;
	/** The map of threads to session factories */
	private static Map<String, SessionFactory> factoriesMap = new LinkedHashMap<String, SessionFactory>();
	/** The config file in use */
	private static String configFiles = DEFAULT_HIBERATE_CONFIGFILE;
	/** Map of threads to sessions */
	private static ThreadLocal<Map<String,Session>> sessionsMap = new ThreadLocal<Map<String,Session>>();
	/** Auto-reconnect on error */
	private static boolean autoRebuidOnJdbcConnectionError = true;
	/** Logger in use */
	private static final Logger LOG = LoggerFactory.getLogger(HibernateSessionFactory.class);
	/** A secondary database connection used for requests directly to the DB */
	private static Connection databaseSecondaryConnection;

	private static ServiceRegistry serviceRegistry;

	/**
	 * Returns a new Hibernate Core Session or the Session of the current thread (Current HTTP request).<br/>
	 * If more than One SessionFactory is configured, the default SessionFactory if used as source.
	 * @return
	 * @throws LoadSessionFromFactoryException 
	 * @throws BuildSessionFactoryException 
	 * @throws Exception
	 */
	public synchronized static Session getSession() {
		return getSession(defaultSessionFactory);
	}

	/**
	 * Returns a new Hibernate Core Session or the Session from the current thread (Current HTTP request)
	 * @param sessionFactoryName Name of the source SessionFactory  (the same from <i>&ltsession-factory&gt</i> tag of a Hibernate configuration file).If <b>null</b>, the default SessionFactory is returned.
	 * @return A Hibernate Core Session
	 * @throws BuildSessionFactoryException 
	 * @throws LoadSessionFromFactoryException 
	 * @throws Exception
	 */
	public synchronized static Session getSession(String sessionFactoryName) {
		//		LOG.debug("Hibernate Session Required (from current Thread) - SessionFactory required: "+( ( (sessionFactoryName == null) || (sessionFactoryName.equals("") ) ? "(default)":"\"" + sessionFactoryName + "\"")));
		Session session = null; 
		Map<String,Session> currentMap = sessionsMap.get();

		// the current sessions map does not exists
		if (currentMap == null) {
			//			LOG.debug("No Hibernate Session in current thread. New Hibernate Session will be created and returned (SessionFactory \""+( ( (sessionFactoryName==null) || (sessionFactoryName.equals("") )?"(default)":"\""+sessionFactoryName+"\""))+"\")");
			session = getNewSession(sessionFactoryName);

			Map<String,Session> newSessionMap = new LinkedHashMap<String, Session>();

			// at the first call from getNewSession() this test is need
			if (sessionFactoryName == null) {
				sessionFactoryName = defaultSessionFactory;
			}
			newSessionMap.put(sessionFactoryName, session);
			sessionsMap.set(newSessionMap);

		} else { //the current sessions map exists
			if (sessionFactoryName == null) {
				sessionFactoryName = "";
			}
			session = currentMap.get(sessionFactoryName);
			// no Session from the required SessionFactory
			if (session == null) {
				//					LOG.debug("Hibernate Session required from current thread but the SessionFactory from the Session of the current thread is not the same of the required SessionFactory (\""+sessionFactoryName+"\")");
				session = getNewSession(sessionFactoryName);
				currentMap.put(sessionFactoryName, session);
			}
		}
		return session;
	}


	/**
	 * Returns a new Hibernate Core Session, NOT the Session from the current thread (Current HTTP request). <br/>
	 * If more than One SessionFactory is configured, the default SessionFactory if used as source to return the New Session.
	 * @return
	 * @throws LoadSessionFromFactoryException 
	 * @throws BuildSessionFactoryException 
	 * @throws Exception
	 */
	public static Session getNewSession() {
		return getNewSession(defaultSessionFactory);
	}
	/**
	 * Returns a new Hibernate Core Session, NOT the Session of the current thread (Current HTTP request).
	 * <br/>The plugin <b>does not commit</b> the transactions of this Session instance.
	 * @param sessionFactoryName Name of the source SessionFactory (the same from <i>&ltsession-factory&gt</i> tag of a Hibernate configuration file).If <b>null</b>, the default SessionFactory is used to return the Session)
	 * @return A Hibernate Core Session
	 * @throws BuildSessionFactoryException 
	 * @throws LoadSessionFromFactoryException 
	 * @throws Exception
	 */
	public static Session getNewSession(String sessionFactoryName) {
		//			LOG.debug("New Hibernate Session required - SessionFactory required: "+( ( (sessionFactoryName==null) || (sessionFactoryName.equals("") )?"(default)":"\""+sessionFactoryName+"\"")));
		if (factoriesMap.isEmpty()) {
			rebuildSessionFactory();
		}

		// at the first call from getNewSession() this test is need
		if (sessionFactoryName == null)
			sessionFactoryName = defaultSessionFactory;

		if ( (sessionFactoryName.equals("")) && (!factoriesMap.containsKey("")) )
			sessionFactoryName = defaultSessionFactory;

		try{	
			Session session = getSessionFactory(sessionFactoryName).openSession();
			session.setFlushMode(FlushMode.ALWAYS);
			//				LOG.debug("New Hibernate Session created and returned (SessionFactory \""+sessionFactoryName+"\")");
			return session;
		} catch (Exception e) {
			LOG.error("Could not load Hibernate Session from Full Hibernate Plugin's Session Factory: {}", e.getMessage(), e);
			return null;
		}

	}

	/**
	 * Close all Hibernate Sessions from the current thread (Current HTTP request) 
	 */
	public static void closeSession() {
		closeSessions();
	}

	/**
	 * Close all Hibernate Sessions from the current thread (Current HTTP request)
	 */
	@SuppressWarnings("unchecked")
	public static void closeSessions() {
		LOG.debug("closeSessions()");
		if (sessionsMap == null) {
			return;
		}
		if (sessionsMap.get() == null){
			return;
		}
		if (sessionsMap.get().values() == null) {
			return;
		}

		for (int i = 0; i< sessionsMap.get().values().size(); i++) {
			Session session = (Session) ((Hashtable<Object, Object>) sessionsMap.get().values()).get(i);
			if ( (session!=null) && (session.isOpen()) ) {
				session.close();
			}

		}
		sessionsMap.set(null);
	}

	/**
	 * Close the Hibernate Session from parameter
	 * @param session Hibernate Session to be closed
	 */
	public static void closeSession(Session session) {
		if (session.isOpen()) {
			session.close();	
		}
	}

	/**
	 * Reload the session factories configurations according the configuration files
	 * @throws BuildSessionFactoryException 
	 * @throws Exception
	 */
	public synchronized static void rebuildSessionFactory() {
		try {
			finishUnitOfWork();
			if (!factoriesMap.isEmpty()) {
				destroyFactory();
			}
			String[] files = configFiles.split(",");
			if (files.length == 0) {
				throw new Exception("No configuration file for Hibernate");
			}
			for (String file:files) {
				Configuration configuration = new Configuration();
				configuration.configure(file);
				String fileProperties = file.substring(1, file.length()).replace(".cfg", "").replace("xml", "properties");
				Resource resource = new Resource(fileProperties);
				if (resource.getURL()!=null) {
					String fullpath = resource.getURL().getPath().replace("%20", " ");
					FileReader reader = new FileReader(fullpath);
					Properties properties = new Properties();
					properties.load(reader);
					reader.close();
					configuration.addProperties(properties);
				}
				serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
						configuration.getProperties()).build();

				SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				verifyInitialized(sessionFactory);

				Field nameField = sessionFactory.getClass().getDeclaredField("name");
				nameField.setAccessible(true);
				String sessionFactoryName = (String)nameField.get(sessionFactory);
				if (sessionFactoryName == null) {
					sessionFactoryName = "";
				}
				factoriesMap.put(sessionFactoryName, sessionFactory);
				if (defaultSessionFactory == null) {
					setDefaultSessionFactory(sessionFactoryName);
				}
			}
		} catch (Exception e) {
			factoriesMap.clear();
			LOG.error(e.getMessage(), e.getCause());
		}
	}

	/**
	 * Gets the secondary database connection
	 */
	public synchronized static Connection getDBConnetion() {
		if (databaseSecondaryConnection != null) {
			try {
				if (!databaseSecondaryConnection.isClosed()) {
					return databaseSecondaryConnection;
				}
			} catch (SQLException e) {
				LOG.debug(e.getMessage(), e.getCause());
			}
			try { 
				databaseSecondaryConnection.close(); 
			} catch (Throwable e) { /* Ignore */ } 
		}
		try {
			String[] files = configFiles.split(",");
			if (files.length == 0)
				throw new SQLException("No configuration file for Hibernate");
			for (String file:files) {
				Configuration configuration = new Configuration();
				configuration.configure(file);

				String fileProperties = file.substring(1,file.length()).replace(".cfg", "").replace("xml", "properties");
				Resource resource = new Resource(fileProperties);
				if (resource.getURL()!=null) {
					String fullpath = resource.getURL().getPath().replace("%20", " ");
					FileReader reader = new FileReader(fullpath);
					Properties properties = new Properties();
					properties.load(reader);
					reader.close();
					configuration.addProperties(properties);
				}

				serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
						configuration.getProperties()).build();

				Session session = configuration.buildSessionFactory(serviceRegistry).getCurrentSession();

				session.doWork(new Work() {
					@Override
					public void execute(Connection connection) throws SQLException {
						databaseSecondaryConnection = connection;
					}
				});
				break;
			}
			return databaseSecondaryConnection;
		} catch (SQLException e) {
			LOG.error("error trying to load hibernate config: {}", e.getMessage(), e);
		} catch (FileNotFoundException e) {
			LOG.error("error trying to load hibernate config: {}", e.getMessage(), e);
		} catch (IOException e) {
			LOG.error("error trying to load hibernate config: {}", e.getMessage(), e);
		}

		return null;
	}

	/**
	 * In Web Applications with Hibernate, when the context is reload, it's necessary to destroy the session factories to evict problems with connection pools, caches, and other third part Hibernate tools 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public synchronized static void destroyFactory() {
		defaultSessionFactory=null;
		closeAllSessionFactoriesAndClearMap();
		try {
			Class<?> utilClass = Class.forName("com.googlecode.s2hibernate.struts2.plugin.util.C3P0Util", false, HibernateSessionFactory.class.getClassLoader());
			Method method = utilClass.getDeclaredMethod("closePolledDataSources");
			method.invoke(utilClass);
		} catch (Exception e) { } 		
	}

	public static void setConfigFiles(String configFiles) {
		if ((configFiles!=null) && (!configFiles.equals(""))) {
			HibernateSessionFactory.configFiles = configFiles;			
		}
		destroyFactory();
	}


	private synchronized static void closeAllSessionFactoriesAndClearMap() {
		for (int i=0; i< factoriesMap.values().size(); i++) {
			@SuppressWarnings("unchecked")
			SessionFactory sessionFactory = (SessionFactory)((Hashtable<Object, Object>) factoriesMap.values()).get(i);
			if ((sessionFactory!=null) && (!sessionFactory.isClosed()) )  {
				sessionFactory.close();
			}
			sessionFactory = null;
		}

		factoriesMap.clear();
	}

	public static String getConfigFiles() {
		return configFiles;
	}

	/**
	 * Get one of the configured SessionFactories
	 * @param sessionFactoryName The SessionFactory name (the same from <i>&ltsession-factory&gt</i> tag of a Hibernate configuration file) 
	 * @return
	 * @throws SessionFactoryNotFoundException 
	 * @throws Exception 
	 */
	public static SessionFactory getSessionFactory(String sessionFactoryName) throws Exception {
		if (factoriesMap.isEmpty()) {
			return null;
		}
		SessionFactory sessionFactory = factoriesMap.get(sessionFactoryName);
		if (sessionFactory == null) {
			throw new Exception("No SessionFactory named \"" + sessionFactoryName + "\" found. You should use " + factoriesMap.keySet());
		}
		return sessionFactory;
	}

	/**
	 * Verifies that the users and roles are initialized
	 */
	private static void verifyInitialized(SessionFactory sessionFactory) {
		if (!initialized) { 
			try {
				initialized=true;
			} catch (Throwable ex) {
				LOG.error(ex.getMessage(), ex.getCause());
				throw new ExceptionInInitializerError(ex);
			}
		}
	}

	/**
	 * Get the state of the SessionFactories 
	 * @return <b>true</b> if there is at leaat One SessionFactory configured
	 */
	public static boolean hasSessionFactories() {
		return (!factoriesMap.isEmpty());
	}

	public static String getDefaultSessionFactory() {
		return defaultSessionFactory;
	}

	public static void setDefaultSessionFactory(String defaultSessionFactory) {
		if (defaultSessionFactory == null) {
			defaultSessionFactory = "";
		}
		HibernateSessionFactory.defaultSessionFactory = defaultSessionFactory;
	}

	public static boolean isAutoRebuidOnJdbcConnectionError() {
		return autoRebuidOnJdbcConnectionError;
	}

	public static void setAutoRebuidOnJdbcConnectionError(
			boolean autoRebuidOnJdbcConnectionError) {
		HibernateSessionFactory.autoRebuidOnJdbcConnectionError = autoRebuidOnJdbcConnectionError;
	}

	/**
	 * Finish the current thread's unit of work
	 */
	public static void finishUnitOfWork() {
		sessionsMap.set(null);
	}
}
