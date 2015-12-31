package com.fdmgroup.task1;

import com.fdmgroup.task1.bank.account.CheckingAccount;
import com.fdmgroup.task1.bank.account.SavingsAccount;
import com.fdmgroup.task1.bank.customer.Company;

public class Main {

	public static void main(String[] args) {
		CheckingAccount checking = new CheckingAccount();
		System.out.println(checking.getId());
		System.out.println(checking.getNextCheckNumber());
		checking.nextCheck();
		System.out.println(checking.getNextCheckNumber());
		SavingsAccount savings = new SavingsAccount();
		System.out.println(savings.getId());
		Company company1 = new Company();
		System.out.println(company1.getId());
	}
}
