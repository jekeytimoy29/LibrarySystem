package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{

	private static final long serialVersionUID = 7274497633871237909L;
	private LibraryMember member;
	private List<CheckoutEntry> entries;
	
	public LibraryMember getMember() {
		return member;
	}
	
	public List<CheckoutEntry> getEntries() {
		return entries;
	}
	
	public CheckoutRecord(LibraryMember member, BookCopy bookCopy, LocalDate checkoutDate){
		this.member = member;
		this.entries = new ArrayList<>();
		addCheckoutEntry(new CheckoutEntry(bookCopy, checkoutDate));
		
	}
	
	public void addCheckoutEntry(CheckoutEntry entry) {
		this.entries.add(entry);
	}

}
