package com.fdmgroup.pensions.task3;

public class Client {

	public static void main(String[] args) {
		// create new instance of the PensionController
		PensionController controller = new PensionController();
		
		// create new instance of the Person
		Person p1 = new Person();
		p1.setFirstname("Fred");
		p1.setLastname("Bloggs");
		p1.setDateOfBirth("12/12/1949");
		
		// create new instance of the Person
		Person p2 = new Person();
		p2.setFirstname("Jhon");
		p2.setLastname("Smith");
		p2.setDateOfBirth("05/01/1950");
		
		// create new instance of the Person
		Person p3 = new Person();
		p3.setFirstname("Joe");
		p3.setLastname("Public");
		p3.setDateOfBirth("28/06/1993");
		
		// create Person class array 
		Person[] persons = new Person[3];
		persons[0] = p1;
		persons[1] = p2;
		persons[2] = p3;
		
		controller.pensionHandler(persons);
	}
}
