package kz.processing.cnp.epay.model.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "payment")
public class Payment {
	private String secure;
	private String amount;
	private String approvalCode;
	private String merchantId;
	private String reference;
	private String responseCode;
	private String payment;
	private String status;
	private String result;
	private String cardmask;
	private String cardhash;
	private String currencyCode;
	
	@XmlAttribute(name = "secure")
	public String getSecure() {
		return secure;
	}
	
	public void setSecure(String secure) {
		this.secure = secure;
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
	
	@XmlAttribute(name = "merchant_id")
	public String getMerchantId() {
		return merchantId;
	}
	
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@XmlAttribute(name = "reference")
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@XmlAttribute(name = "response_code")
	public String getResponseCode() {
		return responseCode;
	}
	
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	@XmlAttribute(name = "payment")
	public String getPayment() {
		return payment;
	}
	
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	@XmlAttribute(name = "status")
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@XmlAttribute(name = "result")
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	@XmlAttribute(name = "cardmask")
	public String getCardmask() {
		return cardmask;
	}
	
	public void setCardmask(String cardmask) {
		this.cardmask = cardmask;
	}
	
	@XmlAttribute(name = "cardhash")
	public String getCardhash() {
		return cardhash;
	}
	
	public void setCardhash(String cardhash) {
		this.cardhash = cardhash;
	}
	
	@XmlAttribute(name = "currencycode")
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
