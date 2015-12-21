package kz.processing.cnp.epay.merchant.service.config;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Returns configuration objects
 */
public class ConfigFactory {
	/** The log to use */
	private static final Logger LOG = LoggerFactory.getLogger(ConfigFactory.class);
	
	private static final String CONFIG_FILE = "configs/config.properties";
	private static Properties properties = new Properties();

	static {
		InputStream inStream = null;
		try {
			inStream = ConfigFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
			properties.load(inStream);
			Set<Object> keys = properties.keySet();
			for(Object key : keys) {
				LOG.info("{} = {}", key, properties.getProperty((String) key));
			}
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (inStream != null) 
				try { 
					inStream.close(); 
				}catch (Throwable e) { 
					/* Not Used */ 
				}
		}
	}
	
	public static String getProperty(String key) {
		if(properties.containsKey(key)) {
			return properties.getProperty(key);
		}
		
		return null;
	}
}
