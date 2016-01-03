package com.fdmgroup.task1.model;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import com.fdmgroup.task1.model.Basket;
import com.fdmgroup.task1.model.Book;

public class BasketTest {

	@Test // mark method as junit test method
	public void test_GetBooksInBasket_ReturnsEmptyBookList_IfNoBooksHaveBeenAdded() {
		Basket basket = new Basket(); // create new instance of Basket class
		List<Book> books = basket.getBooksInBasket(); // get books list
		assertEquals(0, books.size()); // check books list size is 0, if books list size isn't 0 test will fail
	}

	@Test // mark method as junit test method
	public void	test_GetBooksInBasket_ReturnsArrayOfLengthOne_AfterAddBookMethodIsCalledWithOneBook() {
		Basket basket = new Basket(); // create new instance of Basket class
		Book book = new Book(); // create new instance of Book class
		basket.addBook(book); // add created Book instance to basket
		List<Book> books = basket.getBooksInBasket(); // get books list
		assertEquals(1, books.size()); // check books list size is 1, if books list size isn't 1 test will fail
	}
	
	@Test
	public void testGetBooksInBasket3() {
		Basket basket = new Basket(); // create new instance of Basket class
		Book book1 = new Book(); // create new instance of Book class
		basket.addBook(book1); // add created book1 to basket
		Book book2 = new Book(); // create new instance of Book class
		basket.addBook(book2); // add created book2 to basket
		List<Book> books = basket.getBooksInBasket(); // get books list
		assertEquals(2, books.size()); // check books list size is 2, if books list size isn't 2 test will fail
	}
}
