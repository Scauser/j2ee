package kz.processing.cnp.epay.model.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class Customer {
	private String mail;
	private String name;
	private String phone;
	private Merchant merchant = new Merchant();
	private MerchantSign merchantSign = new MerchantSign();
	
	@XmlAttribute
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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
}
