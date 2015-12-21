package kz.processing.cnp.epay.model.bind.document;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kz.processing.cnp.epay.model.bind.BankSign;
import kz.processing.cnp.epay.model.bind.Response;

@XmlRootElement(name = "document")
public class GetStatusResponse {

	private Bank bank = new Bank();
	private BankSign bankSign = new BankSign();

	@XmlElement(name = "bank")
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@XmlElement(name = "bank_sign")
	public BankSign getBankSign() {
		return bankSign;
	}

	public void setBankSign(BankSign bankSign) {
		this.bankSign = bankSign;
	}

	@XmlRootElement(name = "bank")
	public static class Bank {
		private String callCenter;
		private String name;
		private Merchant merchant = new Merchant();
		private Response response = new Response();

		@XmlAttribute(name = "callcenter")
		public String getCallCenter() {
			return callCenter;
		}

		public void setCallCenter(String callCenter) {
			this.callCenter = callCenter;
		}

		@XmlAttribute(name = "name")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public Merchant getMerchant() {
			return merchant;
		}

		public void setMerchant(Merchant merchant) {
			this.merchant = merchant;
		}
		
		public Response getResponse() {
			return response;
		}

		public void setResponse(Response response) {
			this.response = response;
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

			public static class Order {
				private String id;
				private String amount;
				private String currency;

				@XmlAttribute(name = "id")
				public String getId() {
					return id;
				}

				public void setId(String id) {
					this.id = id;
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
			}
		}
	}
}
