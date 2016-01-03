package com.fdmgroup.day5;

import java.util.List;

import com.fdmgroup.task1.model.Book;

public class Catalogue {

	ReadItemCommand readItemCommand;
	WriteItemCommand writeItemCommand;
	
	public Catalogue() {
	}
	
	public Catalogue(ReadItemCommand readItemCommand) {
		this.readItemCommand = readItemCommand;
	}
	
	public Catalogue(WriteItemCommand writeItemCommand) {
		this.writeItemCommand = writeItemCommand;
	}
	
	public Catalogue(ReadItemCommand readItemCommand, WriteItemCommand writeItemCommand) {
		this.readItemCommand = readItemCommand;
		this.writeItemCommand = writeItemCommand;
	}

	public List<Book> getAllBooks() {
		return readItemCommand.readAll();
	}
	
	public void addBook(Book book) {
		writeItemCommand.insertItem(book);
	}
}
