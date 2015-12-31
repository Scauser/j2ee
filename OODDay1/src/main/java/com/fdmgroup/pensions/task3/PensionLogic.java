package com.fdmgroup.pensions.task3;

public class PensionLogic {

	public boolean pensionable(Person person, String year) {
		// find begin index of the person year of birth (dd/MM/yyyy) 
		// in our case it always is 6
		int yearBeginIndex = person.getDateOfBirth().length() - 4;
		
		// find last index of the person year of birth (dd/MM/yyyy) 
		// in our case it always is 10
		int yearLastIndex = person.getDateOfBirth().length();
		
		// then substring year of birth from date of birth
		String yearOfBirth = person.getDateOfBirth().substring(yearBeginIndex, yearLastIndex);
		
		// calculate age of the person
		int personAge = Integer.parseInt(year) - Integer.parseInt(yearOfBirth);
		
		// check is personAge greater or equals 65
		// if personAge is greater or equals return true, otherwise false
		return personAge >= 65;
	}
}
