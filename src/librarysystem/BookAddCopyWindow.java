package librarysystem;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
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
	
	private boolean validateForm() {
		if(copytf.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Please fill all the fields");
			return false;
		}
		try {
			Integer.parseInt(copytf.getText().trim());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please fill copies field with numeric value");
			return false;
		}
				
		return true;
	}

	@Override
	public void init() {
		if(!isInitialized) initUi();
		initData();
	}
	
	public void initData() {
		List<String> isbns = bc.allBookIds();
		Collections.sort(isbns);
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) isbnList.getModel();
        model.removeAllElements();
        for (String item : isbns) {
            model.addElement(item);
        }
        isbnList.setModel(model);
        
        copytf.setText("1");
	}
	
	public void initUi() {
		// TODO Auto-generated method stub
		setTitle("Add Book Copy");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 20, 0));
		
        getContentPane().add(panel);
		
		JLabel lblIsbn = new JLabel("ISBN");
		panel.add(lblIsbn);
     
        isbnList = new JComboBox<String>();
        panel.add(isbnList);
        
        JLabel copyLabel = new JLabel("Number of Copies");
		panel.add(copyLabel);
		copytf = new JTextField();
		copytf.setColumns(10);
		panel.add(copytf);
		
		panel.add(new JLabel());
		panel.add(new JLabel());
		
		JButton btnback = new JButton("Back");
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				Util.centerFrameOnDesktop(BookManagementWindow.INSTANCE);
				BookManagementWindow.INSTANCE.setVisible(true);
			}
		});
		panel.add(btnback);
		
		JButton btnadd = new JButton("Add Copies");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validateForm()) {
					Book book = bc.getBook(isbnList.getSelectedItem().toString());
					for(int i=0;i<Integer.parseInt(copytf.getText());i++) {
						book.addCopy();
					}
					bc.addBook(book);
						
					JOptionPane.showMessageDialog(null, "Added Successfully");
					copytf.setText("");
					isbnList.setSelectedIndex(0);
					LibrarySystem.hideAllWindows();
					BookManagementWindow.INSTANCE.populateData();
					Util.centerFrameOnDesktop(BookManagementWindow.INSTANCE);
					BookManagementWindow.INSTANCE.setVisible(true);
				}
			}
		});
		panel.add(btnadd);
		
		pack();
        
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
