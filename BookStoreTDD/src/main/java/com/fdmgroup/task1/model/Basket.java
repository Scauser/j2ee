package com.fdmgroup.task1.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {

	private List<Book> books = new ArrayList<Book>();
	// total price of all books without discount
	private Double totalPrice = 0.0;

	public List<Book> getBooksInBasket() {
		return books;
	}

	public void addBook(Book book) {
		totalPrice += book.getPrice();
		books.add(book);
	}

	public Double getTotalPrice() {
		return totalPrice;
	}
}
