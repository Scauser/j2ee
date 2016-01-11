package com.fdmgroup.week2_generics;

public class Borrower<T extends Number> {
	
	private T id;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
}
