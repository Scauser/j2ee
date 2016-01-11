package com.fdmgroup.week2_generics;

import java.util.List;

public class Catalog<T> {
	
	List<T> items;
	
	public Catalog(List<T> items) {
		this.items = items;
	}
	
	public void addItem(T t){
		items.add(t);
	}
 
	public T findItem(int index) { 
		return items.get(index);
	}
	
	public List<T> getItems() {
		return items;
	}
}
