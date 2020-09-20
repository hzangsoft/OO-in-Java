package gui;

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
import javax.swing.ListSelectionModel;
import javax.swing.event.*;

import logic.BankLogic;

public class BankFrame extends JFrame{
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 500;
	private final int TEXT_WIDTH = 15;
	
	private BankLogic bank;
		
	private JTextField nameField = new JTextField(TEXT_WIDTH);
	private JTextField pNoField = new JTextField(TEXT_WIDTH);
	private JTextField amountField = new JTextField(TEXT_WIDTH);
	
	private ListModelHandler<String> customers = new ListModelHandler<String>();
	private JList<String> customerList;

	
	private ListModelHandler<String> accounts = new ListModelHandler<String>();
	private JList<String> accountList;
	
	
	public BankFrame(BankLogic bank) {
		this.bank = bank;
		createComponents();
		pack();
		setLocationRelativeTo(null);
	}
	
	private void createComponents() {

		
		customers.setListItems(bank.getAllCustomers());
		customerList = new JList<>(customers.getListModel());
		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		accounts.clearListItems();
		accountList = new JList<>(accounts.getListModel());
		accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


//		Skapa huvudpanelen och de olika delpanelerna.
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		mainPanel.add(createCustomerListPanel(), BorderLayout.NORTH);
		mainPanel.add(createCustomerPanel(), BorderLayout.CENTER);
		mainPanel.add(createAccountPanel(), BorderLayout.SOUTH);

		mainPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		add(mainPanel);
		
		updateGUI();
					
//	Skapa menyerna
		
		createMenuBar();
	}
	
	public void updateGUI() {
		updateCustomerList();
		updateButtonStatus();
	}
	
	private void updateCustomerList() {
		customers.setListItems(bank.getAllCustomers());
		if (customerList.getModel().getSize() != -1) {
			customerList.setSelectedIndex(2);
		}

	}

	private void updateAccountList(long pNo) {
		// Hämta lista över kundens konton som strängar
		accounts.setListItems(bank.getAccountList(pNo));
		
	}
	
	private void updateButtonStatus() {
		/*
		 * if (customerList.getSelectedIndex() == -1) {
		 * 
		 * } else { // TO-DO }
		 */
	}

	
	/*
	 * Metoder för att skapa menuer och submenyer.
	 */	
	
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
	
	
	/*
	 * Metoder för att skapa knappar
	 */
	
	
	private JButton createAddUserButton() {
		class AddUserListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String name = nameField.getText();
				Long pNo = Long.parseLong(pNoField.getText());
				bank.createCustomer(name, pNo);
				updateGUI();				
			}
		}
		
		JButton button = new JButton("Lägg till kund");
		ActionListener listener = new AddUserListener();
		button.addActionListener(listener);
		return button;
	}
	

	private JButton createDeleteUserButton() {
		class DeleteUserListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				Long pNo = Long.parseLong(pNoField.getText());
				ArrayList<String> result = bank.deleteCustomer(pNo);
				updateGUI();
				infoBox("Kund med personnummer = " + pNo + " raderad!", "Info");				
			}
		}
		
		JButton button = new JButton("Radera kund");
		ActionListener listener = new DeleteUserListener();
		button.addActionListener(listener);
		return button;
	}

	private JButton createDepositButton() {
		class DepositListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				Long pNo = Long.parseLong(pNoField.getText());
				Double amount = Double.parseDouble(amountField.getText());
				updateGUI();
				infoBox("Uttag: " + amount, "Info");				
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
				Long pNo = Long.parseLong(pNoField.getText());
				Double amount = Double.parseDouble(amountField.getText());
				updateGUI();
				infoBox("Uttag: " + amount, "Info");				
			}
		}
		
		JButton button = new JButton("Uttag");
		ActionListener listener = new WithDrawListener();
		button.addActionListener(listener);
		return button;
	}
	
	
	
	/*
	 *  Metoder för att skapa paneler.
	*/
	private JPanel createCustomerListPanel() {
		
		/*
		 * class CreateCustomerListListener implements ActionListener { public void
		 * actionPerformed(ActionEvent event) { infoBox("Du har klickat i kundlistan!",
		 * "Kundlistan"); } }
		 */
		JPanel listPanel = new JPanel(new GridLayout(1,2,10,10));		
		Border listBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Kundlista");
		JScrollPane customerScrollPane = new JScrollPane(customerList);
		JPanel customerButtonPanel = new JPanel();
		customerButtonPanel.setLayout(new BoxLayout(customerButtonPanel, BoxLayout.PAGE_AXIS));
		customerButtonPanel.add(createAddUserButton());
		customerButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		customerButtonPanel.add(createDeleteUserButton());

		
		listPanel.add(customerScrollPane);
		listPanel.add(customerButtonPanel);
		listPanel.setBorder(listBorder);
		ListSelectionListener listener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){

					int customerIndex = customerList.getSelectedIndex();
					if ( customerIndex == -1) {
						// Kundlistan är tom. Radera eventuell information
						// i övriga paneler.

						//TO-DO
					} else {
						long pNo = Long.parseLong(customerList.getSelectedValue());	
						nameField.setText("Kalle");
						//Hämta kundens kontoinformation.
						updateAccountList(pNo);
					}
				}
			}
		};

		customerList.addListSelectionListener(listener);
		return listPanel;
		
	}

	private JPanel createCustomerPanel() {
		JPanel customerInfoPanel = new JPanel();
		customerInfoPanel.setLayout(new BoxLayout(customerInfoPanel, BoxLayout.LINE_AXIS));

		
		JPanel customerPanel = new JPanel(new GridLayout(1,1));
		Border customerBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Kundinfo");

		customerInfoPanel.add(new JLabel(" Namn"));
		customerInfoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		customerInfoPanel.add(nameField);
		customerInfoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		customerInfoPanel.add(new JLabel(" Personnummer"));
		customerInfoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		customerInfoPanel.add(pNoField);
		
		customerPanel.setBorder(customerBorder);		
		customerPanel.add(customerInfoPanel);
		return customerPanel;
		
	}
	
	private JPanel createAccountPanel() {
		Border accountBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Kontolista");
		JPanel accountListPanel = new JPanel(new GridLayout(0,1));
		JPanel accountEntryPanel = new JPanel(new GridLayout(0,2));
		JPanel accountPanel = new JPanel();
		accountPanel.setLayout(new BoxLayout(accountPanel,BoxLayout.LINE_AXIS));
		
		JPanel accountButtonPanel = new JPanel();
		accountButtonPanel.setLayout(new BoxLayout(accountButtonPanel, BoxLayout.PAGE_AXIS));
		accountButtonPanel.add(createDepositButton());
		accountButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		accountButtonPanel.add(createWithDrawButton());


		JScrollPane accountScrollPane = new JScrollPane(accountList);
		accountListPanel.add(accountScrollPane);
		accountPanel.add(accountListPanel);
		accountListPanel.setBorder(accountBorder);
		accountEntryPanel.add(new JLabel(" Summa"));
		accountEntryPanel.add(amountField);

		accountPanel.add(accountButtonPanel);

		return accountPanel;
		
	}
	
	
	
	public static void infoBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
