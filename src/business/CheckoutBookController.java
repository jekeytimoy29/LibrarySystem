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
				}
				else {
					CheckoutEntry checkoutEntry = CheckoutRecordFactory.createCheckoutEntry(bc, checkoutDate);
					member.getCheckoutRecord().addCheckoutEntry(checkoutEntry);
					checkoutRecord = member.getCheckoutRecord();
				}
				
				da.saveNewMember(member);
				updateBookCopyAvailability(bc, bookISBN);
				return checkoutRecord;
			}
		}

		throw new CheckoutBookException("Book with ISBN No.: " + bookISBN + " not available for checkout");
	}

	public List<CheckoutRecord> getAllCheckoutRecords() {
		DataAccess da = new DataAccessFacade();
		
		List<CheckoutRecord> records = new ArrayList<>();
		List<LibraryMember> members = new ArrayList<>();
		
		da.readMemberMap().forEach((key, value) -> {
			members.add(value);
		});
		
		for(LibraryMember member : members){
			if(null != member.getCheckoutRecord())
				records.add(member.getCheckoutRecord());
		}

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
		//CheckoutRecord checkoutRecord = da.readCheckoutRecordMap().get(memberId);
		LibraryMember member = da.readMemberMap().get(memberId);
		CheckoutRecord checkoutRecord = member.getCheckoutRecord();
		BookCopy bookCopy;
		
		if(checkoutRecord != null) {
			for(CheckoutEntry entry: checkoutRecord.getEntries()) {
				if(entry.getBookCopy().getBook().getTitle().equals(bookTitle)
						&& entry.getBookCopy().getCopyNum() == Integer.parseInt(copyNumber)) {
					bookCopy = entry.getBookCopy();
					checkoutRecord.getEntries().remove(entry);
					//da.saveNewCheckoutRecord(checkoutRecord);
					da.saveNewMember(member);
					updateBookCopyAvailability(bookCopy, bookCopy.getBook().getIsbn());
					break;
				}
			}
		}
	
	}

}
