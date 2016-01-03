package com.fdmgroup.task1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fdmgroup.task1.Checkout;
import com.fdmgroup.task1.model.Basket;
import com.fdmgroup.task1.model.Book;

public class CheckoutTest {

	@Test
	public void test_CalculatePrice_ReturnsDoubleZeroPointZeroWhenPassedAnEmptyBasket() {
		Basket basket = new Basket();  // create new instance of Basket class
		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePrice(basket); // calculate total price with discount of the basket
		assertEquals(0.0, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}

	@Test
	public void test_CalculatePrice_ReturnsPriceOfBookInBasket_WhenPassedBasketWithOneBook() {
		Basket basket = new Basket();  // create new instance of Basket class
		Book book = new Book(); // create new instance of Book class
		book.setPrice(100.0); // set book price to 100.0
		basket.addBook(book); // add the book to basket
		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePrice(basket); // calculate total price with discount of the basket
		assertEquals(100.0, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}

	// Test 3
	@Test
	public void testCalculatePrice3() {
		Basket basket = new Basket();  // create new instance of Basket class

		Book book1 = new Book(); // create new instance of Book class
		book1.setPrice(100.0); // set book price to 100.0
		basket.addBook(book1); // add the book to basket

		Book book2 = new Book(); // create new instance of Book class
		book2.setPrice(50.0); // set book price to 50.0
		basket.addBook(book2); // add the book to basket

		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePrice(basket); // calculate total price with discount of the basket
		assertEquals(150.0, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}

	// Test 4
	@Test
	public void testCalculatePrice4() {
		Basket basket = new Basket();  // create new instance of Basket class

		Book book1 = new Book(); // create new instance of Book class
		book1.setPrice(100.0); // set book price to 100.0
		basket.addBook(book1); // add the book to basket

		Book book2 = new Book(); // create new instance of Book class
		book2.setPrice(50.0); // set book price to 50.0
		basket.addBook(book2); // add the book to basket

		Book book3 = new Book(); // create new instance of Book class
		book3.setPrice(50.0); // set book price to 50.0
		basket.addBook(book3); // add the book to basket

		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePrice(basket); // calculate total price with discount of the basket
		// totalPrice must be -> 200.0 - (200.0 * 1)/100 = 198.0
		assertEquals(198.0, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}

	// Test 5
	@Test
	public void testCalculatePrice5() {
		Basket basket = new Basket();  // create new instance of Basket class

		Book book1 = new Book(); // create new instance of Book class
		book1.setPrice(100.0); // set book price to 100.0
		basket.addBook(book1); // add the book to basket

		Book book2 = new Book(); // create new instance of Book class
		book2.setPrice(50.0); // set book price to 50.0
		basket.addBook(book2); // add the book to basket

		Book book3 = new Book(); // create new instance of Book class
		book3.setPrice(50.0); // set book price to 50.0
		basket.addBook(book3); // add the book to basket

		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePrice(basket); // calculate total price with discount of the basket
		// totalPrice must be -> 200.0 - (200.0 * 1)/100 = 198.0
		assertEquals(198.0, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}

	// Test 6
	@Test
	public void testCalculatePrice6() {
		Basket basket = new Basket();  // create new instance of Basket class

		Book book1 = new Book(); // create new instance of Book class
		book1.setPrice(100.0); // set book price to 100.0
		basket.addBook(book1); // add the book to basket

		Book book2 = new Book(); // create new instance of Book class
		book2.setPrice(50.0); // set book price to 50.0
		basket.addBook(book2); // add the book to basket

		Book book3 = new Book(); // create new instance of Book class
		book3.setPrice(50.0); // set book price to 50.0
		basket.addBook(book3); // add the book to basket

		Book book4 = new Book(); // create new instance of Book class
		book4.setPrice(50.0); // set book price to 50.0
		basket.addBook(book4); // add the book to basket

		Book book5 = new Book(); // create new instance of Book class
		book5.setPrice(50.0); // set book price to 50.0
		basket.addBook(book5); // add the book to basket

		Book book6 = new Book(); // create new instance of Book class
		book6.setPrice(50.0); // set book price to 50.0
		basket.addBook(book6); // add the book to basket

		Book book7 = new Book(); // create new instance of Book class
		book7.setPrice(50.0); // set book price to 50.0
		basket.addBook(book7); // add the book to basket

		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePrice(basket); // calculate total price with discount of the basket
		// totalPrice must be -> 400.0 - (400.0 * 2)/100 = 392.0
		assertEquals(392.0, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}

	// Test 7
	@Test
	public void testCalculatePrice7() {
		Basket basket = new Basket();  // create new instance of Basket class

		Book book1 = new Book(); // create new instance of Book class
		book1.setPrice(100.0); // set book price to 100.0
		basket.addBook(book1); // add the book to basket

		Book book2 = new Book(); // create new instance of Book class
		book2.setPrice(50.0); // set book price to 50.0
		basket.addBook(book2); // add the book to basket

		Book book3 = new Book(); // create new instance of Book class
		book3.setPrice(50.0); // set book price to 50.0
		basket.addBook(book3); // add the book to basket

		Book book4 = new Book(); // create new instance of Book class
		book4.setPrice(50.0); // set book price to 50.0
		basket.addBook(book4); // add the book to basket

		Book book5 = new Book(); // create new instance of Book class
		book5.setPrice(50.0); // set book price to 50.0
		basket.addBook(book5); // add the book to basket

		Book book6 = new Book(); // create new instance of Book class
		book6.setPrice(50.0); // set book price to 50.0
		basket.addBook(book6); // add the book to basket

		Book book7 = new Book(); // create new instance of Book class
		book7.setPrice(50.0); // set book price to 50.0
		basket.addBook(book7); // add the book to basket

		Book book8 = new Book(); // create new instance of Book class
		book8.setPrice(50.0); // set book price to 50.0
		basket.addBook(book8); // add the book to basket

		Book book9 = new Book(); // create new instance of Book class
		book9.setPrice(50.0); // set book price to 50.0
		basket.addBook(book9); // add the book to basket

		Book book10 = new Book(); // create new instance of Book class
		book10.setPrice(50.0); // set book price to 50.0
		basket.addBook(book10); // add the book to basket

		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePrice(basket); // calculate total price with discount of the basket
		// 10% for having ten books, 3% for having three multiples of 3 books
		// total 13% discount
		// totalPrice must be -> 550.0 - (550.0 * 13)/100 = 478.5
		assertEquals(478.5, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}

	// Test bonus requirement #1
	@Test
	public void testCalculatePrice8() {
		Basket basket = new Basket();  // create new instance of Basket class

		Book book1 = new Book(); // create new instance of Book class
		book1.setId(1); // set unique book id to identify book
		book1.setPrice(100.0); // set book price to 100.0
		basket.addBook(book1); // add the book to basket

		Book book2 = new Book(); // create new instance of Book class
		book2.setId(2); // set unique book id to identify book
		book2.setPrice(50.0); // set book price to 50.0
		basket.addBook(book2); // add the book to basket

		Book book3 = new Book(); // create new instance of Book class
		book3.setId(3); // set unique book id to identify book
		book3.setPrice(50.0); // set book price to 50.0
		basket.addBook(book3); // add the book to basket

		Book book4 = new Book(); // create new instance of Book class
		book4.setId(4); // set unique book id to identify book
		book4.setPrice(50.0); // set book price to 50.0
		basket.addBook(book4); // add the book to basket

		Book book5 = new Book(); // create new instance of Book class
		book5.setId(5); // set unique book id to identify book
		book5.setPrice(50.0); // set book price to 50.0
		basket.addBook(book5); // add the book to basket

		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePriceBonus(basket); // calculate total price with discount of the basket
		// 1% for having 1 multiple of 3 books
		// and 5% for that every book in the Basket is different
		// 300-(300*6)/100=282.0
		assertEquals(282.0, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}

	// Test bonus requirement #2
	@Test
	public void testCalculatePrice9() {
		Basket basket = new Basket();  // create new instance of Basket class

		Book book1 = new Book(); // create new instance of Book class
		book1.setId(1); // set unique book id to identify book
		book1.setPrice(100.0); // set book price to 100.0
		basket.addBook(book1); // add the book to basket

		Book book2 = new Book(); // create new instance of Book class
		book2.setId(2); // set unique book id to identify book
		book2.setPrice(50.0); // set book price to 50.0
		basket.addBook(book2); // add the book to basket

		Book book3 = new Book(); // create new instance of Book class
		book3.setId(2); // set unique book id to identify book
		book3.setPrice(50.0); // set book price to 50.0
		basket.addBook(book3); // add the book to basket

		Book book4 = new Book(); // create new instance of Book class
		book4.setId(4); // set unique book id to identify book
		book4.setPrice(100.0); // set book price to 50.0
		basket.addBook(book4); // add the book to basket

		Book book5 = new Book(); // create new instance of Book class
		book5.setId(5); // set unique book id to identify book
		book5.setPrice(100.0); // set book price to 50.0
		basket.addBook(book5); // add the book to basket

		Checkout checkout = new Checkout();  // create new instance of Checkout class
		double totalPrice = checkout.calculatePriceBonus(basket); // calculate total price with discount of the basket
		// 1% for having 1 multiple of 3 books
		// and 50.0 for that the basket contains 2 of the same book
		// 400.0-(400.0*1)/100-50.0=346.0
		assertEquals(346.0, totalPrice, 0.001); // check it, here 0.001 is accuracy
	}
}
