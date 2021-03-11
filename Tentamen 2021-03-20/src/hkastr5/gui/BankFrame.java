package hkastr5.gui;
import java.awt.BorderLayout;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen BankFrame.
 * 
 */
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileNameExtensionFilter;

import hkastr5.accounts.Account;
import hkastr5.accounts.AccountTypes;
import hkastr5.customers.Customer;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 4
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen BankFrame hanterar det grafiska användargränssnittet för Minibanken.
 * 
 */
import hkastr5.logic.BankLogic;

public class BankFrame extends JFrame{

	private static final long serialVersionUID = 1681249499040375992L;

	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 300;

	private static final int TEXT_WIDTH = 15;

	private static final String SAVEFILENAME = "hkastr5_files/bank.dat";
	private static final String ACCOUNTSTATEMENTFIRSTLINE ="Detta är ett kontoutdrag från Minibanken";

	private BankLogic bank;

	// GUI-komponenter, etc som hör till kundpanelen. 
	private JPanel customerPanel;
	private DefaultListModel<String> customerModel = new DefaultListModel<String>();
	private JList<String> customerList = new JList<String>(customerModel);
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField pNoField;


	// GUI-komponenter som hör till kontopanelen.
	JPanel accountPanel;

	// GUI-objekt saom måste kunna manipuleras i hela klassen
	private JButton depositButton = createDepositButton();
	private JButton withdrawButton = createWithDrawButton();
	private JButton transactionButton = createShowTransactionsButton();
	private JMenuItem createAccountItem = createNewAccountItem();
	private JMenuItem deleteAccountItem = createDeleteAccountItem();
	private JMenuItem showTransactionItem = createShowTransactionsItem();
	private JMenuItem saveAccountStatementItem = createSaveAccountStatementItem();
	private JMenuItem displayAccountStatementItem = createDisplayAccountStatementItem();

	private JButton updateCustomerInfoButton = createUpdateCustomerInfoButton();
	private JButton deleteCustomerButton = createDeleteUserButton();
	private JMenuItem deleteCustomerItem = createDeleteCustomerItem();


	private DefaultListModel<Integer> accountModel = new DefaultListModel<Integer>();
	private JList<Integer> accountList = new JList<Integer>(accountModel);
	private JTextField accountTypeField;
	private JTextField balanceField;

	// Filväljarobjekt för transaktionshantering
	private JFileChooser transactionFileChooser = createTextFileChooser();


	/**
	 * Konstruktor
	 * 
	 * @param bank
	 *            Bankobjekt som ska visas upp i GUIt.
	 */
	public BankFrame(BankLogic bank) {
		this.bank = bank;

		// Fyll kund-och kontolistorna med data 
		initializeListData();

		// Skapa upp alla komponenter.
		createComponents();

		// Visa upp informationen för den valda (= första) kunden i listan
		displayCustomerInfo(currentCustomerpNo());

		updateGUI();

		// Se till att fönstret hamnar mitt på skärmen.
		setLocationRelativeTo(null);
	}


	private void initializeListData() {
		initializeCustomerList();
		if (currentAccountNo() >= 0) {
			initializeAccountList(bank.getCustomer(currentCustomerpNo()).getAccountNoList());
		}
	}


	/**
	 * Initiera kundlistan.
	 * 
	 */
	private void initializeCustomerList() {
		customerModel.clear();
		ArrayList <String> pNoList = bank.getAllCustomerSSNs();
		if (pNoList.isEmpty()) {
			customerList.setSelectedIndex(-1); 
		} else {
			for (String pNo : pNoList) {
				customerModel.addElement(pNo);
			}
			customerList.setSelectedIndex(0);
		}
	}

	/**
	 * Initiera kontolistan
	 * 
	 */
	private void initializeAccountList(ArrayList <String> accounts) {
		accountModel.clear();
		if (accounts.isEmpty()) {
			accountList.setSelectedIndex(-1);
		} else {
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
	}

	/**
	 * Uppdaterar gränsnittet med avseende på förändringar i
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

	/**
	 * Skapa upp kundpanelen
	 * 
	 */

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
					displayCustomerInfo(currentCustomerpNo());
					updateGUI();
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
		// Personummerfältet ska inte gå att editera.
		pNoField.setEditable(false);
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

	/**
	 * Skapa upp kontopanelen
	 * 
	 */

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
					displayAccountInfo(currentCustomerpNo(), currentAccountNo());
				}
			}
		});


		// Skapa kontodetaljpanelen.
		JPanel accountInfoPanel = new JPanel(new GridLayout(1,2));

		//Skapa och lägg till textfälten.
		accountTypeField = new JTextField(TEXT_WIDTH);
		accountTypeField.setBorder(BorderFactory.createTitledBorder("Kontotyp"));
		// Fältet för kontotyp ska inte gå att editera.
		accountTypeField.setEditable(false);
		accountInfoPanel.add(accountTypeField);
		balanceField = new JTextField(TEXT_WIDTH);
		balanceField.setBorder(BorderFactory.createTitledBorder("Saldo"));
		// Fältet för saldot ska inte gå att editera.
		balanceField.setEditable(false);
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

	/**
	 * Skapa knappen för att lägga till en användare.
	 * Lägg till en actionlistener.
	 */
	private JButton createAddUserButton() {
		JButton button = new JButton("Lägg till kund");
		ActionListener listener = new CreateCustomerListener();
		button.addActionListener(listener);
		return button;
	}


	/**
	 * Skapa knappen för att radera en användare.
	 * Lägg till en actionlistener.
	 */
	private JButton createDeleteUserButton() {
		JButton button = new JButton("Ta bort kund");
		ActionListener listener = new DeleteCustomerListener();
		button.addActionListener(listener);
		return button;
	}

	/**
	 * Skapa knappen för att uppdatera informationen om en användare.
	 * Lägg till en actionlistener.
	 */

	private JButton createUpdateCustomerInfoButton() {
		class UpdateUserInfoListener implements ActionListener {

			public void actionPerformed(ActionEvent event) {

				String newName = nameField.getText();
				String newSurname = surnameField.getText();
				String pNo = currentCustomerpNo();

				String validationResult = bank.validateCustomerInfo(newName, newSurname, pNo);

				// Kontrollera om den inmatade information är giltig
				if (validationResult.equals("")) {
					bank.changeCustomerName(pNo, newName, newSurname);
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


	/**
	 * Skapa knappen för att göra en insättning på ett konto.
	 * Lägg till en actionlistener.
	 */
	private JButton createDepositButton() {
		JButton button = new JButton("Insättning");
		ActionListener listener = new DepositButtonListener();
		button.addActionListener(listener);
		return button;
	}

	/**
	 * Skapa knappen för att göra ett uttag från ett konto.
	 * Lägg till en actionlistener.
	 */
	private JButton createWithDrawButton() {
		JButton button = new JButton("Uttag");
		ActionListener listener = new WithdrawButtonListener();
		button.addActionListener(listener);
		return button;
	}

	/**
	 * Skapa knappen för att spara ett kontos alla transaktioner.
	 * Lägg till en actionlistener.
	 */
	private JButton createShowTransactionsButton() {

		JButton button = new JButton("Visa transaktioner");
		ActionListener listener = new ShowTransactionListener();
		button.addActionListener(listener);
		return button;
	}

	/**
	 * Aktivera/deaktivera knappar och menualternativ som är beroende
	 * av om ett konto är vald i kontolistan eller inte.
	 */
	private void setAccountButtonMenuItemStatus() {
		boolean customerSelected = customerList.getSelectedIndex() >= 0;
		createAccountItem.setEnabled(customerSelected);

		boolean accountSelected = accountList.getSelectedIndex() >= 0;
		depositButton.setEnabled(accountSelected);
		withdrawButton.setEnabled(accountSelected);
		transactionButton.setEnabled(accountSelected);
		deleteAccountItem.setEnabled(accountSelected);
		showTransactionItem.setEnabled(accountSelected);
		saveAccountStatementItem.setEnabled(accountSelected);
	}

	/**
	 * Aktivera/deaktivera knappar och menualternativ som är beroende
	 * av om en kund är vald i kundlistan eller inte.
	 */
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

	/**
	 * Skapa menyraden med tillhörande menyer.
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		menuBar.add(createCustomerMenu());
		menuBar.add(createAccountMenu());
		menuBar.add(createTransactionMenu());
	}

	/**
	 * Skapa 'Arkiv'-menyn med tillhörande menyalternativ.
	 */
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("Arkiv");
		menu.add(createOpenItem());
		menu.add(createSaveItem());
		menu.add(createExitItem());
		return menu;
	}

	/**
	 * Skapa 'Öppna'-menyalternativet.
	 * Lägg till en temoprär actionlistener.
	 * Den riktiga implementationen läggs till i inlämningsuppgift 4.
	 */
	private JMenuItem createOpenItem() {
		class OpenItemListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				try {
					ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(SAVEFILENAME));
					bank = (BankLogic)inStream.readObject();
					inStream.close();
					initializeListData();
					infoBox("Din sparade bank har öppnats!", "Öppna");
				} catch (FileNotFoundException e) {
					errorBox("Vi hittade inte den fil du har sparat din bank i.", "Öppna bankdata");
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					errorBox("Den sparade filen har troligen blivit korrupt.", "Öppna bankdata");
					e.printStackTrace();
				} catch (IOException e) {
					errorBox("Något gick fel när vi försökte öppna den sparade filen", "Öppna bankdata");
					e.printStackTrace();
				}
			}
		}

		JMenuItem item = new JMenuItem("Öppna");
		ActionListener listener = new OpenItemListener();
		item.addActionListener(listener);
		return item;
	}

	/**
	 * Skapa 'Spara'-menyalternativet.
	 * Lägg till en temporär actionlistener.
	 * Den riktiga implementationen läggs till i inlämningsuppgift 4.
	 */
	private JMenuItem createSaveItem() {
		class SaveItemListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				try {
					// Ta reda på om filen redan existerar 
					File outFile = new File(SAVEFILENAME);
					boolean fileExists = outFile.exists();
					boolean performSave = !fileExists;
					// Fråga användaren om den existerande filen ska skrivas över.
					if (fileExists) {
						performSave = confirmBox("Din bankdatafil finns redan. Vill du skriva över den?", "Spara bankdata");
					}
					if (performSave) {
						ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(SAVEFILENAME));
						outStream.writeObject(bank);
						outStream.close();
						infoBox("Din bank har sparats!", "Spara");
					}
				} catch (FileNotFoundException e) {
					errorBox("Ett oväntat fel har uppstått. Kontakta din bank!", "Spara bankdata");
					e.printStackTrace();
				} catch (IOException e) {
					errorBox("Ett oväntat fel har uppstått. Kontakta din bank!", "Spara bankdata");
					e.printStackTrace();
				}

			}
		}

		JMenuItem item = new JMenuItem("Spara");
		ActionListener listener = new SaveItemListener();
		item.addActionListener(listener);
		return item;
	}

	/**
	 * Skapa 'Avsluta'-menyalternativet.
	 * Lägg till en actionlistener som stänger applikationen.
	 */
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

	/**
	 * Skapa 'Kund'-menyn med tillhörande menyalternativ.
	 */
	private JMenu createCustomerMenu() {
		JMenu menu = new JMenu("Kund");
		menu.add(createNewCustomerItem());
		menu.add(deleteCustomerItem);
		return menu;
	}

	/**
	 * Skapa 'Skapa'-menyalternativet.
	 * Lägg till en actionlistener.
	 */
	private JMenuItem createNewCustomerItem() {
		JMenuItem item = new JMenuItem("Skapa");
		ActionListener listener = new CreateCustomerListener();
		item.addActionListener(listener);
		return item;
	}

	/**
	 * Skapa 'Radera'-menyalternativet.
	 * Lägg till en actionlistener.
	 */
	private JMenuItem createDeleteCustomerItem() {
		JMenuItem item = new JMenuItem("Ta bort");
		ActionListener listener = new DeleteCustomerListener();
		item.addActionListener(listener);
		return item;
	}

	/**
	 * Skapa 'Konto'-menyn med tillhörande menyalternativ.
	 */
	private JMenu createAccountMenu() {
		JMenu menu = new JMenu("Konto");
		menu.add(createAccountItem);
		menu.add(deleteAccountItem);
		menu.add(showTransactionItem);
		return menu;
	}

	/**
	 * Skapa 'Skapa'-menyalternativet.
	 * Lägg till en actionlistener.
	 */
	private JMenuItem createNewAccountItem() {
		JMenuItem item = new JMenuItem("Skapa");
		ActionListener listener = new CreateAccountListener();
		item.addActionListener(listener);
		return item;
	}

	/**
	 * Skapa 'Radera'-menyalternativet.
	 * Lägg till en actionlistener.
	 */
	private JMenuItem createDeleteAccountItem() {
		JMenuItem item = new JMenuItem("Stäng konto");
		class DeleteAccountListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				int index = accountList.getSelectedIndex();
				int aNo = currentAccountNo();
				String result = "Kontoavslut" + System.lineSeparator() +
						"Personnummer: " + currentCustomerpNo() + System.lineSeparator(); 
				for (String s : bank.closeAccount(currentCustomerpNo(), aNo)) {
					result += s + System.lineSeparator();
				}
				infoBox(result, "Stäng konto");
				accountModel.removeElement(aNo);
				// Avgör vilken kund som ska vara vald.
				int newIndex;
				if (accountModel.isEmpty()) {
					newIndex = -1;
				} else if (index == 0) {
					newIndex = 0;
				} else {
					newIndex = index - 1;
				}
				accountList.setSelectedIndex(newIndex);
				updateGUI();
			}
		}
		ActionListener listener = new DeleteAccountListener();
		item.addActionListener(listener);
		return item;
	}

	/**
	 * Skapa 'Visa transaktioner'-menyalternativet.
	 * Lägg till en actionlistener.
	 */
	private JMenuItem createShowTransactionsItem() {
		JMenuItem item = new JMenuItem("Visa transaktioner");
		ActionListener listener = new ShowTransactionListener();
		item.addActionListener(listener);
		return item;
	}




	/**
	 * Skapa 'Kontoutdrag'-menyn med tillhörande menyalternativ.
	 */
	private JMenu createTransactionMenu() {
		JMenu menu = new JMenu("Kontoutdrag");
		menu.add(saveAccountStatementItem);
		menu.add(displayAccountStatementItem);
		return menu;
	}

	/**
	 * Skapa 'Skapa kontoutdrag'-menyalternativet.
	 * Lägg till en actionlistener.
	 */
	private JMenuItem createSaveAccountStatementItem() {
		class ListTransactionListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				// Skapa en filjväljardialog för att välja var kontoutdragsfil ska sparas.
				int saveConfirmed = transactionFileChooser.showSaveDialog(null);
				// Kontrollera användarens val
				if (saveConfirmed == JFileChooser.APPROVE_OPTION) {
					File outFile = transactionFileChooser.getSelectedFile();
					try {
						PrintWriter out = new PrintWriter(outFile);
						// Skriv ut de inledande raderna med text.
						out.println(ACCOUNTSTATEMENTFIRSTLINE);
						out.println();
						out.println("Datum: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
						out.println("Kundens namn: " + bank.getCustomer(currentCustomerpNo()).getFullName());
						out.println("Kontonummer: " + currentAccountNo());
						Account a = bank.getCustomer(currentCustomerpNo()).getAccount(currentAccountNo());
						out.println("Saldot på kontot: " + a.getBalance());
						out.println();
						ArrayList <String> transactions = a.getTransactions();
						// Kontrollera om det finns några transaktioner och skriv i så fall ut dem.
						if (transactions.isEmpty()) {
							out.println("Det finns inga transaktioner på kontot.");
						} else {
							out.println("Lista över kontotransaktionerna");
							out.println("-------------------------------");
							for (String t : transactions) {
								out.println(t);
							}
						}
						out.close();
						infoBox("Kontoutdrag sparat till filen " + outFile.getName(), "Skapa kontoutdrag");
					} catch (FileNotFoundException e) {
						errorBox("Något gick fel när kontoutdragsfilen skulle skapas. Var vnälig kontakta din bank", "Skapa kontoutdrag");
					}      	
				}
			}		
		}
		JMenuItem item = new JMenuItem("Skapa kontoutdrag");
		ActionListener listener = new ListTransactionListener();
		item.addActionListener(listener);
		return item;
	}


	/**
	 * Skapa 'Visa sparade transaktioner'-menyalternativet.
	 * Lägg till en actionlistener.
	 */
	private JMenuItem createDisplayAccountStatementItem() {
		class DisplayAccountStatementListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				// Skapa en filjväljardialog för att välja vilken kontoutdragsfil som ska öppnas.
				int openConfirmed = transactionFileChooser.showOpenDialog(null);
				// Kontrollera att användarens verkligen har gjort ett val
				if (openConfirmed == JFileChooser.APPROVE_OPTION) {
					File inFile = transactionFileChooser.getSelectedFile();
					// Kontrollera så att det är en fil som är vald (inte en folder).
					if (inFile.isFile()) {
						try {
							String result = "";
							Scanner in = new Scanner(inFile);
							boolean firstLine = true;
							// Läs filen rad för rad.
							while (in.hasNextLine()) {
								String line = in.nextLine() + System.lineSeparator();
								// Användare kan öppna en godtycklig textfil. Kontrollera därför  
								// att den första raden i filen innehåller rätt textsträng.
								if (firstLine) {
									if (line.trim().equals(ACCOUNTSTATEMENTFIRSTLINE)) {
										firstLine = false;
									} else {
										errorBox("Du har öppnat en textfil som inte innehåller ett kontoutdrag. Välj en annan fil.","Visa kontoutdrag");
										in.close();
										return;
									}
								}
								// Lägg till den inlästa raden i resultatet.
								result += line;
							}
							in.close();
							// Visa upp resultatet för användaren
							infoBox(result, "Hämta kontoutdrag");
						} catch (FileNotFoundException e) {
							errorBox("Någonting har gått snett. Prova att välja en annan fil.","Hämta kontoutdrag");						}
					} else {
						// Ge felmeddelande.
						errorBox("Du har valt att öppna information från ett objekt som inte en fil!", "Hämta kontoutdrag");
					}
				}
			}
		}
		JMenuItem item = new JMenuItem("Hämta kontoutdrag");
		ActionListener listener = new DisplayAccountStatementListener();
		item.addActionListener(listener);
		return item;
	}

	/**
	 * Skapa en filväljare för textfiler.
	 * @return	Filväljaren
	 */
	private JFileChooser createTextFileChooser() {
		// Skapa ett dialogfönster för att välja fil 
		JFileChooser fc = new JFileChooser();
		// Filtrera fram alla textfiler.
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textfiler", "txt");
		fc.setFileFilter(filter);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		return fc;	
	}

	/****************************************************
	 *  Metoder för dialogfönster.
	 *****************************************************/	

	/**
	 * Skapa ett informationsfönster och visa upp ett meddelande för användaren.
	 * @param
	 * 		message Meddelandet som ska visas upp
	 * @param
	 * 		title Titeln på informationsfönstret.
	 */
	private void infoBox(String message, String title)
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}


	/**
	 * Skapa ett fönster och visa upp ett felmeddelande för användaren.
	 * @param
	 * 		errorMessage Felmeddelandet som ska visas upp
	 * @param
	 * 		title Titeln på fönstret.
	 */
	private static void errorBox(String errorMessage, String title)
	{
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Skapa ett fönster där användaren ska bekräfta en handling.
	 * @param
	 * 		warningMessage Meddelandet om det användaren ska bekräfta.
	 * @param
	 * 		title Titeln på informationsfönstret.
	 */
	private boolean confirmBox(String warningMessage, String title)
	{
		return JOptionPane.showConfirmDialog(null, warningMessage, title, JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
	}


	/****************************************************
	 *  Metoder för informationsfönster.
	 *****************************************************/	


	/**
	 * Fyll de kundspecifika textfälten med information.
	 * 
	 * @param
	 * 		pNo Personnumret för den kund vars information ska visas upp.
	 */
	private void displayCustomerInfo(String pNo) {
		String name;
		String surname;
		if (pNo.equals("")) {
			// Ingen kund vald. Radera informationen i alla fält
			name = "";
			surname = "";
			// Töm kontolistan
			accountModel.clear();
			displayAccountInfo("", -1);
		} else {
			// Hämta informationen om aktuell kund och fyll informationen i alla fält
			Customer c = bank.getCustomer(currentCustomerpNo());
			name =c.getName();
			surname = c.getSurname();
			// Fyll kundlistan med den aktuella kundens kontonymmer.
			initializeAccountList(bank.getCustomer(currentCustomerpNo()).getAccountNoList());
		}

		// Fyll informationen i kundfälten.
		pNoField.setText(pNo);
		nameField.setText(name);
		surnameField.setText(surname);
	}

	/**
	 * Fyll de kundspecifika textfälten med information.
	 * 
	 * @param
	 * 		pNo Personnumret för den kund vars kontoinformation ska visas upp.
	 * @param 
	 * 		aNo Kontonumret för det konto vars information ska visas upp.
	 */
	private void displayAccountInfo(String pNo, int aNo) {
		String balance;
		String accountType;
		if (aNo == -1) {
			// Inget konto valt. Radera informationen i alla fält
			balance = "";
			accountType = "";
		} else {
			// Hämta informationen om aktuellt konto och fyll informationen i alla fält
			Account a = bank.getCustomer(pNo).getAccount(aNo);
			balance = String.format("%10.2f", a.getBalance());
			accountType = a.getAccountType();
		}
		// Fyll informationen i kontofälten.
		balanceField.setText(balance);
		accountTypeField.setText(accountType);
	}

	/**************************************************************
	 *  Metoder för action listeners som används på flera ställen.
	 **************************************************************/	


	/**
	 * Privat class för att implementera action listener för att ta bort en kund
	 * 
	 */
	private class DeleteCustomerListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int index = customerList.getSelectedIndex();
			// Kontrollera att en kund är vald.
			if ( index>= 0) {			
				Customer customer = bank.getCustomer(currentCustomerpNo());
				// Be användaren bekräfta att kunden ska tas bort
				boolean deleteConfirmed = confirmBox("Är du säker på att du vill ta bort kunden"  +  System.lineSeparator() +
						"Personnummer: "+ customer.getSocialSecurityNumber() + System.lineSeparator() +
						"Namn: "+ customer.getFullName()+ "?", "Borttag av kund");
				if (deleteConfirmed) {
					String pNo = currentCustomerpNo();
					// Användaren har bekräftat, ta bort kunden
					ArrayList<String> result = new ArrayList<String>();
					result = bank.deleteCustomer(pNo);
					String closingStatement = "";
					for (String s : result) {
						closingStatement += s + System.lineSeparator();
					}
					// Visa upp slutinfo om kundavslut i en MessageBox
					infoBox(closingStatement, "Kundavslut");
					customerModel.removeElement(pNo);
					// Avgör vilken kund som ska vara vald.
					int newIndex;
					if (customerModel.isEmpty()) {
						newIndex = -1;
					} else if (index == 0) {
						newIndex = 0;
					} else {
						newIndex = index - 1;
					}
					customerList.setSelectedIndex(newIndex);	
					updateGUI();
				}
			} else {
				infoBox("Ingen kund är markerad.", "Kundavslut");
			}
		}
	}

	/**
	 * Privat class för att implementera action listener för att skapa en kund
	 * 
	 */
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
			// Visa upp dialogrutan tills användaren antingen klickar på 'Cancel'
			// eller tills användare har gjort matat in giltiga värden i alla fält.
			while (!done) {
				result = JOptionPane.showConfirmDialog(null, inputs, "Skapa kund", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					// Till rapport
					String newName = nameField.getText();
					String newSurname = surnameField.getText();
					String newpNo = pNoField.getText();

					// Kontrollera inmatningsrfälten
					String validationResult = bank.validateCustomerInfo(newName, newSurname, newpNo);
					// Alla fält inehåller giltiga värden
					if (validationResult.equals("")) {
						// Skapa den nya kunden
						if (bank.createCustomer(newName, newSurname, newpNo)) {
							// bank.createCustomer(newName, newSurname, newpNo);
							infoBox("Kunden har skapats!", "Skapa kund");
							// Uppdatera kundlistan.
							customerModel.addElement(newpNo);
							customerList.setSelectedIndex(customerModel.indexOf(newpNo));
							updateGUI();
							done = true;
						} else {
							// Det gick inte bra. Det fanns redan en kund med detta personnummer.
							errorBox("Det finns redan en kund med detta personnummer!", "Skapa kund");
						}
					} else {
						// Användaren har gjort en felaktig inmatning.
						// Informera om vilken problem som finns.
						errorBox("Var vänlig kontrollera inmatningsfälten!" + System.lineSeparator() + validationResult,
								"Skapa kund");
					}
				} else {
					done = true;
				} 
			}
		}
	}

	/**
	 * Privat class för att implementera action listener för att skapa ett konto.
	 * 
	 */
	private class CreateAccountListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			// Till rapport
			// Kan inte få det att fungera med radio buttons. Det faller på att ButtonGroup inte är en JComponent.

			String[] accountTypes = AccountTypes.allAccountTypes().toArray(new String[0]);

			Object result = JOptionPane.showInputDialog(null, "Ange vilken typ av konto du vill skapa!", "Skapa konto", JOptionPane.OK_CANCEL_OPTION, null, accountTypes, "Sparkonto");
			// Låt användaren välja kontotype och skapa ett nytt konto i enlighet med valet.
			if (result != null) {
				int aNo = bank.createAccount(currentCustomerpNo(), AccountTypes.getAccountType(result.toString()));
				if (aNo == -1) {
					errorBox("Du har på något mystiskt sätt lyckats välja en kontotyp som är okänd. Var vänlig kontakta banken", "Skapa konto");
					return;
				}
				accountModel.addElement(aNo);
				accountList.setSelectedIndex(accountModel.indexOf(aNo));
				updateGUI();
			}
		}	
	}

	/**
	 * Privat class för att implementera action listener för att visa transaktioner.
	 * 
	 */
	private class ShowTransactionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// Kontrollera så att ett konto är valt.
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

	/**
	 * Privat class för att implementera action listener för att
	 * göra en insättning på ett konto.
	 * 
	 */
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
			// Visa upp dialogrutan och loopa 
			// tills användare har matat in ett giltigt belopp som det finns 
			// täcknng för på kontot eller tills användaren löickat på 'Cancel'.
			while (!done) {
				int result = JOptionPane.showConfirmDialog(null, inputs, "Insättning", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					double amount;
					// Testa om användaren matat in ett giltigt belopp.
					try {
						amount = Double.parseDouble(amountField.getText());
						if (bank.deposit(currentCustomerpNo(), currentAccountNo(), amount)) {
							displayAccountInfo(currentCustomerpNo(), currentAccountNo());
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


	/**
	 * Privat class för att implementera action listener för att
	 * göra ett uttag från ett konto.
	 * 
	 */
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
			// Visa upp dialogrutan och loopa 
			// tills användare har matat in ett giltigt belopp eller 
			// täcknng för på kontot eller tills användaren löickat på 'Cancel'.
			while (!done) {
				int result = JOptionPane.showConfirmDialog(null, inputs, "Uttag", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					double amount;
					// Testa om användaren matat in ett giltigt belopp.
					try {
						amount = Double.parseDouble(amountField.getText());
						if (bank.withdraw(currentCustomerpNo(), currentAccountNo(), amount)) {
							displayAccountInfo(currentCustomerpNo(), currentAccountNo());
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