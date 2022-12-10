package librarysystem;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import business.Address;
import business.Author;
import business.Book;
import business.BookController;

public class BookAddCopyWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 1L;
	public static final BookAddCopyWindow INSTANCE = new BookAddCopyWindow();
	BookController bc = new BookController();
    private boolean isInitialized = false;
	
    private JPanel panel;
    private JComboBox<String> isbnList;
    private JTextField copytf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookAddCopyWindow.INSTANCE.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private BookAddCopyWindow() {}

	@Override
	public void init() {
		// TODO Auto-generated method stub
//		bframe = new JFrame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//bframe.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 20, 0));
		
        getContentPane().add(panel);
        
        List<String> isbns = bc.allBookIds();
		Collections.sort(isbns);
		
		JLabel lblIsbn = new JLabel("ISBN");
		panel.add(lblIsbn);
     
        isbnList = new JComboBox<String>(new Vector<String>(isbns));
        panel.add(isbnList);
        
        JLabel copyLabel = new JLabel("Number of Copies");
		//maxLabel.setBounds(6, 86, 117, 26);
		panel.add(copyLabel);
		copytf = new JTextField();
		//maxtf.setBounds(119, 86, 70, 26);
		copytf.setColumns(10);
		copytf.setText("1");
		panel.add(copytf);
		
		panel.add(new JLabel());
		panel.add(new JLabel());
		
		JButton btnback = new JButton("Back");
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				BookManagementWindow.INSTANCE.init();
				BookManagementWindow.INSTANCE.setVisible(true);
				Util.centerFrameOnDesktop(BookManagementWindow.INSTANCE);
			}
		});
		panel.add(btnback);
		
		JButton btnadd = new JButton("Add Copies");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(copytf.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill all the fields");
				}
				else {
					// add the entered inputs to the table
					Book book = bc.getBook(isbnList.getSelectedItem().toString());
					for(int i=0;i<Integer.parseInt(copytf.getText());i++) {
						book.addCopy();
					}
					bc.addBook(book);
						
					JOptionPane.showMessageDialog(null, "Added Successfully");
					LibrarySystem.hideAllWindows();
					BookManagementWindow.INSTANCE.init();
					BookManagementWindow.INSTANCE.setVisible(true);
					Util.centerFrameOnDesktop(BookManagementWindow.INSTANCE);
					// clear all the text fields
				    copytf.setText("");
				}
				
			}
		});
		panel.add(btnadd);
        
        isInitialized = true;
		
		pack();
		
		setTitle("Add Book Copy");
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
