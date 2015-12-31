package com.fdmgroup.task1.bank.account;

public class SavingsAccount extends BankAccount {

	private Double interestRate = 0.0; // initialize rate is 0.0

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
}
