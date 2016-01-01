package com.fdmgroup.task2;

interface TestInterface1 extends TestInterface2 { // #6 - true

	int y = 1; // #5 - false; interface variables static and final by default, 
	// interface can't have non-final variables

	int turn1(String direction,
			double radius,
			double startSpeed,
			double endSpeed);
}
