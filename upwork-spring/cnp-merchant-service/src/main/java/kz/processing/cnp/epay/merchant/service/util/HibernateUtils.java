package kz.processing.cnp.epay.merchant.service.util;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kz.processing.cnp.epay.merchant.service.config.ConfigFactory;
import kz.processing.cnp.epay.merchant.service.config.HibernateSessionFactory;
import kz.processing.cnp.epay.model.util.KeyValuePair;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper methods for DB access
 */
public class HibernateUtils {
	/** Logger in use */
	private static final Logger LOG = LoggerFactory.getLogger(HibernateUtils.class);
	private static Session session = null;
	private static Connection dbConnection;
	private static long sessionCreatedTime = 0;
	private static long hibernateSessionAliveSeconds;

	static {
		hibernateSessionAliveSeconds = Long.parseLong(ConfigFactory.getProperty("hibernate.session.alive.seconds"));
		getSession();
	}
	
	/**
	 * Returns all objects of the specified type
	 * @param <T> The type of object to find
	 * @param objectClass The type of object to find
	 * @return All the objects of the specified type
	 */
	public static <T> ArrayList<T> getObjects(Class<T> objectClass) {
		return getObjects(objectClass, "from " + objectClass.getName());
	}
	/**
	 * Returns all objects of the specified type using the specified query
	 * @param <T> The type of object to find
	 * @param objectClass The type of object to find
	 * @param queryString The query that's to be used to find the object
	 * @return All the objects of the specified type
	 */
	public static <T> ArrayList<T> getObjects(final Class<T> objectClass, final String queryString,
			final KeyValuePair ... keyValues) {
		ArrayList<T> retval=new ArrayList<T>(0);
		org.hibernate.Transaction transaction = null;
		try {
			Session session = getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(queryString);
			for (int i = 0; i < keyValues.length; i++) {
				query.setParameter(keyValues[i].getKey(), keyValues[i].getValue());
			}
			List<?> dataFromDB = query.list();
			retval = new ArrayList<T>(dataFromDB.size());
			for (int i = 0; i < dataFromDB.size(); i++) {
				retval.add(extracted(dataFromDB.get(i), objectClass));
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				try { 
					transaction.rollback(); 
				} catch (Exception e1) { 
					LOG.error(e.getMessage(),e); 
				}
			}
			LOG.warn(e.getMessage(), e);
		}
		return retval;
	}

	/**
	 * Just casts to the strongly typed version
	 * @param <T> The type we hope this is
	 * @param dataFromDB The data to convert
	 * @param objectClass the type we hope is in the list
	 * @return The cast object
	 */
	@SuppressWarnings("unchecked")
	private static <T> T extracted(Object dataFromDB, Class<T> objectClass) {
		return (T) dataFromDB;
	}

	public synchronized static Connection getDBConnetion() {
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				dbConnection = connection;
			}
		});

		return dbConnection;
	}

	/**
	 * Returns the object with the specified ID
	 * @param <T> The type of object to find
	 * @param objectID The id of the object to find
	 * @return All the objects of the specified type
	 */
	public static <T> T getObject(Class<T> objectClass, Serializable objectID) {
		T retval=null;
		org.hibernate.Transaction transaction = null;
		try {
			Session session = getSession();

			transaction = session.beginTransaction();
			retval = extracted(session.get(objectClass, objectID), objectClass);
			transaction.commit();
		} catch (Exception e) {
			if (transaction!=null) {
				try { transaction.rollback(); } catch (Exception e1) { LOG.error(e.getMessage(),e); }
			}
			LOG.warn(e.getMessage());
		}
		return retval;
	}

	/**
	 * Attempts to save the specified parameter
	 * @param param the object to save
	 * @return The saved object
	 */
	public static Serializable save(Serializable param) throws IOException {
		Session session=null;
		try {
			session = getSession();
		} catch (Exception e) {
			LOG.warn(e.getMessage(), e);
			throw new IOException("Unable to save object: "+e.getMessage(), e);
		}
		org.hibernate.Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Serializable result = session.save(param);
			transaction.commit();
			return result;
		} catch (HibernateException e) {
			if (transaction!=null) {
				try { transaction.rollback(); } catch (Exception e1) { LOG.error(e.getMessage(),e); }
			}
			LOG.warn(e.getMessage());
			throw e;
		}
	}

	/**
	 * Attempts to update the specified parameter with the new data in the database
	 * @param param the object to save
	 */
	public static void update(final Serializable param) {
		Session session = null;
		try {
			session = getSession();
		} catch (Exception e) {
			LOG.error("Unable to update object: {}", e.getMessage(), e);
		}
		org.hibernate.Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(param);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction!=null) {
				try { 
					transaction.rollback(); 
				} catch (Exception e1) {
					LOG.error(e.getMessage(),e);
				}
			}
			LOG.warn(e.getMessage());
			throw e;
		}
	}

	/**
	 * Attempts to merge the specified parameter with the new data in the database
	 * @param param the object to save
	 */
	public static void merge(final Serializable param) throws Exception {
		Session session=null;
		try {
			session = getSession();
		} catch (Exception e) {
			LOG.warn(e.getMessage(), e);
			throw new IOException("Unable to update object: "+e.getMessage(), e);
		}
		org.hibernate.Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.merge(param);
			transaction.commit();
			session.evict(param);
		} catch (JDBCConnectionException e) {
			LOG.debug("rebuild");
			HibernateSessionFactory.rebuildSessionFactory();
			session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			session.merge(param);
			transaction.commit();
			session.evict(param);
		} catch (HibernateException e) {
			if (transaction!=null) {
				try { transaction.rollback(); } catch (Exception e1) { LOG.error(e.getMessage(),e); }
			}
			LOG.warn(e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * Attempts to delete the specified object
	 * @param param The object to delete
	 */
	public static void delete(final Serializable param) {
		Session session = null;
		try {
			session = getSession();
		} catch (Exception e) {
			LOG.error("Unable to delete object: {}", e.getMessage(), e);
		}
		org.hibernate.Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(param);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction!=null) {
				try { 
					transaction.rollback(); 
				} catch (Exception e1) { 
					LOG.error(e.getMessage(),e); 
				}
			}
			LOG.warn(e.getMessage());
			throw e;
		}
	}

	/**
	 * Attempts to run the specified query
	 * @param query The query to run
	 * @param keyValuePairs Any parameters
	 */
	public static void runQueryUpdate(final String query, KeyValuePair[] keyValuePairs){
		Session session = null;
		try {
			session = getSession();
		} catch (Exception e) {
			LOG.error("Unable to run query: {}", e.getMessage(), e);
		}
		org.hibernate.Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query myQuery = session.createQuery(query);
			for (KeyValuePair kvp : keyValuePairs) {
				myQuery.setParameter(kvp.getKey(), kvp.getValue());
			}
			myQuery.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction!=null) {
				try { transaction.rollback(); } catch (Exception e1) { LOG.error(e.getMessage(),e); }
			}
			LOG.warn(e.getMessage());
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Object[]> executeSql(String sql){

		List<Object[]> rows = null;
		try {
			rows = session.createSQLQuery(sql).list();
		} catch (JDBCConnectionException e) {
			LOG.error("SessionFactory fault! Error: {}", e.getMessage(), e.getCause());
			HibernateSessionFactory.rebuildSessionFactory();
			rows = HibernateSessionFactory.getSession().createSQLQuery(sql).list();
		}

		return rows; 
	}

	public static Session getSession() {
		if(session == null) {
			session = HibernateSessionFactory.getSession();
			sessionCreatedTime = (new Date()).getTime();
		} else if(((new Date()).getTime() - sessionCreatedTime) > hibernateSessionAliveSeconds) {
			session.close();
			session = HibernateSessionFactory.getNewSession();
			sessionCreatedTime = (new Date()).getTime();
		}
		
		return session;
	}

	public static void flushSession() {
		if(session == null) {
			getSession();
		}

		session.flush();
	}

	public static void clearSession() {
		if(session == null) {
			getSession();
		}

		session.clear();
	}
}
