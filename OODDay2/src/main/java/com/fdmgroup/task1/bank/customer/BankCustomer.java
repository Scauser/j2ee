package com.fdmgroup.task1.bank.customer;

public class BankCustomer {

	private static Integer lastUniqueId = 1999993;

	private Integer id;
	private String name;
	private String address;
	private String taxIdNumber;

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
}
