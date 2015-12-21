package kz.processing.cnp.epay.model.bind.document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kz.processing.cnp.epay.model.bind.Merchant;
import kz.processing.cnp.epay.model.bind.MerchantSign;

@XmlRootElement(name = "document")
public class StartRequest {
	private Merchant merchant = new Merchant();
	private MerchantSign merchantSign = new MerchantSign();
	
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
