package com.fdmgroup.task1.bank.customer;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.task1.bank.account.BankAccount;

public class BankCustomer {

	private static Integer lastUniqueId = 1999993;

	private Integer id;
	private String name;
	private String address;
	private String taxIdNumber;
	protected List<BankAccount> accounts = new ArrayList<BankAccount>();

	public BankCustomer() {
		lastUniqueId += 7;
		this.id = lastUniqueId;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTaxIdNumber() {
		return taxIdNumber;
	}

	public void setTaxIdNumber(String taxIdNumber) {
		this.taxIdNumber = taxIdNumber;
	}
	
	public List<BankAccount> getAccounts() {
		return accounts;
	}
}
