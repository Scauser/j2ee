package kz.processing.cnp.epay.model.util;

/**
 * Represents a key value pair, so a value that is pointed to by a key
 */
public class KeyValuePair {
	/** The data key */
	private String key;
	/** The data value */
	private Object value;
	/**
	 * Default constructor for null values
	 */
	public KeyValuePair() {
		/* Not Used */
	}
	/**
	 * Instantiates a new key value pair
	 * with default values
	 * 
	 * @param key default value for key
	 * @param value default value for value
	 */
	public KeyValuePair(final String key, final Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
}
