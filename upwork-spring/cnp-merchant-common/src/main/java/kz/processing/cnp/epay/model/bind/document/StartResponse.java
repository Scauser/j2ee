package kz.processing.cnp.epay.model.bind.document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kz.processing.cnp.epay.model.bind.Bank;
import kz.processing.cnp.epay.model.bind.BankSign;

@XmlRootElement(name = "document")
public class StartResponse {
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
}
