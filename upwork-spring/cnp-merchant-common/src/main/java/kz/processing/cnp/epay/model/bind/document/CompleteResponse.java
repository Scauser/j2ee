package kz.processing.cnp.epay.model.bind.document;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kz.processing.cnp.epay.model.bind.BankSign;
import kz.processing.cnp.epay.model.bind.MerchantSign;

@XmlRootElement(name = "document")
public class CompleteResponse {

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
		private String name;
		private CompleteRequest.Merchant merchant = new CompleteRequest.Merchant();
		private MerchantSign merchantSign = new MerchantSign();
		private Response response = new Response();

		@XmlElement(name = "merchant_sign")
		public MerchantSign getMerchantSign() {
			return merchantSign;
		}

		public void setMerchantSign(MerchantSign merchantSign) {
			this.merchantSign = merchantSign;
		}

		@XmlAttribute(name = "name")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public CompleteRequest.Merchant getMerchant() {
			return merchant;
		}

		public void setMerchant(CompleteRequest.Merchant merchant) {
			this.merchant = merchant;
		}

		public Response getResponse() {
			return response;
		}

		public void setResponse(Response response) {
			this.response = response;
		}

		public static class Response {
			private String code;
			private String message;

			@XmlAttribute
			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			@XmlAttribute
			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}
		}
	}
}
