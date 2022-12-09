package librarysystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

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
import business.LibrarySystemException;
import business.MemberController;
import business.SystemController;

public class AdminManagmentWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	MemberController mc = new MemberController();
	SystemController sc = new SystemController();
	JFrame bframe;
	DefaultTableModel model;
	private JTextField txtFname;
	private JTextField txtLname;
	private JTextField txtTel;
	private JTextField txtStreet;
	private JTextField txtCity;
	private JTextField txtState;
	private JTextField txtZip;
	private JTable table;
	private JScrollPane scrollPane;

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

		txtFname = new JTextField();
		txtFname.setBounds(79, 6, 161, 26);
		panel.add(txtFname);
		txtFname.setColumns(10);

		txtLname = new JTextField();
		txtLname.setBounds(79, 39, 161, 26);
		panel.add(txtLname);
		txtLname.setColumns(10);

		txtTel = new JTextField();
		txtTel.setBounds(79, 73, 161, 26);
		panel.add(txtTel);
		txtTel.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 207, 582, 234);
		panel.add(scrollPane);

		table = new JTable();
		// .split("\\(")
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int r = table.getSelectedRow();
				JOptionPane.showMessageDialog(null,
						model.getValueAt(r, 3).toString().split("\\,")[0]
								.split("\\("));
				txtFname.setText(model.getValueAt(r, 0).toString());
				txtLname.setText(model.getValueAt(r, 1).toString());
				txtTel.setText(model.getValueAt(r, 2).toString());
				txtStreet.setText(
						model.getValueAt(r, 3).toString().split(",")[0]);
				txtCity.setText(
						model.getValueAt(r, 3).toString().split(",")[1]);
				txtZip.setText(model.getValueAt(r, 3).toString().split(",")[2]);
			}
		});
		table.setBackground(new Color(255, 240, 245));
		model = new DefaultTableModel();
		String[] row = new String[4];
		Random rand = new Random();
		String id = String.format("%04d", rand.nextInt(10000));
		String[] column = {"First Name", "Last Name", "Telephone", "City"};
		model.setColumnIdentifiers(column);
		List<LibraryMember> librarymembers = mc.getAllMembers();
		for (LibraryMember lib : librarymembers) {
			model.insertRow(0, new Object[]{lib.getFirstName(),
					lib.getLastName(), lib.getTelephone(), lib.getAddress()});
		}
		table.setModel(model);
		scrollPane.setViewportView(table);

		JButton btnadd = new JButton("Add");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtLname.getText().equals("") || txtTel.getText().equals("")
						|| txtStreet.getText().equals("")
						|| txtCity.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Please fill all the fields");
				} else {
					// add the entered inputs to the table
					try {
						mc.addMember(txtFname.getText(), txtLname.getText(),
								txtTel.getText(), txtStreet.getText(),
								txtCity.getText(), txtState.getText(),
								txtZip.getText(), id);
					} catch (LibrarySystemException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					row[0] = txtFname.getText();
					row[1] = txtLname.getText();
					row[2] = txtTel.getText();
					row[3] = txtStreet.getText();
					model.addRow(row);
					JOptionPane.showMessageDialog(null, "Added Successfully");
					model.fireTableDataChanged();
					txtFname.setText("");
					txtLname.setText("");
					txtTel.setText("");
					txtStreet.setText("");
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
					mc.deleteMember(model.getValueAt(r, 0).toString());
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
					model.setValueAt(txtFname.getText(), r, 0);
					model.setValueAt(txtLname.getText(), r, 1);
					model.setValueAt(txtTel.getText(), r, 2);
					model.setValueAt(txtStreet.getText(), r, 3);
					try {
						sc.getAndUpdateMember(model.getValueAt(r, 0).toString(),
								txtFname.getText(), txtLname.getText(),
								txtTel.getText());
					} catch (LibrarySystemException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					model.fireTableDataChanged();
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
				txtFname.setText("");
				txtLname.setText("");
				txtTel.setText("");
				txtStreet.setText("");
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

		txtStreet = new JTextField();
		txtStreet.setColumns(10);
		txtStreet.setBounds(79, 101, 161, 26);
		panel.add(txtStreet);

		txtCity = new JTextField();
		txtCity.setColumns(10);
		txtCity.setBounds(79, 134, 161, 26);
		panel.add(txtCity);

		txtState = new JTextField();
		txtState.setColumns(10);
		txtState.setBounds(79, 169, 161, 26);
		panel.add(txtState);

		txtZip = new JTextField();
		txtZip.setColumns(10);
		txtZip.setBounds(286, 6, 161, 26);
		panel.add(txtZip);
	}
}
