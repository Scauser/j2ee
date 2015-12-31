package com.fdmgroup.fizzbuzz.task1;

public class FizzBuzz {

	public static void main(String args[]) {
		// loop from 1 to 100
		for(int i = 1; i <= 100; i++) {
			// check is i multiple of the 3 and 5
			if(i % 3 == 0 && i % 5 == 0) {
				System.out.println("FizzBuzz");
			} else if(i % 3 == 0) { // check is i multiple of the 3
				System.out.println("Fizz");
			} else if(i % 5 == 0) { // check is i multiple of the 5
				System.out.println("Buzz");
			} else { // else print i
				System.out.println(i);
			}
		}
	}
}
