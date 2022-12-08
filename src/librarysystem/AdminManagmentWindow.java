package librarysystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.LibraryMember;
import business.MemberController;

public class AdminManagmentWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	MemberController mc = new MemberController();
	JFrame bframe;
	DefaultTableModel model;
	private JTextField idtf;
	private JTextField nametf;
	private JTextField authtf;
	private JTextField fname;
	private JTextField lname;
	private JTextField tel;
	private JTextField street;
	private JTextField city;
	private JTextField state;
	private JTextField zip;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminManagmentWindow window = new AdminManagmentWindow();
					window.bframe.setVisible(true);
					window.bframe.setTitle("Book Window");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminManagmentWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		bframe = new JFrame();
		bframe.getContentPane().setForeground(new Color(255, 255, 255));
		bframe.setBounds(100, 100, 600, 500);
		bframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bframe.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(233, 150, 122));
		panel.setBounds(0, 6, 594, 466);
		panel.setLayout(null);
		bframe.getContentPane().add(panel);

		idtf = new JTextField();
		idtf.setBounds(79, 6, 161, 26);
		panel.add(idtf);
		idtf.setColumns(10);

		fname = new JTextField();
		fname.setBounds(79, 39, 161, 26);
		panel.add(fname);
		fname.setColumns(10);

		lname = new JTextField();
		lname.setBounds(79, 73, 161, 26);
		panel.add(lname);
		lname.setColumns(10);

		tel = new JTextField();
		tel.setBounds(79, 73, 161, 26);
		panel.add(tel);
		tel.setColumns(10);

		street = new JTextField();
		street.setBounds(79, 73, 161, 26);
		panel.add(street);
		street.setColumns(10);

		city = new JTextField();
		city.setBounds(79, 73, 161, 26);
		panel.add(city);
		city.setColumns(10);

		state = new JTextField();
		state.setBounds(79, 73, 161, 26);
		panel.add(state);
		state.setColumns(10);

		zip = new JTextField();
		zip.setBounds(79, 73, 161, 26);
		panel.add(zip);
		zip.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 207, 582, 234);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int r = table.getSelectedRow();
				fname.setText(model.getValueAt(r, 0).toString());
				lname.setText(model.getValueAt(r, 1).toString());
				tel.setText(model.getValueAt(r, 2).toString());
				street.setText(model.getValueAt(r, 3).toString());
				city.setText(model.getValueAt(r, 4).toString());
				state.setText(model.getValueAt(r, 5).toString());
				zip.setText(model.getValueAt(r, 6).toString());

			}
		});
		table.setBackground(new Color(255, 240, 245));
		model = new DefaultTableModel();
		String[] column = {"First Name", "Last Name", "Telephone", "Street",
				"City", "State", "Zip"};
		String[] row = new String[7];
		model.setColumnIdentifiers(column);
		List<LibraryMember> librarymembers = mc.getAllMembers();
		for (LibraryMember lib : librarymembers) {
			model.insertRow(0,
					new Object[]{lib.getFirstName(), lib.getLastName(),
							lib.getTelephone(), lib.getMemberId(),
							lib.getAddress()});
		}
		table.setModel(model);
		scrollPane.setViewportView(table);

		JButton btnadd = new JButton("Add");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fname.getText().equals("") || lname.getText().equals("")
				 || tel.getText().equals("")
				 || street.getText().equals("")
				 || city.getText().equals("")
				 || state.getText().equals("")
				 || zip.getText().equals("")
				 || idtf.getText().equals("")
				) {
					JOptionPane.showMessageDialog(null,
							"Please fill all the fields");
				} else {
					// add the entered inputs to the table
					// row[0] = idtf.getText();
					row[0] = fname.getText();
					row[1] = lname.getText();
					row[2] = tel.getText();
					row[3] = street.getText();
					row[4] = city.getText();
					row[5] = state.getText();
					row[6] = zip.getText();
					model.addRow(row);
					JOptionPane.showMessageDialog(null, "Added Successfully");
					// clear all the text fields
					// idtf.setText("");
					fname.setText("");
					lname.setText("");
					tel.setText("");
					street.setText("");
					city.setText("");
					state.setText("");
					zip.setText("");
				}

			}
		});
		btnadd.setBounds(471, 6, 117, 29);
		panel.add(btnadd);

		JButton btndel = new JButton("Delete");
		btndel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = table.getSelectedRow();
				if (r >= 0) {
					model.removeRow(r);
					JOptionPane.showMessageDialog(null, "Deleted Successfully");

				} else {
					JOptionPane.showMessageDialog(null, "Please select a row");
				}
			}
		});
		btndel.setBounds(471, 39, 117, 29);
		panel.add(btndel);

		JButton btnupdate = new JButton("Update");
		btnupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = table.getSelectedRow();
				if (r >= 0) {
					model.setValueAt(idtf.getText(), r, 0);
					model.setValueAt(nametf.getText(), r, 1);
					model.setValueAt(authtf.getText(), r, 2);
					JOptionPane.showMessageDialog(null, "Updated Successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Please select a row");
				}
			}
		});
		btnupdate.setBounds(471, 70, 117, 29);
		panel.add(btnupdate);

		JButton btnclear = new JButton("Clear");
		btnclear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idtf.setText("");
				fname.setText("");
				lname.setText("");
				tel.setText("");
				street.setText("");
				city.setText("");
				state.setText("");
				zip.setText("");
			}
		});
		btnclear.setBounds(471, 101, 117, 29);
		panel.add(btnclear);

		JButton btnback = new JButton("Back");
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bframe.setVisible(false);
				// Main mWindow = new Main();
				// mWindow.mframe.setVisible(true);
			}
		});
		btnback.setBounds(471, 146, 117, 29);
		panel.add(btnback);

		JLabel idLabel = new JLabel("First Name");
		idLabel.setBounds(6, 11, 61, 16);
		panel.add(idLabel);

		JLabel nameLabel = new JLabel("Last Name");
		nameLabel.setBounds(6, 39, 71, 27);
		panel.add(nameLabel);

		JLabel lblTel = new JLabel("Telephone");
		lblTel.setBounds(6, 78, 61, 16);
		panel.add(lblTel);

		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(6, 106, 61, 16);
		panel.add(lblStreet);

		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(6, 144, 61, 16);
		panel.add(lblCity);

		JLabel lblState = new JLabel("State");
		lblState.setBounds(6, 174, 61, 16);
		panel.add(lblState);

		JLabel lblZip = new JLabel("Zip");
		lblZip.setBounds(252, 11, 47, 16);
		panel.add(lblZip);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(79, 104, 161, 26);
		panel.add(textField);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(79, 139, 161, 26);
		panel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(79, 169, 161, 26);
		panel.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(298, 6, 161, 26);
		panel.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(298, 6, 161, 26);
		panel.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(298, 6, 161, 26);
		panel.add(textField_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(298, 6, 161, 26);
		panel.add(textField_6);

	}
}
