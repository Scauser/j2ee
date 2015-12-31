package com.fdmgroup.pensions.task3;

public class PensionController {

	public void pensionHandler(Person[] people) {
		// create new instance of the class PensionLogic
		PensionLogic logic = new PensionLogic();
		// create new instance of the class View
		View view = new View();
		// iterate throw array
		for(Person person : people) {
			// call PesionLogic class method pensionable
			if(logic.pensionable(person, "2014")) {
				// if pensionable method returns true
				// then call View class eligible method
				view.eligible(person);
			} else {
				// if pensionable method returns false
				// then call View class ineligible method
				view.ineligible(person);
			}
		}
	}
}
