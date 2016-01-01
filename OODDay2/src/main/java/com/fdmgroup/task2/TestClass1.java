package com.fdmgroup.task2;

public class TestClass1 implements TestInterface1 {

	public String var = "var1";
	
	// #10 - true
	public TestClass1() {
		System.out.println("TestClass1()");
	}

	// #10 - true
	public TestClass1(String test) {

	}

	// #11 - true
	public int turn1(String direction) {
		return 1;
	}

	// #7 - true; #8 - true
	public int turn1(String direction, double radius, double startSpeed, double endSpeed) {
		return 0;
	}

	// #8 - true
	public int turn2(String direction, double radius, double startSpeed, double endSpeed) {
		return 0;
	}
}
