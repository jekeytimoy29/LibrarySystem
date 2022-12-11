package librarysystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.Author;
import business.Book;
import business.BookController;
import business.ControllerInterface;
import business.SystemController;

public class BookManagementWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 1L;
	public static final BookManagementWindow INSTANCE = new BookManagementWindow();
	BookController bc = new BookController();
    private boolean isInitialized = false;
    
    DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    
	private BookManagementWindow() {}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookManagementWindow.INSTANCE.init();
					//window.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void init() {
		if(!isInitialized) initUi();
		populateData();
	}
	
	public void initUi() {
		// TODO Auto-generated method stub
		setTitle("Book Management");
		
		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 6, 594, 460);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 46, 582, 408);
		panel.add(scrollPane);
		
		// setData
		table = new JTable();
		model = new DefaultTableModel();
		String[] column = {"ISBN", "Title", "Authors", "Max Checkout", "Copies"};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		table.setEnabled(false);
		populateData();
		
		JButton btnadd = new JButton("Add New Book");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				BookAddWindow.INSTANCE.init();
				Util.centerFrameOnDesktop(BookAddWindow.INSTANCE);
				BookAddWindow.INSTANCE.setVisible(true);
			}
		});
		btnadd.setBounds(453, 6, 135, 29);
		panel.add(btnadd);
		
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
		
		JButton btnAddBookCopy = new JButton("Add Book Copy");
		btnAddBookCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				BookAddCopyWindow.INSTANCE.init();
				Util.centerFrameOnDesktop(BookAddCopyWindow.INSTANCE);
				BookAddCopyWindow.INSTANCE.setVisible(true);
			}
		});
		btnAddBookCopy.setBounds(320, 6, 135, 29);
		panel.add(btnAddBookCopy);
		
		isInitialized = true;
	}
	
	public void populateData() {
		model.setRowCount(0);
		List<Book> books = bc.getAllBooks();
		Collections.sort(books, new Comparator<Book>() {
		      @Override
		      public int compare(final Book b1, final Book b2) {
		          return b2.getIsbn().compareTo(b1.getIsbn());
		      }
		});
		for(Book b: books) {
			String author = "";
			for(Author a: b.getAuthors()) {
				if(!author.equals("")) author += ", ";
				author += a.getFirstName() + " " + a.getLastName();
			}
			model.insertRow(0, new Object[] { b.getIsbn(), b.getTitle(), author, b.getMaxCheckoutLength(), b.getNumCopies() });
		}
		model.fireTableDataChanged();
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
