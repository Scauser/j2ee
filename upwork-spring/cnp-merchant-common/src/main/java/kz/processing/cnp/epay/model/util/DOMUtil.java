package kz.processing.cnp.epay.model.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DOMUtil.class.getName());

	public static Document createDOMFromString(String xml) {
		if(xml == null) {
			return null;
		}
		Document document = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
			document.getDocumentElement().normalize();
		} catch (Exception ex) {
			LOG.error("error when create DOM: {}", ex.getMessage(), ex.getCause());
		}

		return document;
	}

	public static Element getElementByTagName(String tagName, Document document) {
		if(tagName == null || document == null) {
			return null;
		}
		NodeList nList = document.getElementsByTagName(tagName);
		for (int i = 0; i < nList.getLength(); i++) {
			if (nList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				return (Element) nList.item(i);
			}
		}
		return null;
	}

	public static String getStringFromDOM(Node node) {
		if(node == null) {
			return null;
		}
		try {
			DOMSource domSource = new DOMSource(node);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty("omit-xml-declaration", "yes");
			transformer.transform(domSource, result);
			return writer.toString();
		} catch (TransformerException ex) {
			LOG.error("error occured when transform from DOM to string: {}" + ex.getMessage());
			return null;
		}
	}
}
