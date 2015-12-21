package kz.processing.cnp.epay.model.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bank")
public class Bank {
	private String callCenter;
	private String name;
	private Customer customer = new Customer();
	private CustomerSign customerSign = new CustomerSign();
	private Result result;
	
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
	
	@XmlElement(name = "customer")
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@XmlElement(name = "customer_sign")
	public CustomerSign getCustomerSign() {
		return customerSign;
	}
	
	public void setCustomerSign(CustomerSign customerSign) {
		this.customerSign = customerSign;
	}

	@XmlElement(name = "results")
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
