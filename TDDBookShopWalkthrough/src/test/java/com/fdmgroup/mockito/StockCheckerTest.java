package com.fdmgroup.mockito;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StockCheckerTest {

	@Mock
	DatabaseReader mockDatabaseReader;

	@Test
	public void test_NumberInStock_CallsReadQuantityMethodOfOurDatabaseReaderExacltyOnce_WhenCalled() {
		//Arrange
		String isbn = "ABC1234567";
		StockChecker stockChecker = new StockChecker(mockDatabaseReader);
		//Act
		stockChecker.numberInStock(isbn);
		//Assert
		verify(mockDatabaseReader,times(1)).readQuantity(anyString());
	}

	@Test
	public void test_NumberInStock_CallsReadQuantityMethodOfOurDatabaseReaderExacltyOnce_WhenCalled2() {
		//Arrange
		String isbn = "ABC1234567";
		DatabaseReader mockDatabaseReader = mock(DatabaseReader.class);
		StockChecker stockChecker = new StockChecker(mockDatabaseReader);
		//Act
		stockChecker.numberInStock(isbn);
		//Assert
		verify(mockDatabaseReader,times(1)).readQuantity(anyString());
	}

	@Test
	public void testBookCopiesInDatabase() { // Test 2
		//Arrange
		String isbn = "ABC1234567";
		StockChecker stockChecker = new StockChecker(mockDatabaseReader);
		//Act
		stockChecker.numberInStockTest2(isbn);
		//Assert
		verify(mockDatabaseReader,times(1)).readQuantity(anyString());
	}

	@Test
	public void testBookCopiesCountIsThree() { // Test 3
		//Arrange
		String isbn = "ABC1234567";
		StockChecker stockChecker = new StockChecker(mockDatabaseReader);
		//Act
		Mockito.when(mockDatabaseReader.readQuantity(anyString())).thenReturn(3); // mock that readQuantity will return 3		
		//Assert
		assertEquals(true, stockChecker.checkBookAvailability(isbn));
	}
}
