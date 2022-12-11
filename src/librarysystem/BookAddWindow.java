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
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    
    private JPanel panel;
    private JScrollPane scrollPane;
    private JTextField idtf;
    private JTextField nametf;
//    private JTextField maxtf;
    private JComboBox<String> maxList;
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
	
	private boolean validateForm() {
		if(idtf.getText().trim().equals("") || nametf.getText().trim().equals("") || copytf.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Please fill all the fields");
			return false;
		}
		try {
			Integer.parseInt(copytf.getText().trim());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please fill num of copies field with numeric value");
			return false;
		}
		
		for(int i=0;i<numOfAuthor;i++) {
			if(txtFirstName.get(i).getText().trim().equals("") || txtLastName.get(i).getText().trim().equals("") 
					|| txtTelephone.get(i).getText().trim().equals("") || txtStreet.get(i).getText().trim().equals("")
					|| txtCity.get(i).getText().trim().equals("") || txtState.get(i).getText().trim().equals("")
					|| txtZip.get(i).getText().trim().equals("") || txaBio.get(i).getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill all the fields");
				return false;
			}
			
			if(!Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$").matcher(txtTelephone.get(i).getText().trim()).matches()) {
				JOptionPane.showMessageDialog(null, "Please fill telephone field with correct format");
				return false;
			}
			
			if(!(txtZip.get(i).getText().trim().length()==5 || txtZip.get(i).getText().trim().length()==6)) {
				JOptionPane.showMessageDialog(null, "Please fill zip field with correct format");
				return false;
			}
		}
		
		return true;
	}

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
	
	private void cleanPanel() {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		txtFirstName.clear();
		txtLastName.clear();
		txtTelephone.clear();
		txtStreet.clear();
		txtCity.clear();
		txtState.clear();
		txtZip.clear();
		txaBio.clear();
		numOfAuthor = 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		if(isInitialized) {
			cleanPanel();
		} else {
			panel = new JPanel();
			scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			getContentPane().add(scrollPane);
		}
		
		setTitle("Add New Book");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(new GridLayout(0, 4, 20, 0));
		
		JLabel idLabel = new JLabel("ISBN");
		panel.add(idLabel);
		idtf = new JTextField();
		idtf.setColumns(10);
		panel.add(idtf);
		
		JLabel nameLabel = new JLabel("Title");
		panel.add(nameLabel);
		nametf = new JTextField();
		nametf.setColumns(10);
		panel.add(nametf);
		
		JLabel maxLabel = new JLabel("Max Checkout");
		panel.add(maxLabel);
//		maxtf = new JTextField();
//		maxtf.setColumns(10);
//		panel.add(maxtf);
		maxList = new JComboBox<String>(new String[]{ "21 Days", "7 Days" });
        panel.add(maxList);
		
		JLabel copiesLabel = new JLabel("Num of Copies");
		panel.add(copiesLabel);
		copytf = new JTextField();
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
				Util.centerFrameOnDesktop(BookManagementWindow.INSTANCE);
				BookManagementWindow.INSTANCE.setVisible(true);
			}
		});
		panel.add(btnback);
		
		JButton btnAddMoreAuthor = new JButton("Add More Author");
		btnAddMoreAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAuthor();
				panel.revalidate();
				panel.repaint();
			}
		});
		panel.add(btnAddMoreAuthor);
				
		JButton btnadd = new JButton("Add");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validateForm()) {
					// add the entered inputs to the table
					List<Author> authors = new ArrayList<Author>();
					for(int i=0;i<txtFirstName.size();i++) {
						authors.add(new Author(txtFirstName.get(i).getText(), txtLastName.get(i).getText(), txtTelephone.get(i).getText(), new Address(txtStreet.get(i).getText(), txtCity.get(i).getText(), txtState.get(i).getText(), txtZip.get(i).getText()), txaBio.get(i).getText()));
					}
					Book book = new Book(idtf.getText(), nametf.getText(), maxList.getSelectedItem().toString()=="7 Days"?7:21, authors);
					for(int i=1;i<Integer.parseInt(copytf.getText());i++) {
						book.addCopy();
					}
					bc.addBook(book);
						
					JOptionPane.showMessageDialog(null, "Added Successfully");
					LibrarySystem.hideAllWindows();
					BookManagementWindow.INSTANCE.populateData();
					Util.centerFrameOnDesktop(BookManagementWindow.INSTANCE);
					BookManagementWindow.INSTANCE.setVisible(true);
				}
			}
		});
		panel.add(btnadd);

		addAuthor();
		
		pack();

		isInitialized = true;
	}
	
	private void addAuthor() {
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		
		JSeparator separator = new JSeparator();
		panel.add(separator);
		JLabel lblAuthor = new JLabel("Author " + (numOfAuthor+1));
		lblAuthor.setVerticalAlignment(SwingConstants.TOP);
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblAuthor);
		JSeparator separator1 = new JSeparator();
		panel.add(separator1);
		JSeparator separator2 = new JSeparator();
		panel.add(separator2);
		
		JLabel lblFirstName = new JLabel("First Name");
		panel.add(lblFirstName);
		JTextField txtFirstName = new JTextField();
		txtFirstName.setColumns(10);
		panel.add(txtFirstName);
		this.txtFirstName.add(txtFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		panel.add(lblLastName);
		JTextField txtLastName = new JTextField();
		txtLastName.setColumns(10);
		panel.add(txtLastName);
		this.txtLastName.add(txtLastName);
		
		JLabel lblTelephone = new JLabel("Telephone");
		panel.add(lblTelephone);
		JTextField txtTelephone = new JTextField();
		txtTelephone.setColumns(10);
		panel.add(txtTelephone);
		this.txtTelephone.add(txtTelephone);
		
		JLabel lblBio = new JLabel("Bio");
		panel.add(lblBio);
		JTextArea txaBio = new JTextArea();
		txaBio.setColumns(10);
		panel.add(txaBio);
		this.txaBio.add(txaBio);
		
		JLabel lblStreet = new JLabel("Street Address");
		panel.add(lblStreet);
		JTextField txtStreet = new JTextField();
		txtStreet.setColumns(10);
		panel.add(txtStreet);
		this.txtStreet.add(txtStreet);
		
		JLabel lblCity = new JLabel("City");
		panel.add(lblCity);
		JTextField txtCity = new JTextField();
		txtCity.setColumns(10);
		panel.add(txtCity);
		this.txtCity.add(txtCity);
		
		JLabel lblState = new JLabel("State");
		panel.add(lblState);
		JTextField txtState = new JTextField();
		txtState.setColumns(10);
		panel.add(txtState);
		this.txtState.add(txtState);
		
		JLabel lblZip = new JLabel("ZIP");
		panel.add(lblZip);
		JTextField txtZip = new JTextField();
		txtZip.setColumns(10);
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
