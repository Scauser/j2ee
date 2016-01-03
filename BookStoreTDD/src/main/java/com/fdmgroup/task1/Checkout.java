package com.fdmgroup.task1;

import java.util.HashSet;
import java.util.Set;

import com.fdmgroup.task1.model.Basket;
import com.fdmgroup.task1.model.Book;

public class Checkout {

	public double calculatePrice(Basket basket) {
		if(basket == null || basket.getBooksInBasket().isEmpty()) {
			// if basket null or books size in the basket is 0, then return 0.0
			return 0.0;
		} else {
			// 1. calculating an accumulative 1% discount for every 3 books
			// try to find 3 books' count
			int discountPrecent = basket.getBooksInBasket().size() / 3;

			if(basket.getBooksInBasket().size() >= 10) { // 2. check is books count greater or equal to 10 to add additional discount 10%
				discountPrecent += 10; // if books count greater or equals to 10 add 10% discount to accumulative discount
			}

			System.out.println("discountPrecent: " + discountPrecent + "%");
			System.out.println("totalPrice without discount: " + basket.getTotalPrice());
			// now try to find total price with discount
			double totalPrice = basket.getTotalPrice() - (basket.getTotalPrice() * discountPrecent / 100);
			System.out.println("totalPrice with discount: " + totalPrice);
			System.out.println(">>>>>>>>>>>");
			return totalPrice;
		}
	}

	public double calculatePriceBonus(Basket basket) {
		if(basket == null || basket.getBooksInBasket().isEmpty()) {
			// if basket null or books size in the basket is 0, then return 0.0
			return 0.0;
		} else {
			// 1. calculating an accumulative 1% discount for every 3 books
			// try to find 3 books' count
			int discountPrecent = basket.getBooksInBasket().size() / 3;

			if(basket.getBooksInBasket().size() >= 10) { // 2. check is books count greater or equal to 10 to add additional discount 10%
				discountPrecent += 10; // if books count greater or equals to 10 add 10% discount to accumulative discount
			}

			// bonus requirements additional discount
			// 3. If every book in the Basket is different, apply an additional 5% to the whole basket

			Set<Integer> set = new HashSet<Integer>();
			for(Book book : basket.getBooksInBasket()) {
				set.add(book.getId());
			}

			if(set.size() == basket.getBooksInBasket().size()) {
				// if all books are different
				discountPrecent += 5;
			}

			// 4. If the basket contains 2 of the same book, apply a unique discount of 50% to those two books only.
			double discountSameBooks = 0.0;
			for(int i = 0; i < basket.getBooksInBasket().size() - 1; i++) {
				for(int j = i + 1; j < basket.getBooksInBasket().size(); j++) {
					if(basket.getBooksInBasket().get(i).getId() == basket.getBooksInBasket().get(j).getId()) {
						discountSameBooks += basket.getBooksInBasket().get(i).getPrice();
					}
				}
			}

			System.out.println("discountPrecent: " + discountPrecent + "%");
			System.out.println("discountSameBooks: " + discountSameBooks);
			System.out.println("totalPrice without discount: " + basket.getTotalPrice());
			// now try to find total price with discount
			double totalPrice = basket.getTotalPrice() - (basket.getTotalPrice() * discountPrecent / 100);
			totalPrice -= discountSameBooks;
			System.out.println("totalPrice with discount: " + totalPrice);
			System.out.println(">>>>>>>>>>>");
			return totalPrice;
		}
	}
}
