package com.fdmgroup.week2_generics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CatalogTest {

	private Catalog<Book> catalogBook;
	private Catalog<Borrower> catalogBorrower;

	@Before
	public void setUp() {
		List<Book> books = new ArrayList<Book>();
		//		books.add(new Book());
		//		books.add(new Book());
		catalogBook = new Catalog<Book>(books);

		List<Borrower> borrowers = new ArrayList<Borrower>();
		//		borrowers.add(new Borrower());
		//		borrowers.add(new Borrower());
		catalogBorrower = new Catalog<Borrower>(borrowers);
	}

	@Test
	public void testAddItem() {
		// test for Book class
		// arrange
		catalogBook.addItem(new Book());
		catalogBook.addItem(new Book());
		// act
		int size = catalogBook.getItems().size();
		// assert
		assertEquals(2, size);

		// test for Borrower class
		// arrange
		catalogBorrower.addItem(new Borrower());
		catalogBorrower.addItem(new Borrower());
		catalogBorrower.addItem(new Borrower());
		// act
		size = catalogBorrower.getItems().size();
		// assert
		assertEquals(3, size);
	}

	@Test
	public void testFindItem() {
		// test for Book class
		// arrange
		Book book0 = new Book();
		catalogBook.addItem(book0);
		Book book1 = new Book();
		catalogBook.addItem(book1);
		// act
		Book book2 = catalogBook.findItem(0);
		// assert
		assertEquals(book0, book2);

		// test for Borrower class
		// arrange
		Borrower b0 = new Borrower();
		catalogBorrower.addItem(b0);
		Borrower b1 = new Borrower();
		catalogBorrower.addItem(b1);
		// act
		Borrower b2 = catalogBorrower.findItem(1);
		// assert
		assertEquals(b1, b2);
	}
}
