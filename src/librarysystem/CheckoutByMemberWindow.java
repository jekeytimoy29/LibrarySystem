package librarysystem;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import business.BookController;
import business.CheckoutByController;
import business.CheckoutEntry;
import business.CheckoutRecord;
import business.LibraryMember;

public class CheckoutByMemberWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 1L;
	public static final CheckoutByMemberWindow INSTANCE = new CheckoutByMemberWindow();
	CheckoutByController cc = new CheckoutByController();
	BookController bc = new BookController();
    private boolean isInitialized = false;

	private JPanel panel;
	private JComboBox<String> memberList;
	private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    
    private String[] column = {"Checkout Date", "Due Date", "ISBN", "Title"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckoutByMemberWindow.INSTANCE.init();
					CheckoutByMemberWindow.INSTANCE.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private CheckoutByMemberWindow() {}

	@Override
	public void init() {
		if(!isInitialized) initUi();
		initData();
	}
	
	public void initData() {
		HashMap<String, LibraryMember> members = cc.getMembers();
		List<String> membersKey = new ArrayList<>();
		members.forEach((key, value) -> {
			membersKey.add(key+" - "+value.getFirstName()+" "+value.getLastName());
		});
		Collections.sort(membersKey);
		DefaultComboBoxModel<String> modelCb = (DefaultComboBoxModel<String>) memberList.getModel();
        modelCb.removeAllElements();
        for (String item : membersKey) {
            modelCb.addElement(item);
        }
        memberList.setModel(modelCb);
        
        model.setRowCount(0);
        populateRecord(memberList.getSelectedItem().toString().split(" - ")[0]);
	}
	
	public void initUi() {
		// TODO Auto-generated method stub
		setTitle("Checkout Record By Member");

		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 6, 594, 460);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 46, 582, 408);
		panel.add(scrollPane);
		
		JButton btnback = new JButton("Back");
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
				LibrarySystem.INSTANCE.setVisible(true);
			}
		});
		btnback.setBounds(6, 5, 117, 29);
		panel.add(btnback);
		
		JLabel lblMember = new JLabel("Members");
		lblMember.setBounds(320, 6, 135, 29);
		panel.add(lblMember);
     
        memberList = new JComboBox<String>();
        memberList.setBounds(385, 6, 205, 29);
        memberList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(memberList.getSelectedItem()!=null) {
					model.setRowCount(0);
					populateRecord(memberList.getSelectedItem().toString().split(" - ")[0]);
				}
			}
		});
        panel.add(memberList);
        
        // setData
     	table = new JTable();
     	model = new DefaultTableModel();
     	model.setColumnIdentifiers(column);
     	table.setModel(model);
     	scrollPane.setViewportView(table);
     	table.setEnabled(false);
        
        isInitialized = true;
		
	}
	
	public void populateRecord(String memberId) {
 		CheckoutRecord cr = cc.getCheckout(memberId);
 		
 		Integer chckDateLen = column[0].length();
 		Integer dueDateLen = column[1].length();
 		Integer isbnLen = column[2].length();
 		Integer titleLen = column[3].length();
 		
 		List<String> chckDateCol = new ArrayList<String>();
 		List<String> dueDateCol = new ArrayList<String>();
 		List<String> isbnCol = new ArrayList<String>();
 		List<String> titleCol = new ArrayList<String>();
 		
 		if(cr!=null) {
	 		Collections.sort(cr.getEntries(), new Comparator<CheckoutEntry>() {
				@Override
				public int compare(CheckoutEntry o1, CheckoutEntry o2) {
					// TODO Auto-generated method stub
					return o1.getCheckoutDate().compareTo(o2.getCheckoutDate());
				}
	 		});
	 		for(CheckoutEntry ce: cr.getEntries()) {
	 			model.insertRow(0, new Object[] { ce.getCheckoutDate(), ce.getDueDate(), ce.getBookCopy().getBook().getIsbn(), ce.getBookCopy().getBook().getTitle() });
	 			
	 			chckDateCol.add(ce.getCheckoutDate().toString());
	 			if(chckDateLen < ce.getCheckoutDate().toString().length()) chckDateLen = ce.getCheckoutDate().toString().length();
	 			dueDateCol.add(ce.getDueDate().toString());
	 			if(dueDateLen < ce.getDueDate().toString().length()) dueDateLen = ce.getDueDate().toString().length();
	 			isbnCol.add(ce.getBookCopy().getBook().getIsbn());
	 			if(isbnLen < ce.getBookCopy().getBook().getIsbn().length()) isbnLen = ce.getBookCopy().getBook().getIsbn().length();
	 			titleCol.add(ce.getBookCopy().getBook().getTitle());
	 			if(titleLen < ce.getBookCopy().getBook().getTitle().length()) titleLen = ce.getBookCopy().getBook().getTitle().length();
	 		}
 		}
 		
 		for(int i=0;i<chckDateLen;i++)
 			System.out.print("-");
 		for(int i=0;i<dueDateLen;i++)
 			System.out.print("-");
 		for(int i=0;i<isbnLen;i++)
 			System.out.print("-");
 		for(int i=0;i<titleLen;i++)
 			System.out.print("-");
 		System.out.println("------------");
 		
 		System.out.print(column[0].toUpperCase());
 		for(int i=0;i<chckDateLen-column[0].length();i++)
 			System.out.print(" ");
 		System.out.print(" | ");
 		
 		System.out.print(column[1].toUpperCase());
 		for(int i=0;i<dueDateLen-column[1].length();i++)
 			System.out.print(" ");
 		System.out.print(" | ");
 		
 		System.out.print(column[2].toUpperCase());
 		for(int i=0;i<isbnLen-column[2].length();i++)
 			System.out.print(" ");
 		System.out.print(" | ");
 		
 		System.out.print(column[3].toUpperCase());
 		for(int i=0;i<titleLen-column[3].length();i++)
 			System.out.print(" ");
 		System.out.println(" | ");
 		
 		for(int i=0;i<chckDateLen;i++)
 			System.out.print("-");
 		for(int i=0;i<dueDateLen;i++)
 			System.out.print("-");
 		for(int i=0;i<isbnLen;i++)
 			System.out.print("-");
 		for(int i=0;i<titleLen;i++)
 			System.out.print("-");
 		System.out.println("------------");
 		
 		for(int i=0;i<chckDateCol.size();i++) {
 			System.out.print(chckDateCol.get(i));
 	 		for(int j=0;j<chckDateLen-chckDateCol.get(i).length();j++)
 	 			System.out.print(" ");
 	 		System.out.print(" | ");
 	 		
 	 		System.out.print(dueDateCol.get(i));
 	 		for(int j=0;j<dueDateLen-dueDateCol.get(i).length();j++)
 	 			System.out.print(" ");
 	 		System.out.print(" | ");
 	 		
 	 		System.out.print(isbnCol.get(i));
 	 		for(int j=0;j<isbnLen-isbnCol.get(i).length();j++)
 	 			System.out.print(" ");
 	 		System.out.print(" | ");
 	 		
 	 		System.out.print(titleCol.get(i));
 	 		for(int j=0;j<titleLen-titleCol.get(i).length();j++)
 	 			System.out.print(" ");
 	 		System.out.println(" | ");
 		}
 		
 		for(int i=0;i<chckDateLen;i++)
 			System.out.print("-");
 		for(int i=0;i<dueDateLen;i++)
 			System.out.print("-");
 		for(int i=0;i<isbnLen;i++)
 			System.out.print("-");
 		for(int i=0;i<titleLen;i++)
 			System.out.print("-");
 		System.out.println("------------");
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		
		isInitialized = val;
	}

}
