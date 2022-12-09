package business;

import java.util.HashMap;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class CheckoutBookController {
	
	public void checkoutBook(String memberId, String bookISBN) throws CheckoutBookException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> memberMap = da.readMemberMap();
		HashMap<String, Book> bookMap = da.readBooksMap();
		
		if(!memberMap.containsKey(memberId))
			throw new CheckoutBookException("Member ID: " + memberId + " not found");
		
		if(!bookMap.containsKey(bookISBN))
			throw new CheckoutBookException("Book with ISBN No.: " + bookISBN + "not found");
		
		for(BookCopy bc : bookMap.get(bookISBN).getCopies()) {
			if(bc.isAvailable()) {
				
				return;
			}
		}
		
		throw new CheckoutBookException("Book with ISBN No.: " + bookISBN + "not available for checkout");
	}

}
