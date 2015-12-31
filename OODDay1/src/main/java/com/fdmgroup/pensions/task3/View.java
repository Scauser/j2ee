package com.fdmgroup.pensions.task3;

public class View {

	public void eligible(Person person) {
		System.out.println(person.getFirstname() + " " + person.getLastname() + 
				" may qualify for a pension this year.");
	}

	public void ineligible(Person person) {
		System.out.println(person.getFirstname() + " " + person.getLastname() + 
				" is not old enough yet.");
	}
}
