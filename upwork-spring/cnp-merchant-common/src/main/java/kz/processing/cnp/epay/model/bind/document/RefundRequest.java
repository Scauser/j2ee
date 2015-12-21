package kz.processing.cnp.epay.model.bind.document;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kz.processing.cnp.epay.model.bind.MerchantSign;

@XmlRootElement(name = "document")
public class RefundRequest {
	
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
		private Command command = new Command();
		private Payment payment = new Payment();
		private String reason;
		
		@XmlAttribute(name = "id")
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		@XmlElement
		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
		@XmlElement
		public Payment getPayment() {
			return payment;
		}

		public void setPayment(Payment payment) {
			this.payment = payment;
		}
		
		@XmlElement
		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		@XmlRootElement(name = "command")
		public static class Command {
			private String type;

			@XmlAttribute
			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}			
		}
		
		@XmlRootElement(name = "payment")
		public static class Payment {
			private String amount;
			private String approvalCode;
			private String reference;
			private String orderId;
			private String currencyCode;
			
			@XmlAttribute(name = "orderid")
			public String getOrderId() {
				return orderId;
			}
			public void setOrderId(String orderId) {
				this.orderId = orderId;
			}
			@XmlAttribute(name = "amount")
			public String getAmount() {
				return amount;
			}
			public void setAmount(String amount) {
				this.amount = amount;
			}
			@XmlAttribute(name = "approval_code")
			public String getApprovalCode() {
				return approvalCode;
			}
			public void setApprovalCode(String approvalCode) {
				this.approvalCode = approvalCode;
			}
			@XmlAttribute(name = "reference")
			public String getReference() {
				return reference;
			}
			public void setReference(String reference) {
				this.reference = reference;
			}
			@XmlAttribute(name = "currency_code")
			public String getCurrencyCode() {
				return currencyCode;
			}
			public void setCurrencyCode(String currencyCode) {
				this.currencyCode = currencyCode;
			}
		}
	}
}
