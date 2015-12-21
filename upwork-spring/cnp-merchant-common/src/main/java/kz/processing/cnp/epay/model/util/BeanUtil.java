package kz.processing.cnp.epay.model.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class BeanUtil {

	public static String dump(Object bean) {
		return "\r\n" + dump(bean, "");
	}

	private static String dump(Object bean, String indent) {		
		if( bean == null) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		Class<?> clazz = bean.getClass() ;

		try {
			buffer.append("[").append(clazz.getSimpleName()).append("]\r\n");
			Method []methods = clazz.getDeclaredMethods() ;
			for(Method method : methods) {
				String methodName = method.getName();
				if((methodName.startsWith("get") || methodName.startsWith("is")) 
						&& !methodName.contains("Specified")) {
					Object retVal = method.invoke(bean, new Object[]{});
					if( retVal instanceof java.lang.String) {
						buffer.append(indent).append("[").append(methodName.replace("get", "")).append("]  = ").append((String)retVal).append("\r\n");
					} else if(retVal instanceof Boolean) {
						if(methodName.contains("is") && !methodName.contains("get")) {
							buffer.append(indent).append("[").append(methodName.replace("is", "")).append("]  = ").append((Boolean)retVal).append("\r\n");
						} else if(methodName.contains("get")) { 
							buffer.append(indent).append("[").append(methodName.replace("get", "")).append("]  = ").append((Boolean)retVal).append("\r\n");
						}
					} else if(retVal instanceof Integer || retVal instanceof Float || retVal instanceof Double || retVal instanceof Long) {
						buffer.append(indent).append("[").append(methodName.replace("get", "")).append("]  = ").append(retVal.toString()).append("\r\n");
					} else if ( retVal instanceof Object[]) {
						buffer.append(indent).append("[").append(methodName.replace("get", "")).append("]\r\n");
						for ( int i=0; i < Array.getLength(retVal); i++ ) {
							Object value = ((Object[])retVal)[i];
							buffer.append(dump(value, "  "));
						}
					} else {
						if( retVal == null ) {
							buffer.append("[").append(methodName.replace("get", "")).append("]  = null").append("\r\n");
							continue ;
						}
						buffer.append(dump(retVal, "  "));
					}
				} 
			}
		} catch(Exception ex) { }
		return buffer.toString();
	}
}
