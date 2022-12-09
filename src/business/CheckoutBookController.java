package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class CheckoutBookController {

	public CheckoutRecord checkoutBook(String memberId, String bookISBN, LocalDate checkoutDate) throws CheckoutBookException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> memberMap = da.readMemberMap();
		HashMap<String, Book> bookMap = da.readBooksMap();

		if (!memberMap.containsKey(memberId))
			throw new CheckoutBookException("Member ID: " + memberId + " not found");

		if (!bookMap.containsKey(bookISBN))
			throw new CheckoutBookException("Book with ISBN No.: " + bookISBN + " not found");

		for (BookCopy bc : bookMap.get(bookISBN).getCopies()) {
			if (bc.isAvailable()) {
				LibraryMember member = memberMap.get(memberId);
				CheckoutRecord checkoutRecord;
				
				if(member.getCheckoutRecord() == null) {
					checkoutRecord = CheckoutRecordFactory.createCheckoutRecord(member);
					checkoutRecord.addCheckoutEntry(CheckoutRecordFactory.createCheckoutEntry(bc, checkoutDate));
					da.saveNewCheckoutRecord(checkoutRecord);
				}
				else {
					CheckoutEntry checkoutEntry = CheckoutRecordFactory.createCheckoutEntry(bc, checkoutDate);
					member.getCheckoutRecord().addCheckoutEntry(checkoutEntry);
					checkoutRecord = member.getCheckoutRecord();
					da.saveNewCheckoutRecord(checkoutRecord);
				}
				
				updateBookCopyAvailability(bc, bookISBN);
				return checkoutRecord;
			}
		}

		throw new CheckoutBookException("Book with ISBN No.: " + bookISBN + " not available for checkout");
	}

	public List<CheckoutRecord> getAllCheckoutRecords() {
		DataAccess da = new DataAccessFacade();
		
		List<CheckoutRecord> records = new ArrayList<CheckoutRecord>();
		
		da.readCheckoutRecordMap().forEach((key, value) -> {
			records.add(value);
		});

		return records;
	}

	private void updateBookCopyAvailability(BookCopy bc, String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
		
		for(BookCopy bookCopy : bookMap.get(isbn).getCopies()) {
			if(bc.getCopyNum() == bookCopy.getCopyNum()) {
				bookCopy.changeAvailability();
				break;
			}
		}
		
		da.saveNewBook(bookMap.get(isbn));
	}

	public void deleteCheckoutEntry(String memberId, String bookTitle, String copyNumber) {
		DataAccess da = new DataAccessFacade();
		CheckoutRecord checkoutRecord = da.readCheckoutRecordMap().get(memberId);
		BookCopy bookCopy;
		
		for(CheckoutEntry entry: checkoutRecord.getEntries()) {
			if(entry.getBookCopy().getBook().getTitle().equals(bookTitle)
					&& entry.getBookCopy().getCopyNum() == Integer.parseInt(copyNumber)) {
				bookCopy = entry.getBookCopy();
				checkoutRecord.getEntries().remove(entry);
				da.saveNewCheckoutRecord(checkoutRecord);
				updateBookCopyAvailability(bookCopy, bookCopy.getBook().getIsbn());
				break;
			}
		}
		
	}

}
