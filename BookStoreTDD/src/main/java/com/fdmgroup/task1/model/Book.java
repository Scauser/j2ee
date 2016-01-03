package com.fdmgroup.task1.model;

public class Book {

	// book unique id, for determining book is same or different
	private Integer id;
	// book price
	private Double price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
