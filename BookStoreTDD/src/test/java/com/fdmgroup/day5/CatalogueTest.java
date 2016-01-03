package com.fdmgroup.day5;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fdmgroup.task1.model.Book;

@RunWith(MockitoJUnitRunner.class)
public class CatalogueTest {

	@Mock
	ReadItemCommand mockReadItemCommand;

	@Mock
	WriteItemCommand mockWriteItemCommand;

	@Mock
	List<Book> mockBooks;

	@Mock
	Book mockBook;

	@InjectMocks
	Catalogue catalogue;

	@Test
	public void test_GetAllBooks_ReturnsEmptyBookList_IfNoBooksAreInTheCatalogue() {
		Catalogue catalogue = new Catalogue(mockReadItemCommand);
		List<Book> books = catalogue.getAllBooks();
		assertEquals(0, books.size());
	}

	@Test
	public void test_GetAllBooks_CallsReadAllMethodOfReadItemCommand_WhenCalled() {
		Catalogue catalogue = new Catalogue(mockReadItemCommand);
		catalogue.getAllBooks();
		verify(mockReadItemCommand, times(1)).readAll();
	}

	@Test
	public void test_GetAllBooks_ReturnsListOfBooksItReceivesFromReadAllMethodOfReadItemCommand_WhenCalled(){
		Catalogue catalogue = new Catalogue(mockReadItemCommand);
		Mockito.when(mockReadItemCommand.readAll()).thenReturn(mockBooks);
		List<Book> books = catalogue.getAllBooks();
		assertEquals(mockBooks, books);
	}

	@Test
	public void test_AddBook_CallsInsertItemMethodOfWriteItemCommand_WhenCalled(){
		Catalogue catalogue = new Catalogue(mockWriteItemCommand);
		catalogue.addBook(null);
		verify(mockWriteItemCommand, times(1)).insertItem(null);
	}
	
	@Test // Test 5
	public void testCatalogue5() {
		Catalogue catalogue = new Catalogue(mockWriteItemCommand);
		catalogue.addBook(mockBook); // add mock Book to catalogue
		verify(mockWriteItemCommand, times(1)).insertItem(mockBook);
	}
}
