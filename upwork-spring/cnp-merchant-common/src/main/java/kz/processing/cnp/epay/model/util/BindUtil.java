package kz.processing.cnp.epay.model.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BindUtil {
	private static final Logger LOG = LoggerFactory.getLogger(BindUtil.class);
	
	public static String marshall(Object object, Class<?> clazz){
		try {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Marshaller marshaller = context.createMarshaller();
		StringWriter writer = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.marshal(object, writer);
		
		return writer.toString();
		} catch(Exception ex) {
			LOG.error("couldn't marshall object {} class {}", object, clazz.getName(), ex);
			
			return null;
		}
	}
	
	public static Object unmarshall(String xml, Class<?> clazz){
		try {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		return unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		} catch(Exception ex) {
			LOG.error("couldn't unmarshall xml {} to class {}", xml, clazz.getName(), ex);
			
			return null;
		}
	}
}
