package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import logic.BankLogic;

public class BankFrame extends JFrame{
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 150;
	private final int TEXT_WIDTH = 15;
	
	private BankLogic bank;
		
	private JTextField nameField = new JTextField(TEXT_WIDTH);
	private JTextField pNoField = new JTextField(TEXT_WIDTH);
	private JTextField amountField = new JTextField(TEXT_WIDTH);
	
	private ListModelHandler<String> customers = new ListModelHandler<String>();
	private ListModelHandler<Integer> accounts = new ListModelHandler<Integer>();
	private JList<String> customerList;
	
	
	public BankFrame(BankLogic bank) {
		this.bank = bank;
		createComponents();
		pack();
		setLocationRelativeTo(null);
	}
	
	private void createComponents() {

		updateGUI();
			
		Border listBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Kundlista");
		Border customerBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Kundinfo");
		Border accountBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Kontolista");
 
		JPanel mainPanel = new JPanel(new GridLayout(0,3));
		JPanel listPanel = new JPanel(new GridLayout(0,1));
		JPanel customerDataPanel = new JPanel(new GridLayout(0,2));
		JPanel customerButtonPanel = new JPanel(new GridLayout(0,2));
		JPanel customerPanel = new JPanel(new GridLayout(2,1));
		JPanel accountListPanel = new JPanel(new GridLayout(0,1));
		JPanel accountEntryPanel = new JPanel(new GridLayout(0,2));
		JPanel accountButtonPanel = new JPanel(new GridLayout(0,2));
		JPanel accountPanel = new JPanel(new GridLayout(3,1));

		JScrollPane customerScrollPane = new JScrollPane(customerList);
		listPanel.add(customerScrollPane);
		listPanel.setBorder(listBorder);
		mainPanel.add(listPanel);

		customerDataPanel.add(new JLabel(" Namn"));
		customerDataPanel.add(nameField);
		customerDataPanel.add(new JLabel(" Personnummer"));
		customerDataPanel.add(pNoField);
		customerDataPanel.setBorder(customerBorder);
		customerButtonPanel.add(createAddUserButton());
		customerButtonPanel.add(createDeleteUserButton());
		customerPanel.add(customerDataPanel);
		customerPanel.add(customerButtonPanel);
		mainPanel.add(customerPanel);

		JScrollPane accountScrollPane = new JScrollPane(accountList);
		accountListPanel.add(accountScrollPane);
		accountPanel.add(accountListPanel);
		accountListPanel.setBorder(accountBorder);
		accountEntryPanel.add(new JLabel(" Summa"));
		accountEntryPanel.add(nameField);
		accountPanel.add(accountEntryPanel);
		accountButtonPanel.add(createDepositButton());
		accountButtonPanel.add(createWithDrawButton());
		accountPanel.add(accountButtonPanel);

		mainPanel.add(accountPanel);

		mainPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		add(mainPanel);
		
		createMenuBar();
	}

	public void updateGUI() {
		updateCustomerList();
		updateButtonStatus();
	}
	
	private void updateCustomerList() {
		customers.setListItems(bank.getAllCustomers());
		customerList = new JList<>(customers.getListModel());
	}

	private void updateAccountList(long pNo) {
		// Hämta list över kundens konton som sträng!!
		accounts.setListItems(bank.getAllCustomers());
		customerList = new JList<>(customers.getListModel());
	}
	private void updateButtonStatus() {
		if (customerList.getSelectedIndex() == -1) {
			
		} else {
			// TO-DO
		}
		
	}

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

	public static void infoBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
