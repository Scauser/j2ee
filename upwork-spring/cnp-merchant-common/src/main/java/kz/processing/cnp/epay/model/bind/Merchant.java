package kz.processing.cnp.epay.model.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "merchant")
public class Merchant {
	private String certId;
	private String name;
	private Order order = new Order();
	
	@XmlAttribute(name = "cert_id")
	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "order")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public static class Order {
		private String orderId;
		private String parentOrderId;
		private String amount;
		private String currency;
		private String purchaserName;
		private Department department = new Department();
		
		@XmlAttribute(name = "order_id")
		public String getOrderId() {
			return orderId;
		}
		
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		
		@XmlAttribute(name = "parent_order_id")
		public String getParentOrderId() {
			return parentOrderId;
		}

		public void setParentOrderId(String parentOrderId) {
			this.parentOrderId = parentOrderId;
		}

		@XmlAttribute(name = "amount")
		public String getAmount() {
			return amount;
		}
		
		public void setAmount(String amount) {
			this.amount = amount;
		}
		
		@XmlAttribute(name = "currency")
		public String getCurrency() {
			return currency;
		}
		
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		
		@XmlElement(name = "department")
		public Department getDepartment() {
			return department;
		}
		
		public void setDepartment(Department department) {
			this.department = department;
		}
		
		@XmlAttribute(name = "purchaser_name")
		public String getPurchaserName() {
			return purchaserName;
		}
		
		public void setPurchaserName(String purchaserName) {
			this.purchaserName = purchaserName;
		}
	}
	
	public static class Department {
		private String merchantId;
		private String amount;
		private String terminal;
		private String kbk;
		private String kno;
		private String knp;
		
		@XmlAttribute(name = "merchant_id")
		public String getMerchantId() {
			return merchantId;
		}
		
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
		
		@XmlAttribute(name = "amount")
		public String getAmount() {
			return amount;
		}
		
		public void setAmount(String amount) {
			this.amount = amount;
		}

		@XmlAttribute(name = "terminal")
		public String getTerminal() {
			return terminal;
		}

		public void setTerminal(String terminal) {
			this.terminal = terminal;
		}

		@XmlAttribute(name = "kbk")
		public String getKbk() {
			return kbk;
		}

		public void setKbk(String kbk) {
			this.kbk = kbk;
		}

		@XmlAttribute(name = "kno")
		public String getKno() {
			return kno;
		}

		public void setKno(String kno) {
			this.kno = kno;
		}

		@XmlAttribute(name = "knp")
		public String getKnp() {
			return knp;
		}

		public void setKnp(String knp) {
			this.knp = knp;
		}
	}
}
