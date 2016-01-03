package com.fdmgroup.day5;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fdmgroup.task1.model.Book;

@RunWith(MockitoJUnitRunner.class)
public class CatalogueTest {

	// create mock object of the ReadItemCommand
	@Mock
	ReadItemCommand mockReadItemCommand;

	// create mock object of the WriteItemCommand
	@Mock
	WriteItemCommand mockWriteItemCommand;

	// create mock list object of books
	@Mock
	List<Book> mockBooks;

	// create mock object of the book
	@Mock
	Book mockBook;

	@Test
	public void test_GetAllBooks_ReturnsEmptyBookList_IfNoBooksAreInTheCatalogue() {
		// Test 1
		// Arrange
		Catalogue catalogue = new Catalogue(mockReadItemCommand); // create new object Catalogue
		// Act
		List<Book> books = catalogue.getAllBooks(); // get books list
		// Assert
		assertEquals(0, books.size()); // check is books.size 0, if not test will fail
	}

	@Test
	public void test_GetAllBooks_CallsReadAllMethodOfReadItemCommand_WhenCalled() {
		// Test 2
		// Arrange
		Catalogue catalogue = new Catalogue(mockReadItemCommand); // create new object Catalogue
		// Act
		catalogue.getAllBooks(); // get books list
		// Assert
		verify(mockReadItemCommand, times(1)).readAll(); // verify that readAll method of the ReadItemCommand called only 1 time, if not test will fail
	}

	@Test
	public void test_GetAllBooks_ReturnsListOfBooksItReceivesFromReadAllMethodOfReadItemCommand_WhenCalled(){
		// Test 3
		// Arrange
		Catalogue catalogue = new Catalogue(mockReadItemCommand); // *You will need a Catalogue object, *You will need to inject your mock ReadItemCommand into your Catalogue object
		// *You will need to stub the readAll() method of your mock ReadItemCommand to return the mock book list
		Mockito.when(mockReadItemCommand.readAll()).thenReturn(mockBooks); // mock of the method readAll of the mock object mockReadItemCommand, 
		//it means that all call of the methods readAll will return mock object mockBooks
		List<Book> books = catalogue.getAllBooks();
		// assert
		assertEquals(mockBooks, books);
	}

	@Test
	public void test_AddBook_CallsInsertItemMethodOfWriteItemCommand_WhenCalled(){
		// Test 4
		// Arrange
		Catalogue catalogue = new Catalogue(mockWriteItemCommand);
		// Act
		catalogue.addBook(null);
		// Assert
		verify(mockWriteItemCommand, times(1)).insertItem(null);
	}

	@Test 
	public void testCatalogue5() {
		// Test 5
		// Arrange
		Catalogue catalogue = new Catalogue(mockWriteItemCommand);
		// act
		catalogue.addBook(mockBook); // add mock Book to catalogue
		// assert
		verify(mockWriteItemCommand, times(1)).insertItem(mockBook);
	}
}
