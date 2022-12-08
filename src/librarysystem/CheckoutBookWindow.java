package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.GridLayout;

public class CheckoutBookWindow extends JFrame implements LibWindow {
	public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();

	private JPanel contentPane;
	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;

	private JPanel topPanel;
	private JPanel lowerPanel;
	private JPanel middlePanel;
	private JPanel leftMiddlePanel;
	private JPanel rightMiddlePanel;

	private JTextField searchBook;
	private JLabel label;
	private JButton checkoutBookButton;
	private JTable booksTable;

	/**
	 * Create the frame.
	 */
	public CheckoutBookWindow() {
	}

	@Override
	public void init() {
		formatContentPane();

		setSize(660, 500);
		isInitialized = true;
	}

	private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 1));

		defineUpperHalf();
		defineMiddleHalf();
		defineLowerHalf();

		mainPanel.add(upperHalf, BorderLayout.NORTH);
		mainPanel.add(middleHalf, BorderLayout.CENTER);
		mainPanel.add(lowerHalf, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);
		pack();

	}

	private void defineUpperHalf() {

		upperHalf = new JPanel();
		upperHalf.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		upperHalf.add(topPanel, BorderLayout.NORTH);
		upperHalf.add(middlePanel, BorderLayout.CENTER);
		upperHalf.add(lowerPanel, BorderLayout.SOUTH);

	}

	private void defineMiddleHalf() {
		middleHalf = new JPanel();
		middleHalf.setLayout(new BorderLayout());
		JSeparator s = new JSeparator();
		s.setOrientation(SwingConstants.HORIZONTAL);
		// middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
		middleHalf.add(s, BorderLayout.SOUTH);

	}

	private void defineLowerHalf() {
		lowerHalf = new JPanel();
		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton backButton = new JButton("<= Back to Main");
		addBackButtonListener(backButton);
		lowerHalf.add(backButton);

	}

	private void defineTopPanel() {
		topPanel = new JPanel();
		JPanel intPanel = new JPanel(new BorderLayout());
		intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
		JLabel loginLabel = new JLabel("Search Book");
		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
		intPanel.add(loginLabel, BorderLayout.CENTER);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(intPanel);

	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		defineLeftMiddlePanel();
		defineRightMiddlePanel();
		middlePanel.add(leftMiddlePanel);
		middlePanel.add(rightMiddlePanel);
	}

	private void defineLowerPanel() {
		lowerPanel = new JPanel();
		
		booksTable = new JTable();
		lowerPanel.add(booksTable);
	}

	private void defineLeftMiddlePanel() {

		JPanel topText = new JPanel();
		JPanel bottomText = new JPanel();
		topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		searchBook = new JTextField(10);
		label = new JLabel("Search for book to checkout:");
		label.setFont(Util.makeSmallFont(label.getFont()));
		topText.add(searchBook);
		bottomText.add(label);

		leftMiddlePanel = new JPanel();
		leftMiddlePanel.setLayout(new BorderLayout());
		leftMiddlePanel.add(topText, BorderLayout.NORTH);
		leftMiddlePanel.add(bottomText, BorderLayout.CENTER);
	}

	private void defineRightMiddlePanel() {

		JPanel buttonPAnel = new JPanel();
		buttonPAnel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		checkoutBookButton = new JButton("Checkout Book");
		buttonPAnel.add(checkoutBookButton);

		rightMiddlePanel = new JPanel();
		rightMiddlePanel.setLayout(new BorderLayout());
		rightMiddlePanel.add(buttonPAnel, BorderLayout.NORTH);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
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
