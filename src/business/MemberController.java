package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class MemberController {
	public boolean memberExists(String memberId, List<String> memberIds) {
		return memberIds.contains(memberId);
	}
	public List<LibraryMember> getAllMembers() {
		DataAccess da = new DataAccessFacade();
		ArrayList<LibraryMember> libraryMembers = new ArrayList<LibraryMember>();
		da.readMemberMap().forEach((key, value) -> {
			libraryMembers.add(value);
		});
		return libraryMembers;
	}
	public LibraryMember getMember(String memberId) {
		DataAccessFacade da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		LibraryMember libraryMember = members.get(memberId);
		return libraryMember;
	}
	public void updateMember(LibraryMember libraryMember) {
		DataAccessFacade da = new DataAccessFacade();
		da.saveNewMember(libraryMember);
	}
	public void deleteMember(String memberId) {
		DataAccessFacade da = new DataAccessFacade();
		da.deleteMember(memberId);
	}
	public void addMember(String memberId, String fname, String lname,
			String tel, String street, String city, String state, String zip)
			throws LibrarySystemException {
		Address address = new Address(street, city, state, zip);
		LibraryMember libraryMember = new LibraryMember(memberId, fname, lname,
				tel, address);
		DataAccess dataAccess = new DataAccessFacade();
		dataAccess.saveNewMember(libraryMember);
	}
}