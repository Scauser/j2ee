package com.fdmgroup.fizzbuzz.task2;

public class FizzBuzzRunner {

	public void fizzBuzz(int number) {
		for(int i = 1; i <= number; i++) {
			// task2 main task
			//		if(i % 3 == 0 && i % 5 == 0) {
			//			System.out.println("FizzBuzz");
			//		} else if(i % 3 == 0) {
			//			System.out.println("Fizz");
			//		} else if(i % 5 == 0) {
			//			System.out.println("Buzz");
			//		} else {
			//			System.out.println(i);
			//		}

			// with additional methods
			if(fizzbuzz(i)) {
				System.out.println("FizzBuzz");
			} else if(fizz(i)) {
				System.out.println("Fizz");
			} else if(buzz(i)) {
				System.out.println("Buzz");
			} else {
				System.out.println(i);
			}
		}
	}

	private boolean fizz(int num) {
		// if num is multiple of 3 return true, otherwise false
		return num % 3 == 0;
	}

	private boolean buzz(int num) {
		// if num is multiple of 5 return true, otherwise false
		return num % 5 == 0;
	}

	private boolean fizzbuzz(int num) {
		// if num is multiple of 3 and 5 return true, otherwise false
		return num % 3 == 0 && num % 5 == 0;
	}
}
