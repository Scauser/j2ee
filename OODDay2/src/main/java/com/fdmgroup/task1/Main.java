package com.fdmgroup.task1;

import com.fdmgroup.task1.bank.account.BankAccount;
import com.fdmgroup.task1.bank.account.CheckingAccount;
import com.fdmgroup.task1.bank.account.SavingsAccount;
import com.fdmgroup.task1.bank.customer.BankCustomer;
import com.fdmgroup.task1.bank.customer.Company;
import com.fdmgroup.task1.bank.customer.Person;

public class Main {

	public static void main(String[] args) {
		System.out.println("--- Company Customer Start ---");
		checkCompanyCustomer();
		System.out.println("--- Company Customer End ---");
		System.out.println();
		System.out.println("//==//==//==//==//==//==//");
		System.out.println();
		System.out.println("--- Person Customer Start ---");
		checkPersonCustomer();
		System.out.println("--- Person Customer End ---");
	}

	private static void checkCompanyCustomer() {
		CheckingAccount checking = new CheckingAccount();
		// create new instance of the CheckingAccount
		System.out.println("Initialized data:");
		System.out.println(" id: " + checking.getId()); 
		// print id to make sure that unique id is created, according to spec id of the bank account must be started from 1000
		System.out.println(" nextCheckNumber: " + checking.getNextCheckNumber());
		// print nextCheckNumber to make sure that nextCheckNumber is created, according to spec nextCheckNumber must be started from 0
		System.out.println(" balance: " + checking.getBalance());

		checking.nextCheck();
		checking.changeBalance(BankAccount.WITHDRAW, 2.0);

		System.out.println("--------"); 
		System.out.println("Changed data:");
		System.out.println(" id: " + checking.getId());
		System.out.println(" nextCheckNumber: " + checking.getNextCheckNumber());
		System.out.println(" balance: " + checking.getBalance());

		SavingsAccount savings = new SavingsAccount();
		System.out.println("========");
		System.out.println("Initialized data:");
		System.out.println(" id: " + savings.getId());
		System.out.println(" balance: " + savings.getBalance());
		savings.changeBalance(BankAccount.WITHDRAW, 2.0); // we'll get error, because saving account can't be overdrawn
		savings.changeBalance(BankAccount.DEPOSIT, 2.0);
		System.out.println("--------"); 
		System.out.println("Changed data:");
		System.out.println(" id: " + savings.getId());
		System.out.println(" balance: " + savings.getBalance());
		Company company1 = new Company();
		System.out.println("========");
		System.out.println("ID: " + company1.getId()); // check init customer id
		company1.addAccount(savings); // mapping account to customer
		company1.addAccount(checking); // mapping account to customer

		System.out.println("accountType after mapping: " + checking.getAccountType()); // recheck account type after mapping
		System.out.println("accountType after mapping: " + savings.getAccountType()); // recheck account type after mapping

		/* 
		 * Can add the same amount (specified by user) to all of the accounts it owns at once
		 */
		System.out.println("--------\nBefore"); 
		System.out.println("first account: " + checking.getBalance()); 
		System.out.println("second account: " + savings.getBalance()); 
		company1.addAmountToAllAccounts(200.0);
		System.out.println("--------\nAfter"); 
		System.out.println("first account: " + checking.getBalance()); 
		System.out.println("second account: " + savings.getBalance());
		
		// remove company
		removeCustomer(company1);
	}

	private static void checkPersonCustomer() {
		CheckingAccount checking = new CheckingAccount();
		System.out.println("Initialized data:");
		System.out.println(" id: " + checking.getId());
		System.out.println(" nextCheckNumber: " + checking.getNextCheckNumber());
		System.out.println(" balance: " + checking.getBalance());

		checking.nextCheck();
		checking.changeBalance(BankAccount.WITHDRAW, 2.0);

		System.out.println("--------"); 
		System.out.println("Changed data:");
		System.out.println(" id: " + checking.getId());
		System.out.println(" nextCheckNumber: " + checking.getNextCheckNumber());
		System.out.println(" balance: " + checking.getBalance());

		SavingsAccount savings = new SavingsAccount();
		System.out.println("========");
		System.out.println("Initialized data:");
		System.out.println(" id: " + savings.getId());
		System.out.println(" balance: " + savings.getBalance());
		savings.changeBalance(BankAccount.WITHDRAW, 2.0); // we'll get error, because saving account can't be overdrawn
		savings.changeBalance(BankAccount.DEPOSIT, 2.0);
		System.out.println("--------"); 
		System.out.println("Changed data:");
		System.out.println(" id: " + savings.getId());
		System.out.println(" balance: " + savings.getBalance());
		Person person = new Person();
		System.out.println("========");
		System.out.println("ID: " + person.getId()); // check init customer id
		person.addAccount(savings); // mapping account to customer
		person.addAccount(checking); // mapping account to customer

		System.out.println("accountType after mapping: " + checking.getAccountType()); // recheck account type after mapping
		System.out.println("accountType after mapping: " + savings.getAccountType()); // recheck account type after mapping
		
		/*
		 * Can reset all of his/her accounts' balances back to 0 in one step
		 */
		System.out.println("--------\nBefore"); 
		System.out.println("first account: " + checking.getBalance()); 
		System.out.println("second account: " + savings.getBalance()); 
		person.resetAllAccounts();
		System.out.println("--------\nAfter"); 
		System.out.println("first account: " + checking.getBalance()); 
		System.out.println("second account: " + savings.getBalance());
		
		// remove person
		removeCustomer(person);
	}
	
	private static void removeCustomer(BankCustomer customer) {
		// remove all accounts belong to customer
		for(BankAccount account : customer.getAccounts()) {
			customer.getAccounts().remove(account);
			account = null;
		}
		
		// set current customer to null
		customer = null;
	}
}
