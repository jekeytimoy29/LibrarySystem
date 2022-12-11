package business;

import java.util.HashMap;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class CheckoutByController {
	public CheckoutRecord getCheckout(String memberId) {
		DataAccess da = new DataAccessFacade();
		return da.readMemberMap().get(memberId).getCheckoutRecord();
	}
	
	public HashMap<String, LibraryMember> getMembers() {
		DataAccess da = new DataAccessFacade();
		return da.readMemberMap();
	}
	
//	public void addCheckout(CheckoutRecord checkout) {
//		DataAccess da = new DataAccessFacade();
//		da.saveCheckout(checkout);
//	}
}
