package kz.processing.cnp.epay.model.bind.document;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kz.processing.cnp.epay.model.bind.MerchantSign;

@XmlRootElement(name = "document")
public class GetStatusRequest {
	
	private Merchant merchant;
	private MerchantSign merchantSign;
	
	@XmlElement(name = "merchant")
	public Merchant getMerchant() {
		return merchant;
	}
	
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@XmlElement(name = "merchant_sign")
	public MerchantSign getMerchantSign() {
		return merchantSign;
	}

	public void setMerchantSign(MerchantSign merchantSign) {
		this.merchantSign = merchantSign;
	}

	@XmlRootElement(name = "merchant")
	public static class Merchant {
		private String id;
		private Order order = new Order();
		
		@XmlAttribute(name = "id")
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@XmlElement(name = "order")
		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		@XmlRootElement(name = "order")
		public static class Order {
			private String id;
			
			@XmlAttribute(name = "id")
			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}
		}
	}
}
