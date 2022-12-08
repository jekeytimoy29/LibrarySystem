package librarysystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckoutBookWindow extends JFrame implements LibWindow{
    public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();
	
	private boolean isInitialized;

    private JPanel mainPanel;
    DefaultTableModel model;
    private JTextField memberIdField;
    private JTextField isbnField;
    private JTable table;
    private JScrollPane scrollPane;
	
	private CheckoutBookWindow() {}

	@Override
	public void init() {
		setBounds(100, 100, 600, 500);
		getContentPane().setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 6, 594, 466);
		mainPanel.setLayout(null);
		getContentPane().add(mainPanel);
		

		JLabel checkoutBookLabel = new JLabel("Checkout Book");
		checkoutBookLabel.setBounds(40, 2, 241, 26);
		Util.adjustLabelFont(checkoutBookLabel, Util.DARK_BLUE, true);
		mainPanel.add(checkoutBookLabel);
		
		JLabel idLabel = new JLabel("Member ID");
		idLabel.setBounds(36, 40, 91, 16);
		mainPanel.add(idLabel);
		
		JLabel nameLabel = new JLabel("Book ISBN No.");
		nameLabel.setBounds(36, 68, 101, 27);
		mainPanel.add(nameLabel);
		
		memberIdField = new JTextField();
		memberIdField.setBounds(129, 35, 241, 26);
		mainPanel.add(memberIdField);
		memberIdField.setColumns(10);
		
		isbnField = new JTextField();
		isbnField.setBounds(129, 68, 241, 26);
		mainPanel.add(isbnField);
		isbnField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 154, 582, 287);
		mainPanel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int r = table.getSelectedRow();
				memberIdField.setText(model.getValueAt(r, 0).toString());
				isbnField.setText(model.getValueAt(r, 1).toString());
				
			}
		});
		
		table.setBackground(new Color(255, 240, 245));
		model = new DefaultTableModel();
		String[] column = {"memberId","Book Name","Book Copy", "Checkout Date", "Due Date"};
	    String[] row = new String[2];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(memberIdField.getText().equals("")||isbnField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill all the fields");
				}
				else {
				// add the entered inputs to the table
				row[0] = memberIdField.getText();
				row[1] = isbnField.getText();
				model.addRow(row);
				JOptionPane.showMessageDialog(null, "Added Successfully");
				// clear all the text fields
			    memberIdField.setText("");
			    isbnField.setText("");
				}
				
			}
		});
		
		btnCheckout.setBounds(461, 35, 117, 29);
		mainPanel.add(btnCheckout);
		
		JButton btnDiscard = new JButton("Discard");
		btnDiscard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = table.getSelectedRow();
				if(r>=0) {
					model.removeRow(r);
					JOptionPane.showMessageDialog(null, "Deleted Successfully");
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Please select a row");
				}
			}
		});
		btnDiscard.setBounds(461, 76, 117, 29);
		mainPanel.add(btnDiscard);
		
		JButton btnclear = new JButton("Clear");
		btnclear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberIdField.setText("");
			    isbnField.setText("");
			}
		});
		btnclear.setBounds(370, 117, 117, 29);
		mainPanel.add(btnclear);
		
		JButton btnback = new JButton("< = Back to Main");
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
    			LibrarySystem.hideAllWindows();
				LibrarySystem.INSTANCE.init();
    			LibrarySystem.INSTANCE.setVisible(true);
			}
		});
		btnback.setBounds(79, 117, 147, 29);
		mainPanel.add(btnback);
		
	}
	
	
	@Override
	public boolean isInitialized() {
		return isInitialized;
	}
	
	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
}