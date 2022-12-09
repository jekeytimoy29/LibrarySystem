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
		// TODO Auto-generated method stub
//		bframe = new JFrame();
//		bframe.getContentPane().setForeground(new Color(255, 255, 255));
		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
//		panel.setBackground(new Color(233, 150, 122));
		panel.setBounds(0, 6, 594, 460);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 46, 582, 408);
		panel.add(scrollPane);
		
		table = new JTable();
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int r = table.getSelectedRow();
//				idtf.setText(model.getValueAt(r, 0).toString());
//				nametf.setText(model.getValueAt(r, 1).toString());
//				authtf.setText(model.getValueAt(r, 2).toString());
//				
//			}
//		});
//		table.setBackground(new Color(255, 240, 245));
		
		// setData
		model = new DefaultTableModel();
		String[] column = {"ISBN", "Title", "Authors", "Max Checkout", "Copies"};
		model.setColumnIdentifiers(column);
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
		table.setModel(model);
		scrollPane.setViewportView(table);
		table.setEnabled(false);
		
		JButton btnadd = new JButton("Add New Book");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				BookAddWindow.INSTANCE.init();
				BookAddWindow.INSTANCE.setVisible(true);
				Util.centerFrameOnDesktop(BookAddWindow.INSTANCE);
				dispose();
			}
		});
		btnadd.setBounds(453, 6, 135, 29);
		panel.add(btnadd);
		
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
		
		JButton btnAddBookCopy = new JButton("Add Book Copy");
		btnAddBookCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				BookAddCopyWindow.INSTANCE.init();
				BookAddCopyWindow.INSTANCE.setVisible(true);
				Util.centerFrameOnDesktop(BookAddCopyWindow.INSTANCE);
				dispose();
			}
		});
		btnAddBookCopy.setBounds(320, 6, 135, 29);
		panel.add(btnAddBookCopy);
		
		setTitle("Book Management");
		
		isInitialized = true;
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
