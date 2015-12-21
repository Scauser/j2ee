package kz.processing.cnp.epay.model.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response {	
	private String payment;
	private String status;
	private String result;
	private String amount;
	private String timestamp;
	// bank rrn
	private String reference;
	private String cardmask;
	private String cardhash;
	private String secure;
	private String approvalCode;
	private String currencyCode;
	// cnp rrn
	private String customerReference;
	
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
	@XmlAttribute(name = "timestamp")
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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
	@XmlAttribute(name = "customer_reference")
	public String getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
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
