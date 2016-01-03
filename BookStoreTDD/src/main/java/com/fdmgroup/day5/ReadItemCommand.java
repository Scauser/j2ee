package com.fdmgroup.day5;

import java.util.List;

import com.fdmgroup.task1.model.Book;

public interface ReadItemCommand {

	List<Book> readAll();
}
