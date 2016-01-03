package com.fdmgroup.mockito;

public class StockChecker {

	DatabaseReader reader;

	public StockChecker(DatabaseReader reader) {
		this.reader = reader;
	}

	public int numberInStock(String isbn) {
		reader.readQuantity(""); // call DatabaseReader readQuantity method

		return 0;
	}

	public int numberInStockTest2(String isbn) { // Test 2
	
		return reader.readQuantity(isbn); // return value, that come from readQuantity method
	}

	public boolean checkBookAvailability(String isbn) { // Test 3
		int quantity = reader.readQuantity(isbn);
		
		return quantity == 3; // return true, if quantity is equal to 3, otherwise return false
	}
}
