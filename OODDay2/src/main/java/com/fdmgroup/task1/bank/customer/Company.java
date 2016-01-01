package com.fdmgroup.task1.bank.customer;

import com.fdmgroup.task1.bank.account.BankAccount;

public class Company extends BankCustomer {

	public void addAccount(BankAccount account) {
		// check accountType if account is Personal account show error
		if(account != null && BankAccount.ACCOUNT_TYPE_PERSONAL.equals(account.getAccountType())) {
			System.out.println("ERROR: Personal Account can't belong to Company!!");
			return;
		}
		// change account type to business
		account.setAccountType(BankAccount.ACCOUNT_TYPE_BUSINESS);
		// set customer to this instance it means account and Company will belong each other
		account.setCustomer(this);
		this.accounts.add(account);
	}

	public void addAmountToAllAccounts(Double amount) {
		for(BankAccount account : accounts) {
			account.changeBalance(BankAccount.DEPOSIT, amount);
		}
	}
}
