package com.fdmgroup.task1.bank.customer;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.task1.bank.account.BankAccount;

public class Company extends BankCustomer {

	private List<BankAccount> accounts = new ArrayList<BankAccount>();

	public void addAccount(BankAccount account) {
		this.accounts.add(account);
	}

	public void resetAllAccounts() {
		for(BankAccount account : accounts) {
			account.changeBalance(BankAccount.CORRECTION, 0.0);
		}
	}

	public List<BankAccount> getAccounts() {
		return accounts;
	}
}
