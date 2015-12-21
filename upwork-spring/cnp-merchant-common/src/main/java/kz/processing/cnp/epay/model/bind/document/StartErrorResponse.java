package kz.processing.cnp.epay.model.bind.document;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "response")
public class StartErrorResponse {
	
	private String orderId;
	private Error error = new Error();
	private Session session = new Session();

	@XmlAttribute(name = "order_id")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@XmlElement
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@XmlElement
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public static class Error {
		
		private String type; //system; auth
		private String time; // yyyy-MM-dd hh:mm:ss 
		private String code; // system - 99
		private String error;
		
		@XmlAttribute
		public String getType() {
			return type;
		}
		
		public void setType(String type) {
			this.type = type;
		}
		
		@XmlAttribute
		public String getTime() {
			return time;
		}
		
		public void setTime(String time) {
			this.time = time;
		}
		
		@XmlAttribute
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		@XmlValue
		public String getError() {
			return error;
		}
	
		public void setError(String error) {
			this.error = error;
		}
	}

	public static class Session {
		
		private String id;
		
		@XmlAttribute
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}
}
