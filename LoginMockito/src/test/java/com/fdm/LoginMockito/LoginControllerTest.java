package com.fdm.LoginMockito;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

	LoginController login; //referenced here so don't need to write LoginController login= new LoginController in every arrange
	@Mock
	DatabaseReader reader; //above created as an interface rather than class
	@Mock //Have to keep doing @Mock for each mock object
	User user1;
	@Mock
	User user2;
	
	@Before
	public void setUp() throws Exception {
		
		//Arrange
		MockitoAnnotations.initMocks(this);  //Creates all the mock objects we've defined above ie reader
		login= new LoginController(reader);  // Hover on red underline to create LoginController constructor
		//stubbing(below)- get mock object to return a specific value. This gets around the fact we actually don't have a database
		when(reader.getUser("Mihir")).thenReturn(user1); //Return user 1 any time reader's getUser method is called with these args
		when(user1.getName()).thenReturn("Mihir"); //Return mihir any time user 1's getName method is called 
		when(user1.getPassword()).thenReturn("123"); //Return 123 any time user 1's getPassword method is called
		when(reader.getUser("Kuba")).thenReturn(user2); 
		when(user2.getName()).thenReturn("Kuba");
		when(user2.getPassword()).thenReturn("456");
	}

	@Test
	public void testCheckMethodCallsGetUserMethodInDatabaseReader() {
		//Act
		login.Check("Mihir","123");
		verify(reader,times(1)).getUser("Mihir"); // Checks that getUser method in reader object has been called with the right args
		// times checks the number of times check is called. 0 means its not called.
	}

	@Test
	public void testCheckMethodReturnsTrueWhenMihirAnd123PassedIn(){
		//Act
		boolean loggedOn = login.Check("Mihir","123");
		//Assert
		assertTrue(loggedOn);
	}
	@Test
	public void testCheckMethodReturnsTrueWhenKubaAnd456PassedIn(){
		//Act
		boolean loggedOn = login.Check("Kuba","456");
		//Assert
		assertTrue(loggedOn);
	}

	@Test
	public void testCheckMethodReturnsFalseWhenIncorrectNamePassedIn(){
		//Act
		boolean loggedOn = login.Check("Nick","456");  //Here password doesn't matter since we're testing userName
		//Assert
		assertFalse(loggedOn);
	}
	
	@Test
	public void testCheckMethodReturnsFalseWhenIncorrectPasswordPassedIn(){
		//Act
		boolean loggedOn = login.Check("Mihir","456");
		//Assert
		assertFalse(loggedOn);
	}
}
