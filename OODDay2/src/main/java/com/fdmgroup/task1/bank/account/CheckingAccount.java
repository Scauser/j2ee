package com.fdmgroup.task1.bank.account;

public class CheckingAccount extends BankAccount {

	private Integer nextCheckNumber = 0; // initialize next number is 0
	
	public void nextCheck() {
		this.nextCheckNumber++;
	}
	
	public Integer getNextCheckNumber() {
		return nextCheckNumber;
	}
}
