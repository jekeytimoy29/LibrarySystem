package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;

public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE = new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
	JMenu options;
	JMenuItem login, allBookIds, bookMgmt, memberMgmt, checkoutBook;
	String pathToImage;
	private boolean isInitialized = false;

	private static LibWindow[] allWindows = {LibrarySystem.INSTANCE,
			LoginWindow.INSTANCE, MemberManagementWindow.INSTANCE,
			BookManagementWindow.INSTANCE, AllBookIdsWindow.INSTANCE,
			CheckoutBookWindow.INSTANCE};

	public static void hideAllWindows() {
		for (LibWindow frame : allWindows) {
			frame.setVisible(false);
		}
	}

	private LibrarySystem() {
	}

	public void init() {
		formatContentPane();
		setPathToImage();
		insertSplashImage();

		createMenus();
		// pack();
		setSize(660, 500);
		isInitialized = true;
	}

	private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 1));
		getContentPane().add(mainPanel);
	}

	private void setPathToImage() {
		String currDirectory = System.getProperty("user.dir");
		pathToImage = currDirectory + "/src/librarysystem/library.jpg";
	}

	private void insertSplashImage() {
		ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));
	}
	private void createMenus() {
		menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);
	}

	private void addMenuItems() {
		options = new JMenu("Options");
		menuBar.add(options);
		login = new JMenuItem("Login/Logout");
		login.addActionListener(new LoginListener());
		checkoutBook = new JMenuItem("Checkout Book");
		checkoutBook.addActionListener(new CheckoutBookListener());
		memberMgmt = new JMenuItem("Member Management");
		memberMgmt.addActionListener(new MemberManagementListener());
		bookMgmt = new JMenuItem("Book Management");
		bookMgmt.addActionListener(new BookManagementListener());
		options.add(login);

		if (SystemController.currentAuth == Auth.LIBRARIAN
				|| SystemController.currentAuth == Auth.BOTH)
			options.add(checkoutBook);

		if (SystemController.currentAuth == Auth.ADMIN
				|| SystemController.currentAuth == Auth.BOTH)
			options.add(bookMgmt);

		options.add(memberMgmt);
	}

	class BookManagementListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			BookManagementWindow.INSTANCE.init();

			Util.centerFrameOnDesktop(BookManagementWindow.INSTANCE);
			// BookManagementWindow.INSTANCE.setVisible(true);
		}

	}

	class CheckoutBookListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			CheckoutBookWindow.INSTANCE.init();

			Util.centerFrameOnDesktop(CheckoutBookWindow.INSTANCE);
		}

	}

	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();

			if (!LoginWindow.INSTANCE.isInitialized()) {
				LoginWindow.INSTANCE.init();
				LoginWindow.INSTANCE.setController(ci);
			}

			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);

		}

	}
	class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for (String s : ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			AllBookIdsWindow.INSTANCE.setSize(660, 500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);

		}

	}

	class AllMemberIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setVisible(true);

			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for (String s : ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setSize(660, 500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);

		}

	}

	class MemberManagementListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			MemberManagementWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(MemberManagementWindow.INSTANCE);
		}

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
