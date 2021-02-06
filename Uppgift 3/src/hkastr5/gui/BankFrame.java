package hkastr5.gui;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen BankFrame.  //TO-DO
 * 
 */
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;


/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen Account hanterar gemensamma aspekter av olika kontotyper i banken.
 * 
 */

import hkastr5.logic.*;

public class BankFrame extends JFrame{
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 500;
	
	private final int TEXT_WIDTH = 15;

	private BankLogic bank;

	// GUI-komponenter som hör till kundpanelen. 
	private JPanel customerPanel;
	private JList customerList;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField pNoField;

	// GUI-komponenter som hör till kontopanelen.
	JPanel accountPanel;
	private JList accountList;
	private JTextField amountField;
	private JTextField balanceField;

	public BankFrame(BankLogic bank) {
		this.bank = bank;
		createComponents();

		setLocationRelativeTo(null);
	}

	private void createComponents() {

		// Skapa vänster (kund) och höger (konto) delpanel.
		customerPanel = new JPanel(new GridLayout(2, 1));
		customerPanel.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));

		accountPanel = new JPanel(new GridLayout(2, 1));
		accountPanel.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));

		// Skapa huvudpanelen.
		JPanel framePanel = new JPanel(new BorderLayout());

		// Skapa innehållet i kundpanelen och lägg till den i huvudpanelen.
		createCustomerPanel();		
		framePanel.add(customerPanel, BorderLayout.WEST);

		// Skapa innehållet i kontopanelen och lägg till den i huvudpanelen.
		createAccountPanel();		
		framePanel.add(accountPanel, BorderLayout.EAST);

		//	Skapa menyerna
		createMenuBar();
		
		// Lääg till huvudpanelen till fönstret
		add(framePanel);

		pack();
		
		// Ser till så att testinformation syns.
		customerList.setListData(bank.getAllCustomers().toArray(new String[0]));
	}


	/****************************************************
	 *  Metoder för att skapa paneler.
	 *****************************************************/

	private void createCustomerPanel() {

		ActionListener listener = new ClickCustomerListener();

	
		// Skapa kundlistpanelen med en rad och en kolumn.
		//JPanel customerListPanel = new JPanel(new GridLayout(1,1));
		JPanel customerListPanel = new JPanel();
		customerListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), ""));

		// Skapa och lägg till kundlistan.
		customerList = new JList();
		customerList.setBorder(BorderFactory.createTitledBorder("Kundlista"));	
		JScrollPane customerScrollPane = new JScrollPane(customerList);
		customerListPanel.add(customerScrollPane);
		
		// Skapa en anonym lyssnare som hanterar klickning på en kundperson i listan
		customerList.addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent evt) 
			{
				  getPersonInfo();
			}
		});
		

		// Lägg till panelen.
		customerPanel.add(customerListPanel);

		// Skapa kundinfopanelen med en rad och tre kolumner.
		JPanel customerInfoPanel = new JPanel(new GridLayout(1,3));
		customerInfoPanel.setBorder(BorderFactory.createTitledBorder("Kundinformation"));

		//Skapa och lägg till textfälten.
		nameField = new JTextField(TEXT_WIDTH);
		nameField.setBorder(BorderFactory.createTitledBorder("Förnamn"));
		customerInfoPanel.add(nameField);

		surnameField = new JTextField(TEXT_WIDTH);
		surnameField.setBorder(BorderFactory.createTitledBorder("Efternamn"));
		customerInfoPanel.add(surnameField);

		pNoField = new JTextField(TEXT_WIDTH);
		pNoField.setBorder(BorderFactory.createTitledBorder("Personnummer"));
		customerInfoPanel.add(pNoField);

		// Lägg till panelen.
		customerPanel.add(customerInfoPanel);
	}
	
	/**
	 * Hanterar klick som görs i kundpanelen.
	 */
	public class ClickCustomerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String buttonText = event.getActionCommand();
			if(buttonText.equals("Lägg till"))
			{
				//
			}
			if(buttonText.equals("Visa"))
			{
				//
			}
			if(buttonText.equals("Rensa"))
			{
				//
			}
		}
	}

	private void createAccountPanel() {

		ActionListener listener = new ClickAccountListener();

		// Skapa kontolistpanelen med en rad och en kolumn. 
		JPanel accountListPanel = new JPanel(new GridLayout(1,1));
		accountListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		
		// Skapa och lägg till kontolistan.
		accountList = new JList();
		accountList.setBorder(BorderFactory.createTitledBorder("Kontolista"));	
		accountListPanel.add(accountList);

		// Lägg till panelen.
		accountPanel.add(accountListPanel);
		accountList.setBorder(BorderFactory.createTitledBorder("Kontoinformation"));		
		
		// Skapa kontodetaljpanelen med en rad och två kolumnwe.
		JPanel accountInfoPanel = new JPanel(new GridLayout(1,2));
		accountInfoPanel.setBorder(BorderFactory.createTitledBorder("Kontoinformation"));
		
		//Skapa och lägg till textfälten.
		balanceField = new JTextField(TEXT_WIDTH);
		balanceField.setBorder(BorderFactory.createTitledBorder("Saldo"));
		accountInfoPanel.add(balanceField);

		amountField = new JTextField(TEXT_WIDTH);
		amountField.setBorder(BorderFactory.createTitledBorder("Belopp"));
		accountInfoPanel.add(amountField);

		// Lägg till panelen.
		accountPanel.add(accountInfoPanel);

		/*
		 * JPanel accountButtonPanel = new JPanel(); accountButtonPanel.setLayout(new
		 * BoxLayout(accountButtonPanel, BoxLayout.PAGE_AXIS));
		 * accountButtonPanel.add(createDepositButton());
		 * accountButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		 * accountButtonPanel.add(createWithDrawButton());
		 * 
		 * 
		 * JScrollPane accountScrollPane = new JScrollPane(accountList);
		 * accountListPanel.add(accountScrollPane); accountPanel.add(accountListPanel);
		 * accountListPanel.setBorder(accountBorder); accountEntryPanel.add(new
		 * JLabel(" Summa")); accountEntryPanel.add(amountField);
		 * 
		 * accountPanel.add(accountButtonPanel);
		 * 
		 * return accountPanel;
		 * 
		 */	}

	/**
	 * Hanterar klick som görs i kontoanelen.
	 */
	public class ClickAccountListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String buttonText = event.getActionCommand();
			if(buttonText.equals("Lägg till"))
			{
				//
			}
			if(buttonText.equals("Visa"))
			{
				//
			}
			if(buttonText.equals("Rensa"))
			{
				//
			}
		}
	}



	/****************************************************
	 * Metoder för att skapa knappar
	 ****************************************************/


	private JButton createAddUserButton() {
		class AddUserListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Lägg till kund!", "Lägg till kund");			}
		}

		JButton button = new JButton("Lägg till kund");
		ActionListener listener = new AddUserListener();
		button.addActionListener(listener);
		return button;
	}


	private JButton createDeleteUserButton() {
		class DeleteUserListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Radera kund!", "Radera kund");
			}
		}

		JButton button = new JButton("Radera kund");
		ActionListener listener = new DeleteUserListener();
		button.addActionListener(listener);
		return button;
	}

	private JButton createUpdateUserInfoButton() {
		class UpdateUserInfoListener implements ActionListener {

			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Uppdatera kundinfo!", "Uppdatera kundinfo");		
			}
		}

		JButton button = new JButton("Uppdatera kundinfo");
		ActionListener listener = new UpdateUserInfoListener();
		button.addActionListener(listener);
		return button;
	}

	private JButton createDepositButton() {
		class DepositListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Insättning!", "Insättning");				
			}
		}

		JButton button = new JButton("Insättning");
		ActionListener listener = new DepositListener();
		button.addActionListener(listener);
		return button;
	}

	private JButton createWithDrawButton() {
		class WithDrawListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Uttag!", "Uttag");				
			}
		}

		JButton button = new JButton("Uttag");
		ActionListener listener = new WithDrawListener();
		button.addActionListener(listener);
		return button;
	}



	/****************************************************
	 * 
	 * Metoder för att skapa menuer och submenyer.
	 * 
	 ****************************************************/	

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		menuBar.add(createCustomerMenu());
		menuBar.add(createAccountMenu());
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("Arkiv");
		menu.add(createOpenItem());
		menu.add(createSaveItem());
		menu.add(createExitItem());
		return menu;
	}

	private JMenuItem createOpenItem() {
		class OpenItemListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Öppna!", "Öppna");
			}
		}

		JMenuItem item = new JMenuItem("Öppna");
		ActionListener listener = new OpenItemListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenuItem createSaveItem() {
		class SaveItemListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Spara!", "Spara");				
			}
		}

		JMenuItem item = new JMenuItem("Spara");
		ActionListener listener = new SaveItemListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenuItem createExitItem() {
		class ExitItemListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);				
			}
		}

		JMenuItem item = new JMenuItem("Avsluta");
		ActionListener listener = new ExitItemListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenu createCustomerMenu() {
		JMenu menu = new JMenu("Kund");
		menu.add(createNewCustomerItem());
		menu.add(createDeleteCustomerItem());
		return menu;
	}

	private JMenuItem createNewCustomerItem() {
		class CreateCustomerListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Kund => Skapa!", "Skapa kund");				
			}
		}

		JMenuItem item = new JMenuItem("Skapa");
		ActionListener listener = new CreateCustomerListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenuItem createDeleteCustomerItem() {
		class DeleteCustomerListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Kund => Radera!", "Radera kund");				
			}
		}

		JMenuItem item = new JMenuItem("Radera");
		ActionListener listener = new DeleteCustomerListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenu createAccountMenu() {
		JMenu menu = new JMenu("Konto");
		menu.add(createNewAccountItem());
		menu.add(createDeleteAccountItem());
		menu.add(createShowTransactionsItem());
		return menu;
	}

	private JMenuItem createNewAccountItem() {
		class CreateAccountListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Konto => Skapa!", "Skapa konto");				
			}
		}

		JMenuItem item = new JMenuItem("Skapa");
		ActionListener listener = new CreateAccountListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenuItem createDeleteAccountItem() {
		class DeleteAccountListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Konto => Radera!", "Radera konto");				
			}
		}

		JMenuItem item = new JMenuItem("Radera");
		ActionListener listener = new DeleteAccountListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenuItem createShowTransactionsItem() {
		class ShowTransactionsListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				infoBox("Du har klickat på Konto => Visa transaktioner!", "Visa transaktioner");				
			}
		}

		JMenuItem item = new JMenuItem("Visa transaktioner");
		ActionListener listener = new ShowTransactionsListener();
		item.addActionListener(listener);
		return item;
	}



	/****************************************************
	 *  Metoder för informationsfönster.
	 *****************************************************/	

	public static void infoBox(String message, String title)
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
