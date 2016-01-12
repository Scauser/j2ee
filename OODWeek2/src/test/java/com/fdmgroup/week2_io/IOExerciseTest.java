package com.fdmgroup.week2_io;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class IOExerciseTest {

	private IOExercise exercise;

	@Before
	public void setUp() {
		exercise = new IOExercise();
	}

	@Test
	public void testCountCharacter() {
		int count = exercise.countCharacter('a', "sample.txt");
		assertEquals(4, count);
	}

	@Test
	public void testRegisterUser() {
		boolean result = exercise.registerUser("Daniyar1", 
				"daniyar.artykov1@gmail.com", "London1");
		assertEquals(true, result);
		
		result = exercise.registerUser("Daniyar2", 
				"daniyar.artykov2@gmail.com", "London2");
		assertEquals(true, result);
	}

	@Test
	public void testGetUsers() {
		List<User> users = exercise.getUsers();

		for(User u : users) {
			System.out.println("username: " + u.getUsername());
			System.out.println("email: " + u.getEmail());
			System.out.println("address: " + u.getAddress());
			System.out.println("-----------------------------");
		}

		assertEquals(true, users.size() > 0);
	}
}
