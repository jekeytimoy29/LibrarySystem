package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import librarysystem.Main;

public class AdminBoard extends JDialog {

	JFrame bframe;
	DefaultTableModel model;
	private JTextField idtf;
	private JTextField nametf;
	private JTextField authtf;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminBoard window = new AdminBoard();
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
	public AdminBoard() {
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
		panel.setBackground(new Color(255, 192, 203));
		panel.setBounds(0, 6, 594, 466);
		panel.setLayout(null);
		bframe.getContentPane().add(panel);

		JLabel idLabel = new JLabel("Book ID");
		idLabel.setBounds(6, 20, 61, 16);
		panel.add(idLabel);

		JLabel nameLabel = new JLabel("Book Name");
		nameLabel.setBounds(6, 48, 71, 27);
		panel.add(nameLabel);

		JLabel authLabel = new JLabel("Author");
		authLabel.setBounds(6, 87, 61, 16);
		panel.add(authLabel);

		idtf = new JTextField();
		idtf.setBounds(79, 15, 161, 26);
		panel.add(idtf);
		idtf.setColumns(10);

		nametf = new JTextField();
		nametf.setBounds(79, 48, 161, 26);
		panel.add(nametf);
		nametf.setColumns(10);

		authtf = new JTextField();
		authtf.setBounds(79, 87, 161, 26);
		panel.add(authtf);
		authtf.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 154, 582, 287);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int r = table.getSelectedRow();
				idtf.setText(model.getValueAt(r, 0).toString());
				nametf.setText(model.getValueAt(r, 1).toString());
				authtf.setText(model.getValueAt(r, 2).toString());

			}
		});
		table.setBackground(new Color(255, 240, 245));
		model = new DefaultTableModel();
		String[] column = {"ID", "Book Name", "Author"};
		String[] row = new String[3];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);

		JButton btnadd = new JButton("Add");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idtf.getText().equals("") || nametf.getText().equals("")
						|| authtf.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Please fill all the fields");
				} else {
					// add the entered inputs to the table
					row[0] = idtf.getText();
					row[1] = nametf.getText();
					row[2] = authtf.getText();
					model.addRow(row);
					JOptionPane.showMessageDialog(null, "Added Successfully");
					// clear all the text fields
					idtf.setText("");
					nametf.setText("");
					authtf.setText("");
				}

			}
		});
		btnadd.setBounds(277, 15, 117, 29);
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
		btndel.setBounds(461, 15, 117, 29);
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
		btnupdate.setBounds(277, 56, 117, 29);
		panel.add(btnupdate);

		JButton btnclear = new JButton("Clear");
		btnclear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idtf.setText("");
				nametf.setText("");
				authtf.setText("");
			}
		});
		btnclear.setBounds(461, 56, 117, 29);
		panel.add(btnclear);

		JButton btnback = new JButton("Back");
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bframe.setVisible(false);
				Main mWindow = new Main();
				// mWindow.mframe.setVisible(true);
			}
		});
		btnback.setBounds(370, 107, 117, 29);
		panel.add(btnback);

	}

}
