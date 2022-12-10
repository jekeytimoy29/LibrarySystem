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
				LibrarySystem.INSTANCE.init();
    			LibrarySystem.INSTANCE.setVisible(true);
    			Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
    			dispose();
			}
		});
		btnback.setBounds(6, 5, 117, 29);
		panel.add(btnback);
		
		HashMap<String, LibraryMember> members = cc.getMembers();
		List<String> membersKey = new ArrayList<>();
		members.forEach((key, value) -> {
			membersKey.add(key+" - "+value.getFirstName()+" "+value.getLastName());
		});
		Collections.sort(membersKey);
		
		JLabel lblMember = new JLabel("Members");
		lblMember.setBounds(320, 6, 135, 29);
		panel.add(lblMember);
     
        memberList = new JComboBox<String>(new Vector<String>(membersKey));
        memberList.setBounds(385, 6, 205, 29);
        memberList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				populateRecord(memberList.getSelectedItem().toString().split(" - ")[0]);
			}
		});
        panel.add(memberList);
        
        // setData
     	table = new JTable();
     	model = new DefaultTableModel();
     	String[] column = {"Checkout Date", "Due Date", "ISBN", "Title"};
     	model.setColumnIdentifiers(column);
     	table.setModel(model);
     	scrollPane.setViewportView(table);
     	table.setEnabled(false);
        
     	populateRecord(memberList.getSelectedItem().toString().split(" - ")[0]);
     	
        setVisible(true);
        
        isInitialized = true;
		
	}
	
	public void populateRecord(String memberId) {
 		CheckoutRecord cr = cc.getCheckout(memberId);
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
	 		}
 		}
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
