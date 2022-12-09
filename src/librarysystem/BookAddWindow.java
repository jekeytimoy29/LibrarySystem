package librarysystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.Address;
import business.Author;
import business.Book;
import business.BookController;
import business.ControllerInterface;
import business.SystemController;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class BookAddWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 1L;
	public static final BookAddWindow INSTANCE = new BookAddWindow();
	BookController bc = new BookController();
    private boolean isInitialized = false;
    private int numOfAuthor = 0;
    private final int authArea = 240;
    
    private JPanel panel;
    private JTextField idtf;
    private JTextField nametf;
    private JTextField maxtf;
    private JTextField copytf;
    
    private List<JTextField> txtFirstName = new ArrayList<JTextField>();
    private List<JTextField> txtLastName = new ArrayList<JTextField>();
    private List<JTextField> txtTelephone = new ArrayList<JTextField>();
    private List<JTextField> txtStreet = new ArrayList<JTextField>();
    private List<JTextField> txtCity = new ArrayList<JTextField>();
    private List<JTextField> txtState = new ArrayList<JTextField>();
    private List<JTextField> txtZip = new ArrayList<JTextField>();
    private List<JTextArea> txaBio = new ArrayList<JTextArea>();
    
	private BookAddWindow() {}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookAddWindow.INSTANCE.init();
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
		//bframe.getContentPane().setForeground(new Color(255, 255, 255));
		//bframe.setBounds(100, 100, 600, 570);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//bframe.getContentPane().setLayout(null);
		
		panel = new JPanel();
		//panel.setBackground(new Color(233, 150, 122));
		//panel.setBounds(0, 6, 594, 532);
		//panel.setPreferredSize(panel.getPreferredSize());
		panel.setLayout(new GridLayout(0, 4, 20, 0));
		
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   
		
		//panel.setPreferredSize(new Dimension(100, 100));
  
        getContentPane().add(scrollPane);
		
		
		//bframe.getContentPane().add(panel);
		
		JLabel idLabel = new JLabel("ISBN");
		//idLabel.setBounds(6, 20, 61, 16);
		panel.add(idLabel);
		idtf = new JTextField();
		//idtf.setBounds(119, 15, 130, 26);
		idtf.setColumns(10);
		panel.add(idtf);
		
		JLabel nameLabel = new JLabel("Title");
		//nameLabel.setBounds(6, 48, 61, 27);
		panel.add(nameLabel);
		nametf = new JTextField();
		//nametf.setBounds(119, 48, 351, 26);
		nametf.setColumns(10);
		panel.add(nametf);
		
		JLabel maxLabel = new JLabel("Max Checkout");
		//maxLabel.setBounds(6, 86, 117, 26);
		panel.add(maxLabel);
		maxtf = new JTextField();
		//maxtf.setBounds(119, 86, 70, 26);
		maxtf.setColumns(10);
		panel.add(maxtf);
		
		JLabel copiesLabel = new JLabel("Num of Copies");
//		copiesLabel.setBounds(6, 120, 101, 16);
		panel.add(copiesLabel);
		copytf = new JTextField();
//		copytf.setBounds(119, 115, 70, 26);
		copytf.setColumns(10);
		panel.add(copytf);
		panel.add(new JLabel());
		
		panel.add(new JLabel());
		panel.add(new JLabel());
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
//		btnback.setBounds(471, 15, 117, 29);
		panel.add(btnback);
		
		JButton btnAddMoreAuthor = new JButton("Add More Author");
		btnAddMoreAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAuthor();
				panel.revalidate();
				panel.repaint();
			}
		});
//		btnAddMoreAuthor.setBounds(327, 138, 143, 27);
		panel.add(btnAddMoreAuthor);
				
		JButton btnadd = new JButton("Add");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idtf.getText().equals("") || nametf.getText().equals("") || maxtf.getText().equals("") || copytf.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill all the fields");
				}
				else {
				// add the entered inputs to the table
				List<Author> authors = new ArrayList<Author>();
				for(int i=0;i<txtFirstName.size();i++) {
					authors.add(new Author(txtFirstName.get(i).getText(), txtLastName.get(i).getText(), txtTelephone.get(i).getText(), new Address(txtStreet.get(i).getText(), txtCity.get(i).getText(), txtState.get(i).getText(), txtZip.get(i).getText()), txaBio.get(i).getText()));
				}
				Book book = new Book(idtf.getText(), nametf.getText(), Integer.parseInt(maxtf.getText()), authors);
				for(int i=1;i<Integer.parseInt(copytf.getText());i++) {
					book.addCopy();
				}
				bc.addBook(book);
					
				JOptionPane.showMessageDialog(null, "Added Successfully");
				LibrarySystem.hideAllWindows();
				BookManagementWindow.INSTANCE.init();
				BookManagementWindow.INSTANCE.setVisible(true);
				Util.centerFrameOnDesktop(BookManagementWindow.INSTANCE);
				// clear all the text fields
			    idtf.setText("");
			    nametf.setText("");
			    maxtf.setText("");
			    copytf.setText("");
				}
				
			}
		});
		//btnadd.setBounds(471, 138, 117, 27);
		panel.add(btnadd);
//		
//		JLabel lblDays = new JLabel("Day(s)");
//		lblDays.setBounds(190, 87, 117, 26);
//		panel.add(lblDays);
//		
//		
		addAuthor();
//		//addAuthor();
//		
		isInitialized = true;
		
		pack();
		
		setTitle("Add New Book");
	}
	
	private void addAuthor() {
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		
		JSeparator separator = new JSeparator();
//		separator.setBounds(6, 174+y, 582, 12);
		panel.add(separator);
		JLabel lblAuthor = new JLabel("Author " + (numOfAuthor+1));
		lblAuthor.setVerticalAlignment(SwingConstants.TOP);
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
//		lblAuthor.setBounds(6, 163+y, 101, 16);
		panel.add(lblAuthor);
		JSeparator separator1 = new JSeparator();
//		separator.setBounds(6, 174+y, 582, 12);
		panel.add(separator1);
		JSeparator separator2 = new JSeparator();
//		separator.setBounds(6, 174+y, 582, 12);
		panel.add(separator2);
		
//		int y = authArea*numOfAuthor;
		JLabel lblFirstName = new JLabel("First Name");
//		lblFirstName.setBounds(6, 198+y, 101, 16);
		panel.add(lblFirstName);
		JTextField txtFirstName = new JTextField();
		txtFirstName.setColumns(10);
//		txtFirstName.setBounds(119, 193+y, 170, 26);
		panel.add(txtFirstName);
		this.txtFirstName.add(txtFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
//		lblLastName.setBounds(6, 231+y, 101, 16);
		panel.add(lblLastName);
		JTextField txtLastName = new JTextField();
		txtLastName.setColumns(10);
//		txtLastName.setBounds(119, 226+y, 170, 26);
		panel.add(txtLastName);
		this.txtLastName.add(txtLastName);
		
		JLabel lblTelephone = new JLabel("Telephone");
//		lblTelephone.setBounds(6, 264+y, 101, 16);
		panel.add(lblTelephone);
		JTextField txtTelephone = new JTextField();
		txtTelephone.setColumns(10);
//		txtTelephone.setBounds(119, 259+y, 170, 26);
		panel.add(txtTelephone);
		this.txtTelephone.add(txtTelephone);
		
		JLabel lblBio = new JLabel("Bio");
//		lblBio.setBounds(6, 297+y, 61, 16);
		panel.add(lblBio);
		JTextArea txaBio = new JTextArea();
//		txaBio.setBounds(119, 297+y, 170, 87);
		txaBio.setColumns(10);
//		txaBio.setRows(2);
		panel.add(txaBio);
		this.txaBio.add(txaBio);
		
		JLabel lblStreet = new JLabel("Street Address");
//		lblStreet.setBounds(301, 198+y, 61, 16);
		panel.add(lblStreet);
		JTextField txtStreet = new JTextField();
		txtStreet.setColumns(10);
//		txtStreet.setBounds(374, 193+y, 201, 26);
		panel.add(txtStreet);
		this.txtStreet.add(txtStreet);
		
		JLabel lblCity = new JLabel("City");
//		lblCity.setBounds(301, 231+y, 61, 16);
		panel.add(lblCity);
		JTextField txtCity = new JTextField();
		txtCity.setColumns(10);
//		txtCity.setBounds(374, 226+y, 201, 26);
		panel.add(txtCity);
		this.txtCity.add(txtCity);
		
		JLabel lblState = new JLabel("State");
//		lblState.setBounds(301, 264+y, 61, 16);
		panel.add(lblState);
		JTextField txtState = new JTextField();
		txtState.setColumns(10);
//		txtState.setBounds(374, 259+y, 201, 26);
		panel.add(txtState);
		this.txtState.add(txtState);
		
		JLabel lblZip = new JLabel("ZIP");
//		lblZip.setBounds(301, 292+y, 61, 16);
		panel.add(lblZip);
		JTextField txtZip = new JTextField();
		txtZip.setColumns(10);
//		txtZip.setBounds(374, 292+y, 201, 26);
		panel.add(txtZip);
		this.txtZip.add(txtZip);
		
		numOfAuthor++;
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
