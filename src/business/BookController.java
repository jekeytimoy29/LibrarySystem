package business;

import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class BookController {
	
	public Book getBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.readBooksMap().get(isbn);
	}
	
	public List<Book> getAllBooks() {
		DataAccess da = new DataAccessFacade();
		List<Book> books = new ArrayList<Book>();
		da.readBooksMap().forEach((key, value) -> {
			books.add(value);
		});
		return books;
	}
	
	public void addBook(Book book) {
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
	}
	
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
}
