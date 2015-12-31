package com.fdmgroup.task1.bank.account;

import com.fdmgroup.task1.bank.customer.BankCustomer;

public class BankAccount {

	public static final String DEPOSIT = "DEPOSIT";
	public static final String WITHDRAW = "WITHDRAW";
	public static final String CORRECTION = "CORRECTION";
	
	public static final String ACCOUNT_TYPE_BUSINESS = "BUSINESS";
	public static final String ACCOUNT_TYPE_PERSONAL = "PERSONAL";
	
	private static Integer lastUniqueId = 995;
	
	private Integer id;
	private Double balance;
	private BankCustomer customer;
//	private String accountType;
	
	public BankAccount() {
		lastUniqueId += 5;
		this.id = lastUniqueId;
		this.balance = 0.0;
	}
	
	public void changeBalance(String changeType, Double changeValue) {
		if(DEPOSIT.equals(changeType)) { // if change type is Deposit
			balance += changeValue;
		} else if(WITHDRAW.equals(changeType)) { // if change type is Withdraw
			Double newBalance = balance - changeValue; // new balance after changes
			if(this instanceof SavingsAccount && newBalance < 0.0) { // 
				System.out.println("Savings Account cann't be overdrawn!!");
				return;
			} else {
				balance = newBalance;
			}
		} else if(CORRECTION.equals(changeType)) { // if change type is Correction
			if(this instanceof SavingsAccount && changeValue < 0.0) {
				System.out.println("Savings Account cann't be overdrawn!!");
				return;
			} else {
				balance = changeValue;
			}
		}
	}

	public Integer getId() {
		return id;
	}

	public Double getBalance() {
		return balance;
	}
	
	public BankCustomer getCustomer() {
		return customer;
	}
	
	public void setCustomer(BankCustomer customer) {
		this.customer = customer;
	}
	
//	public String getAccountType() {
//		return accountType;
//	}
}
