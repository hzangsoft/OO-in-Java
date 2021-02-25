package hkastr5.gui;
import java.awt.BorderLayout;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import hkastr5.accounts.Account;
import hkastr5.customers.Customer;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen BankFrame hanterar det grafiska användargränssnittet för Minibanken.
 * 
 */
import hkastr5.logic.BankLogic;

public class BankFrame extends JFrame{
	/**
	 * 
	 */

	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 300;

	private static final int TEXT_WIDTH = 15;

	private BankLogic bank;

	// GUI-komponenter, etc som hör till kundpanelen. 
	private JPanel customerPanel;
	private DefaultListModel<String> customerModel = new DefaultListModel();
	private JList customerList =new JList(customerModel);
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField pNoField;


	// GUI-komponenter som hör till kontopanelen.
	JPanel accountPanel;

	// GUI-objekt saom måste kunna manipuleras i hela klassen
	private JButton depositButton = createDepositButton();
	private JButton withdrawButton = createWithDrawButton();
	private JButton transactionButton = showListTransactionsButton();
	private JMenuItem createAccountItem = createNewAccountItem();
	private JMenuItem deleteAccountItem = createDeleteAccountItem();
	private JMenuItem showTransactionItem = createShowTransactionsItem();

	private JButton updateCustomerInfoButton = createUpdateCustomerInfoButton();
	private JButton deleteCustomerButton = createDeleteUserButton();
	private JMenuItem deleteCustomerItem = createDeleteCustomerItem();


	private DefaultListModel<Integer> accountModel = new DefaultListModel<Integer>();
	private JList accountList = new JList(accountModel);
	private JTextField accountTypeField;
	private JTextField balanceField;

	public BankFrame(BankLogic bank) {
		this.bank = bank;
		initializeListData();
		createComponents();

		
		// Se till att fönstret hamnar mitt på skärmen.
		setLocationRelativeTo(null);
	}

	
	private void initializeListData() {
		initializeCustomerList();
		initializeAccountList(currentCustomerpNo());
	}


	/**
	 * 
	 * 
	 */
	private void initializeCustomerList() {
		customerModel.clear();
		ArrayList <String> pNoList = bank.getAllCustomerSSNs();
		if (!pNoList.isEmpty()) {
			for (String pNo : pNoList) {
				customerModel.addElement(pNo);
			}
			customerList.setSelectedIndex(0);
		}
	}
	
	/**
	 * 
	 * 
	 */
	private void initializeAccountList(String pNo) {
		accountModel.clear();
		ArrayList <String> accounts = bank.getCustomer(pNo).getAccountNoList();
		if (!accounts.isEmpty()) {
			for (String accountID : accounts) {
				accountModel.addElement(Integer.valueOf(accountID));				
			}
			accountList.setSelectedIndex(0);
		}
	}
	
	
	/**
	 * Skapa upp gränssnittet
	 * 
	 */
	private void createComponents() {

		// Skapa vänster (kund) och höger (konto) delpanel.
		customerPanel = new JPanel(new GridLayout(3, 1));
		customerPanel.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));

		accountPanel = new JPanel(new GridLayout(3, 1));
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

		// Lägg till huvudpanelen till fönstret
		add(framePanel);

		pack();

		updateGUI();
	}

	/**
	 * Uppdaterar gränsnittet med avseende på förändringar i
	 *   - innehåll i kundlistan
	 *   - innehåll i kontolistan
	 *   - knappstatus
	 *   - menyalternativsstatus
	 * 
	 */
	private void updateGUI() {
		// Aktivera/deaktivera knappar och menyalternativ
		setCustomerButtonMenuItemStatus();
		setAccountButtonMenuItemStatus();
		}

	/**
	 * Hämta personnumret för det kund som är vald i kundlistan.
	 * 
	 * @return Personnumret om en kund är vald
	 * @return En tom sträng 
	 */
	private String currentCustomerpNo() {
		int index = customerList.getSelectedIndex();
		if (index >= 0) {
			return (String) customerList.getSelectedValue();
		} else {
			return "";
		}
	}

	/**
	 * Hämta kontonumret för det konto som är valt i kontolistan.
	 * 
	 * @return Kontonumret
	 */
	private int currentAccountNo() {
		int index = accountList.getSelectedIndex();
		if (index >= 0) {
			return accountModel.get(index);
		} else {
			return -1;
		}
	}


	/****************************************************
	 *  Metoder för att skapa paneler.
	 *****************************************************/

	private void createCustomerPanel() {

		// Skapa kundlistpanelen .
		JPanel customerListPanel = new JPanel(new GridLayout(1,3));
		customerListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), ""));

		// Skapa och lägg till kundlistan.
		customerList.setBorder(BorderFactory.createTitledBorder("Kundlista"));
		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane customerScrollPane = new JScrollPane(customerList);
		customerListPanel.add(customerScrollPane);

		// Skapa en anonym lyssnare som hanterar klickning på en kundperson i listan
		customerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
			    if (!evt.getValueIsAdjusting()) {
			    	String pNo = currentCustomerpNo();
			    	if (pNo.equals("")) {
				    	displayCustomerInfo("", "", "");
				    	accountModel.clear();
				    	displayAccountInfo("", "");
			    	} else {
				    	Customer c = bank.getCustomer(currentCustomerpNo());
				    	displayCustomerInfo(c.getSocialSecurityNumber(), c.getName(), c.getSurname());
				    	initializeAccountList(currentCustomerpNo());
			    	}
			    }				
			}
		});

		// Lägg till panelen.
		customerPanel.add(customerListPanel);



		// Skapa kundinfopanelen med en rad och tre kolumner.
		JPanel customerInfoPanel = new JPanel(new GridLayout(1,3));

		//Skapa och lägg till textfälten.
		pNoField = new JTextField(TEXT_WIDTH);
		pNoField.setBorder(BorderFactory.createTitledBorder("Personnummer"));
		customerInfoPanel.add(pNoField);

		nameField = new JTextField(TEXT_WIDTH);
		nameField.setBorder(BorderFactory.createTitledBorder("Förnamn"));
		customerInfoPanel.add(nameField);

		surnameField = new JTextField(TEXT_WIDTH);
		surnameField.setBorder(BorderFactory.createTitledBorder("Efternamn"));
		customerInfoPanel.add(surnameField);

		// Lägg till panelen.
		customerPanel.add(customerInfoPanel);



		// Skapa en panel med tre knappar.
		JPanel customerButtonPanel = new JPanel(new GridLayout(1,3));
		customerButtonPanel.add(createAddUserButton());
		customerButtonPanel.add(updateCustomerInfoButton);
		customerButtonPanel.add(deleteCustomerButton);


		// Lägg till panelen.
		customerPanel.add(customerButtonPanel);
	}


	private void createAccountPanel() {

		// Skapa kontolistpanelen. 
		JPanel accountListPanel = new JPanel(new GridLayout(1,3));
		accountListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

		// Skapa och lägg till kontolistan.
		accountList.setBorder(BorderFactory.createTitledBorder("Kontolista"));	
		accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane accountScrollPane = new JScrollPane(accountList);
		accountListPanel.add(accountScrollPane);

		// Lägg till panelen.
		accountPanel.add(accountListPanel);

		// Skapa en anonym lyssnare som hanterar klickning på en kundperson i listan
		accountList.addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent evt) 
			{
				if (!evt.getValueIsAdjusting()) {
					String pNo = currentCustomerpNo();
					int aNo = currentAccountNo();
					if (pNo.equals("") || aNo < 0) {
						displayAccountInfo("", "");
					} else {
						Account a = bank.getCustomer(pNo).getAccount(aNo);
						String balance = String.format("%10.2f", a.getBalance());
						String accountType = a.getAccountType();
						displayAccountInfo(balance, accountType);
					}
				}
			}
		});


		// Skapa kontodetaljpanelen.
		JPanel accountInfoPanel = new JPanel(new GridLayout(1,2));

		//Skapa och lägg till textfälten.
		accountTypeField = new JTextField(TEXT_WIDTH);
		accountTypeField.setBorder(BorderFactory.createTitledBorder("Kontotyp"));
		accountInfoPanel.add(accountTypeField);
		balanceField = new JTextField(TEXT_WIDTH);
		balanceField.setBorder(BorderFactory.createTitledBorder("Saldo"));
		accountInfoPanel.add(balanceField);

		// Lägg till panelen.
		accountPanel.add(accountInfoPanel);

		// Skapa en panel med tre knappar.
		JPanel accountButtonPanel = new JPanel(new GridLayout(1,3));
		accountButtonPanel.add(depositButton);
		accountButtonPanel.add(withdrawButton);
		accountButtonPanel.add(transactionButton);

		// Lägg till panelen.
		accountPanel.add(accountButtonPanel);

	}



	/****************************************************
	 * Metoder för att skapa knappar
	 ****************************************************/


	private JButton createAddUserButton() {
		JButton button = new JButton("Lägg till kund");
		ActionListener listener = new CreateCustomerListener();
		button.addActionListener(listener);
		return button;
	}


	private JButton createDeleteUserButton() {
		JButton button = new JButton("Ta bort kund");
		ActionListener listener = new DeleteCustomerListener();
		button.addActionListener(listener);
		return button;
	}

	private JButton createUpdateCustomerInfoButton() {
		class UpdateUserInfoListener implements ActionListener {

			public void actionPerformed(ActionEvent event) {

				// Till rapport
				String newName = nameField.getText();
				String newSurname = surnameField.getText();
				String newpNo = pNoField.getText();

				String validationResult = bank.validateCustomerInfo(newName, newSurname, newpNo);


				if (validationResult.equals("")) {
					bank.changeCustomerName(currentCustomerpNo(), newName, newSurname, newpNo);
					infoBox("Kundinformationen har uppdaterats!", "Uppdatera kundinfo");
					// Uppdatera kundlistan ifall personnumret har ändrats.
					updateGUI();
				} else {
					errorBox("Var vänlig kontrollera inmatningsfälten!" + System.lineSeparator() + validationResult, "Uppdatera kundinfo");
				}
			}
		}

		JButton button = new JButton("Uppdatera kundinfo");
		ActionListener listener = new UpdateUserInfoListener();
		button.addActionListener(listener);
		return button;
	}

	private JButton createDepositButton() {
		JButton button = new JButton("Insättning");
		ActionListener listener = new DepositButtonListener();
		button.addActionListener(listener);
		return button;
	}

	private JButton createWithDrawButton() {
		JButton button = new JButton("Uttag");
		ActionListener listener = new WithdrawButtonListener();
		button.addActionListener(listener);
		return button;
	}

	private JButton showListTransactionsButton() {
		JButton button = new JButton("Visa transaktioner");
		ActionListener listener = new ShowTransactionListener();
		button.addActionListener(listener);
		return button;
	}

	private void setAccountButtonMenuItemStatus() {
		boolean accountSelected = accountList.getSelectedIndex() >= 0;
		depositButton.setEnabled(accountSelected);
		withdrawButton.setEnabled(accountSelected);
		transactionButton.setEnabled(accountSelected);
		createAccountItem.setEnabled(accountSelected);
		deleteAccountItem.setEnabled(accountSelected);
		showTransactionItem.setEnabled(accountSelected);
	}

	private void setCustomerButtonMenuItemStatus() {
		boolean customerSelected = customerList.getSelectedIndex() >= 0;
		updateCustomerInfoButton.setEnabled(customerSelected);
		deleteCustomerButton.setEnabled(customerSelected);
		deleteCustomerItem.setEnabled(customerSelected);

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
		menu.add(deleteCustomerItem);
		return menu;
	}

	private JMenuItem createNewCustomerItem() {
		JMenuItem item = new JMenuItem("Skapa");
		ActionListener listener = new CreateCustomerListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenuItem createDeleteCustomerItem() {
		JMenuItem item = new JMenuItem("Radera");
		ActionListener listener = new DeleteCustomerListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenu createAccountMenu() {
		JMenu menu = new JMenu("Konto");
		menu.add(createAccountItem);
		menu.add(deleteAccountItem);
		menu.add(showTransactionItem);
		return menu;
	}

	private JMenuItem createNewAccountItem() {
		JMenuItem item = new JMenuItem("Skapa");
		ActionListener listener = new CreateAccountListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenuItem createDeleteAccountItem() {
		JMenuItem item = new JMenuItem("Stäng konto");
		class DeleteAccountListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String result = "Kontoavslut" + System.lineSeparator() +
						"Personnummer: " + currentCustomerpNo() + System.lineSeparator(); 
				for (String s : bank.closeAccount(currentCustomerpNo(), currentAccountNo())) {
					result += s + System.lineSeparator();
				}
				infoBox(result, "Stäng konto");
				updateGUI();
			}
		}
		ActionListener listener = new DeleteAccountListener();
		item.addActionListener(listener);
		return item;
	}

	private JMenuItem createShowTransactionsItem() {
		JMenuItem item = new JMenuItem("Visa transaktioner");
		ActionListener listener = new ShowTransactionListener();
		item.addActionListener(listener);
		return item;
	}



	/****************************************************
	 *  Metoder för informationsfönster.
	 *****************************************************/	

	private void infoBox(String message, String title)
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}


	private static void errorBox(String errorMessage, String title)
	{
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}

	private boolean confirmBox(String warningMessage, String title)
	{
		return JOptionPane.showConfirmDialog(null, warningMessage, title, JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
	}

	/**
	 * Fyll de kundspecifika textfälten med information.
	 * 
	 * @return En lista över bankens kunder.
	 */

	
	private void displayCustomerInfo(String pNo, String name, String surname) {
		pNoField.setText(pNo);
		nameField.setText(name);
		surnameField.setText(surname);
	}
	
	private void displayAccountInfo(String balance, String accountType) {
		balanceField.setText(balance);
		accountTypeField.setText(accountType);
	}
	
	private class DeleteCustomerListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int index = customerList.getSelectedIndex();
			if ( index>= 0) {			
				Customer customer = bank.getCustomer(currentCustomerpNo());
				boolean deleteConfirmed = confirmBox("Är du säker på att du vill ta bort kunden"  +  System.lineSeparator() +
						"Personnummer: "+ customer.getSocialSecurityNumber() + System.lineSeparator() +
						"Namn: "+ customer.getFullName()+ "?", "Borttag av kund");
				if (deleteConfirmed) {

					ArrayList<String> result = new ArrayList<String>();
					result = bank.deleteCustomer(currentCustomerpNo());
					String closingStatement = "";
					for (String s : result) {
						closingStatement += s + System.lineSeparator();
					}
					// Visa upp slutinfo om kund i en MessageBox
					infoBox(closingStatement, "Kundavslut");
					customerModel.remove(index);
					updateGUI();
				}
			} else {
				infoBox("Ingen kund är markerad.", "Kundavslut");
			}
		}
	}

	private class CreateCustomerListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			// Skapa ett nytt dialogfönster.
			// Skapa och lägg till textfälten.
			JTextField pNoField = new JTextField(TEXT_WIDTH);
			JTextField nameField = new JTextField(TEXT_WIDTH);
			JTextField surnameField = new JTextField(TEXT_WIDTH);

			final JComponent[] inputs = new JComponent[] {
					new JLabel("Personnummer"),
					pNoField,
					new JLabel("Förnamn"),
					nameField,
					new JLabel("Efternamn"),
					surnameField
			};

			int result;
			Boolean done = false;
			while (!done) {
				result = JOptionPane.showConfirmDialog(null, inputs, "Skapa kund", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					// Till rapport
					String newName = nameField.getText();
					String newSurname = surnameField.getText();
					String newpNo = pNoField.getText();

					String validationResult = bank.validateCustomerInfo(newName, newSurname, newpNo);
					if (validationResult.equals("")) {
						if (bank.createCustomer(newName, newSurname, newpNo)) {
							bank.createCustomer(newName, newSurname, newpNo);
							infoBox("Kunden har skapats!", "Skapa kund");
							// Uppdatera kundlistan.
							//Peka på den nya kunden
							customerModel.add(0, newpNo);
							updateGUI();
							done = true;
						} else {
							errorBox("Det finns redan en kund med detta personnummer!", "Skapa kund");
						}
					} else {
						errorBox("Var vänlig kontrollera inmatningsfälten!" + System.lineSeparator() + validationResult,
								"Skapa kund");
					}
				} else {
					done = true;
				} 
			}
		}
	}

	private class CreateAccountListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			// Kan inte få det att fungera med radio buttons. Det faller på att ButtonGroup inte är en JComponent.

			String[] accountTypes = {"Sparkonto", "Kreditkonto"};

			Object result = JOptionPane.showInputDialog(null, "Ange vilken typ av konto du vill skapa!", "Skapa konto", JOptionPane.OK_CANCEL_OPTION, null, accountTypes, "Sparkonto");
			if (result != null) {
				String selection = result.toString();
				if (selection.equals(accountTypes[0])) {
					bank.createSavingsAccount(currentCustomerpNo());
				} else if (selection.equals(accountTypes[1])) {
					bank.createCreditAccount(currentCustomerpNo());
				} 
				updateGUI();
			}
		}	
	}

	private class ShowTransactionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (accountList.getSelectedIndex() >=0 ) {
				int accountNo = currentAccountNo();
				ArrayList <String> transactions = bank.getTransactions(currentCustomerpNo(), accountNo);
				String message = "";
				for (String s : transactions) {
					message += s + System.lineSeparator();
				}					
				infoBox(message, "Visa transaktioner");
			} else {
				infoBox("Inget konto är valt", "Visa transaktioner");
			}
		}
	}

	class DepositButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// Skapa ett nytt dialogfönster.
			// Skapa och lägg till textfälten.
			JTextField amountField = new JTextField(TEXT_WIDTH);
			final JComponent[] inputs = new JComponent[] {
					new JLabel("Vilket belopp vill du sätta in på från kontot?"),
					amountField,
			};
			Boolean done = false;
			while (!done) {
				int result = JOptionPane.showConfirmDialog(null, inputs, "Insättning", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					double amount;
					try {
						amount = Double.parseDouble(amountField.getText());
						if (bank.deposit(currentCustomerpNo(), currentAccountNo(), amount)) {
							updateGUI();
							done = true;
						} else {
							errorBox("Ett oförutsett fel har inträffat. Var god kontakta banken för att få hjälp", "Insättning");
						}
					} catch (NumberFormatException e) {
						errorBox("Var vänlig kontrollera beloppet!" + System.lineSeparator() +
								"Din inmatning '" + amountField.getText() + "' verkar inte vara ett tal!",
								"Insättning");
					}
				} else {
					done = true;
				} 
			}
		}
	}

	class WithdrawButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// Skapa ett nytt dialogfönster.
			// Skapa och lägg till textfälten.
			JTextField amountField = new JTextField(TEXT_WIDTH);
			final JComponent[] inputs = new JComponent[] {
					new JLabel("Vilket belopp vill du ta ut från kontot?"),
					amountField,
			};
			Boolean done = false;
			while (!done) {
				int result = JOptionPane.showConfirmDialog(null, inputs, "Uttag", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					double amount;
					try {
						amount = Double.parseDouble(amountField.getText());
						if (bank.withdraw(currentCustomerpNo(), currentAccountNo(), amount)) {
							updateGUI();
							done = true;
						} else {
							errorBox("Det finns inte täckning på konto för detta uttag", "Insättning");
						}
					} catch (NumberFormatException e) {
						errorBox("Var vänlig kontrollera beloppet!" + System.lineSeparator() +
								"Din inmatning '" + amountField.getText() + "' verkar inte vara ett tal!",
								"Insättning");
					}
				} else {
					done = true;
				} 
			}

		}
	}
}


