package librarysystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.BookController;
import business.BookCopy;
import business.CheckoutByController;
import business.CheckoutEntry;
import business.CheckoutRecord;
import business.LibraryMember;

public class CheckoutByBookWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 1L;
	public static final CheckoutByBookWindow INSTANCE = new CheckoutByBookWindow();
	CheckoutByController cc = new CheckoutByController();
	BookController bc = new BookController();
    private boolean isInitialized = false;

	private JPanel panel;
	private JComboBox<String> bookList;
	private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    
    private HashMap<String, String> memberNameByCheckout = new HashMap<String, String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckoutByBookWindow.INSTANCE.init();
					CheckoutByBookWindow.INSTANCE.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private CheckoutByBookWindow() {}
	
	private void populateMemberNameByCheckout() {
//		List<Book> books = bc.getAllBooks();
//		for(Book b: books) {
//			memberNameByCheckout.put(b.getIsbn(), "");
//		}
		HashMap<String, LibraryMember> members = cc.getMembers();
		members.forEach((key, value) -> {
			CheckoutRecord cr = cc.getCheckout(key);
			for(CheckoutEntry ce: cr.getEntries()) {
				if(ce.getBookCopy()!=null) {
					String memberKey = ce.getBookCopy().getBook().getIsbn()+"="+ce.getBookCopy().getCopyNum();
					String memberVal = (value.getFirstName()+" "+value.getLastName())+"="+(ce.getDueDate().toString());
					memberNameByCheckout.put(memberKey, memberVal);
				}
			}
		});
	}
	
	@Override
	public void init() {
		if(!isInitialized) initUi();
		initData();
	}
	
	public void initData() {
		populateMemberNameByCheckout();
		
		List<Book> books = bc.getAllBooks();
		List<String> booksKey = new ArrayList<>();
		for(Book b:books) {
			booksKey.add(b.getIsbn()+": "+b.getTitle());
		}
		Collections.sort(booksKey);
		DefaultComboBoxModel<String> modelCb = (DefaultComboBoxModel<String>) bookList.getModel();
        modelCb.removeAllElements();
        for (String item : booksKey) {
            modelCb.addElement(item);
        }
        bookList.setModel(modelCb);
        
        model.setRowCount(0);
		populateRecord(bookList.getSelectedItem().toString().split(": ")[0]);
	}
	
	public void initUi() {
		// TODO Auto-generated method stub
		setTitle("Checkout Record By Book");

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
		
		JLabel lblBook = new JLabel("Books");
		lblBook.setBounds(320, 6, 135, 29);
		panel.add(lblBook);
     
        bookList = new JComboBox<String>();
        bookList.setBounds(385, 6, 205, 29);
        bookList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bookList.getSelectedItem()!=null) {
					model.setRowCount(0);
					populateRecord(bookList.getSelectedItem().toString().split(": ")[0]);
				}
			}
		});
        panel.add(bookList);
        
        // setData
     	table = new JTable();
     	model = new DefaultTableModel();
     	String[] column = {"ISBN", "Title", "Copy Number", "Checked Out By", "Due Date"};
     	model.setColumnIdentifiers(column);
     	table.setModel(model);
     	scrollPane.setViewportView(table);
     	table.setEnabled(false);
     	
        isInitialized = true;
	}
	
	public void populateRecord(String isbn) {
		List<Integer> hglRow = new ArrayList<Integer>();
		Integer counter = 0;
 		Book book = bc.getBook(isbn);
 		if(book!=null) {
	 		Arrays.sort(book.getCopies(), new Comparator<BookCopy>() {
				@Override
				public int compare(BookCopy o1, BookCopy o2) {
					// TODO Auto-generated method stub
					return o1.getCopyNum() - o2.getCopyNum();
				}
	 		});
	 		for(BookCopy bc: book.getCopies()) {
	 			//String memberName = bc.getCheckoutEntry()!=null?(bc.getCheckoutEntry().getCheckoutRecord().getMember().getFirstName()+" "+bc.getCheckoutEntry().getCheckoutRecord().getMember().getLastName()):"";
	 			String memberKey = bc.getBook().getIsbn()+"="+bc.getCopyNum();
	 			String memberName = memberNameByCheckout.get(memberKey)!=null?memberNameByCheckout.get(memberKey).split("=")[0]:"";
	 			String memberDueDate = memberNameByCheckout.get(memberKey)!=null?memberNameByCheckout.get(memberKey).split("=")[1]:"";
	 			if (!memberDueDate.equals("") && LocalDate.parse(memberDueDate).isBefore(LocalDate.now())) {
	 				hglRow.add(counter);
	 				//memberDueDate += " (Overdue)";
	 			}
	 			model.insertRow(counter++, new Object[] { bc.getBook().getIsbn(), bc.getBook().getTitle(), bc.getCopyNum(), memberName, memberDueDate });
	 		}
 		}
 		
 		for(Integer i: hglRow) {
 			table.addRowSelectionInterval(i, i);
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
