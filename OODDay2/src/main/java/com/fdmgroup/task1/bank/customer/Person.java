package com.fdmgroup.task1.bank.customer;

import com.fdmgroup.task1.bank.account.BankAccount;

public class Person extends BankCustomer {

	public void addAccount(BankAccount account) {
		// check accountType if account is Business account show error
		if(account != null && BankAccount.ACCOUNT_TYPE_BUSINESS.equals(account.getAccountType())) {
			System.out.println("ERROR: Business Account can't belong to Person!!");
			return;
		}
		// change account type to personal
		account.setAccountType(BankAccount.ACCOUNT_TYPE_PERSONAL);
		// set customer to this instance it means account and Person will belong each other
		account.setCustomer(this);
		this.accounts.add(account);
	}
	
	public void resetAllAccounts() {
		for(BankAccount account : accounts) {
			account.changeBalance(BankAccount.CORRECTION, 0.0);
		}
	}
}
