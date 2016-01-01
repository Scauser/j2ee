package com.fdmgroup.task2;

abstract class TestAbstract1 implements TestInterface1 { // #3 - true, #7 - true
	
	int x; // #2 - true
	
	public TestAbstract1() { // #4 - true
		
	}
	
	void moveTo(int newX, int newY) { // #1 - true
		
	}
	
	abstract void draw(); // #1 - true
}
