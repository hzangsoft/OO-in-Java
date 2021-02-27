package hkastr5.logic;
/**
 * 
 *
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen Banklogic hanterar bankens samtliga kunder. Informationen 
 * om kunderna läggs i en ArrayList.
 * 
 * 
 */

import java.util.ArrayList;
import java.util.ListIterator;

import hkastr5.accounts.Account;
import hkastr5.customers.*;

public class BankLogic {

	// En lista över bankens kunder.
		private ArrayList<Customer> customerList;


	/**
	 * Konstruktor för klassen Banklogic.
	 * 
	 * @param createTestData
	 * 			Anger om de testdata som funns i klassen ska användas.
	 */
	public BankLogic(boolean createTestData) {
		this.customerList = new ArrayList<Customer>();
		if (createTestData) {
			createTestData();
		}
	}

	
	/**
	 * Fyller banken med testdata.
	 */
	private void createTestData() {

		
		// Create customers
		
		this.createCustomer("Karl", "Karlsson", "8505221898");
		this.createCustomer("Donald", "Duck", "9302205513");
		this.createCustomer("Pelle", "Persson", "6911258876");
		this.createCustomer("Lotta", "Larsson", "7505121231");
		this.createCustomer("Bob", "Hamman", "7505221898");
		this.createCustomer("Jeff", "Meckstroth", "8302205513");
		this.createCustomer("Eric", "Rodwell", "5911258876");
		this.createCustomer("Peter", "Fredin", "6505121231");
		this.createCustomer("Giorgio", "Belladonna", "6505221898");
		this.createCustomer("Simon", "Hult", "7302205513");
		this.createCustomer("Jan", "Kamras", "4911258876");
		this.createCustomer("Steve", "Weinstein", "5505121231");
		this.createCustomer("Magnus", "Carlsen", "5505221898");
		this.createCustomer("Bobby", "Fischer", "6302205513");
		this.createCustomer("Boris", "Spasskij", "3911258876");
		this.createCustomer("Anatoly", "Karpov", "4505121231");


		// Creates accounts
		String pNo;
		int a;
		
		pNo = "8505221898";
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 100.0);
		this.deposit(pNo, a, 50.0);
		this.deposit(pNo, a, 60.0);
		this.deposit(pNo, a, 70.0);
		this.deposit(pNo, a, 80.0);
		this.deposit(pNo, a, 90.0);
		
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 200.0);
		
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 300.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 400.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 500.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 600.0);
		
		
		pNo = "9302205513";
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 700.0);
		
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 800.0);
		
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 900.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 1000.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 1100.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 1200.0);
		
		pNo = "6911258876";
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 1300.0);
		
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 1400.0);
		
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 1500.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 1600.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 1700.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 1800.0);
		
		
		pNo= "7505121231";
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 1900.0);
		
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 2000.0);
		
		a = this.createCreditAccount(pNo);
		this.deposit(pNo, a, 2100.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 2200.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 2300.0);
		
		a = this.createSavingsAccount(pNo);
		this.deposit(pNo, a, 2400.0);
		
	}

	
	/**
	 * Kontrollerar om en kund med personnummer pNo existerar.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @return True om kunden existerar.
	 * @return False om kunden inte existerar.
	 */
	public boolean customerExists(String pNo) {
		return getCustomerIndex(pNo) >= 0;
	}

	/**
	 * Returnerar indexet i customerlist till kunden med personnummer pNo.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @return Indexet om kunden existerar.
	 * @return -1 om kunden inte existerar.
	 */
	private int getCustomerIndex(String pNo) {
		int index = -1;

		// Iterera över listan tills vi har hittat kunden eller tills listan är
		// slut.
		ListIterator<Customer> customerIterator = customerList.listIterator();
		while ((customerIterator.hasNext()) && (index == -1)) {
			if (customerIterator.next().getSocialSecurityNumber().equals(pNo)) {
				index = customerIterator.previousIndex();
			}
		}
		return index;
	}
	
	/**
	 * Skapar en ny kund med namnet name samt personnummer pNo, kunden skapas
	 * endast om det inte finns någon kund med personnummer pNo.
	 * 
	 * @param name
	 *            Kundens förnamn
	 * @param surname
	 *            Kundens efternamn
	 * @param pNo
	 *            Kundens personnummer
	 * @return True om kund skapades.
	 * @return False om kund inte kunde skapas.
	 */
	public boolean createCustomer(String name, String surname, String pNo) {

		// Kontrollera om kunden redan finns
		if (customerExists(pNo)) {
			return false;
		} else {
			return addCustomer(name, surname, pNo);
		}
	}

	/**
	 * Lägg till en kund i listan.
	 * @param name
	 *            Kundens namn
	 * @param surname           
	 *            Kundens efternamn
	 * @param pNo
	 *            Kundens personnummer
	 * @return True om kunden kunde läggas till.
	 * @return False om kunden redan fanns.
	 */
	private boolean addCustomer(String name, String surname, String pNo) {
		int index = getCustomerIndex(pNo);

		// Kontrollera om kunden redan finns och returnera false i så fall.
		if (index >= 0) {
			return false;
		} else {
			return customerList.add(new Customer(name, surname, pNo));
		}
	}
	/**
	 * Returnerar en ArrayList som innehåller informationen om kunden inklusive
	 * dennes konton. Första platsen i listan är reserverad för kundens namn och
	 * personnummer sedan följer informationen om kundens konton.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @return En ArrayList med strängar innehållande kundinformation.
	 * @return Null om kunden inte fanns
	 */
	public Customer getCustomer(String pNo) {
		// Kontrollera om kunden finns och returnera kundinfo i så fall
		int index = getCustomerIndex(pNo);
		
		if (index >= 0) {
			return customerList.get(index);
		} else {
			return null;
		}
	}
	

	/**
	 * Byter namn på kund med personnummer pNo till name.
	 * 
	 * @param pNo
	 *            Kundens nuvarande personnummer
	 * @param newName
	 *            Nya namnet
	 * @param newpNo
	 *            Kundens nya personnummer

	 */
	public void changeCustomerName(String pNo, String newName, String newSurname) {
		int index = getCustomerIndex(pNo);
		// Kontrollera om kunden finns
		if (index >= 0) {
			// Här används copykonstruktorn.
			Customer c = new Customer(customerList.get(index));
			// Ändra kundens namn.
			c.setName(newName);
			c.setSurname(newSurname);
			customerList.set(index, c);
		}
	}

	/**
	 * Tar bort kund med personnummer pNo ur banken, alla kundens eventuella
	 * konton tas också bort.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @return En lista som innehåller information om alla konton som togs bort,
	 *         saldot som kunden får tillbaka samt vad räntan blev.
	 * @return Returnerar null om ingen kund togs bort
	 */

	public ArrayList<String> deleteCustomer(String pNo) {
		int index = getCustomerIndex(pNo);

		// Kontrollera om kunden finns
		if (index >= 0)	{
			ArrayList<String> result = new ArrayList<String>();
			result = customerList.get(index).closeAllAccounts();
			customerList.remove(index);
			return result;
		} else {
			return null;
		}
	}

	/**
	 * Skapar ett sparkonto till kund med personnummer pNo.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @return Kontonumret som det skapade kontot fick.
	 * @return –1 om inget konto skapades.
	 */
	public int createSavingsAccount(String pNo) {
		int index = getCustomerIndex(pNo);
		// Kontrollera om kunden finns
		if (index >= 0) {
			// Skapa kontot
			return customerList.get(index).createSavingsAccount();
		} else {
			return -1;
		}
	}

	/**
	 * @param pNo
	 *            Kundens personnummer
	 * @param accountId
	 *            Kundens kontonummer
	 * @return En String som innehåller presentation av kontot med kontonnummer
	 *         accountId som tillhör kunden pNo (kontonummer, saldo, kontotyp,
	 *         räntesats).
	 * @return Null om konto inte finns eller om det inte tillhör kunden
	 */
	public Account getAccount(String pNo, int accountId) {
		int index = getCustomerIndex(pNo);		

		// Kontrollera om kunden finns
		if (index >= 0) {
			// Hämta kontoinformationen
			return customerList.get(index).getAccount(accountId);
		} else {
			return null;
		}
	}

	/**
	 * Gör en insättning på konto med kontonnummer accountId som tillhör kunden
	 * pNo.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @param accountId
	 *            Kundens kontonummer
	 * @param amount
	 *            Summan som sätts in
	 * @return True om det gick bra annars false
	 */
	public boolean deposit(String pNo, int accountId, double amount) {

		boolean result = false;
		int index = getCustomerIndex(pNo);

		// Kontrollera om kunden finns
		if (index >= 0) {
			result = customerList.get(index).deposit(accountId, amount);
		}
		return result;
	}



	/**
	 * Gör ett uttag på konto med kontonnummer accountId som tillhör kunden pNo.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @param accountId
	 *            Kundens kontonummer
	 * @param amount
	 *            Summan som tas ut
	 * @return True om det gick bra annars false
	 */
	public boolean withdraw(String pNo, int accountId, double amount) {
		
		boolean	result = false;

		// Kontrollera om kunden finns
		int index = getCustomerIndex(pNo);
		if (index >= 0) {
			result = customerList.get(index).withdraw(accountId, amount);
		}
		return result;
	}

	/**
	 * Avslutar ett konto med kontonnummer accountId som tillhör kunden pNo. När
	 * man avslutar ett konto skall räntan beräknas som saldo multiplicerat med
	 * ränta/100. OBS! Enda gången ränta läggs på är när kontot tas bort
	 * eftersom årsskiften inte hanteras i denna version av systemet.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @param accountId
	 *            Kundens kontonummer
	 * @return En string som ger information om kontot, inklusive räntan man får
	 *         på pengarna.
	 * @return Returnerar null om inget konto togs bort
	 */
	public ArrayList <String> closeAccount(String pNo, int accountId) {

		// Kontrollera om kunden finns
		int index = getCustomerIndex(pNo);
		if (index >= 0) {
			return customerList.get(index).closeAccount(accountId);
		} else {
			return null;
		}
	}

	/**
	 * Skapar ett kreditkonto till kund med personnummer pNr
	 * 
	 * @param pNr
	 *            Kundens personnummer
	 * @return Kontonumret för det skapade kontot om allt gick bra
	 * @return -1 om inget konto skapades
	 */
	public int createCreditAccount(String pNo) {
		int index =	getCustomerIndex(pNo);

		// Kontrollera om kunden finns
		if (index >= 0) {
			// Skapa kontot
			return customerList.get(index).createCreditAccount();
		} else {
			return -1;
		}
	}

	/**
	 * Hämtar en lista som innehåller presentation av konto samt alla
	 * transaktioner som gjorts på kontot.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @param accountId
	 *            Kontonumret för det aktuella kontot.
	 * @return En ArrayList med strängar innehållande relevant information
	 */
	public ArrayList<String> getTransactions(String pNo, int accountId) {
		
		int customerIndex = getCustomerIndex(pNo);
		ArrayList<String> result = new ArrayList<String>(); 

		// Kontrollera om kunden finns
		if (customerIndex >= 0) {
			// Kontrollera om kontot finns
			if (customerList.get(customerIndex).accountExists(accountId)) {
				result = customerList.get(customerIndex).getTransactions(accountId);
			}
		}
		return result;
	}
	
	/**
	 * Hämtar en lista som innehåller alla konton för en given kund.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @return En ArrayList med strängar innehållande relevant information
	 */
	public ArrayList<Integer> getAccountList(String pNo) {
		// Kontrollera om kunden finns
		if (customerExists(pNo)) {
			// Kontrollera om kontot finns
			return getAccountList(pNo);
		} else {
			return null;
		}
	}

	/**
	 * Hämtar en lista som innehåller en lista med personnummer på alla kunder.
	 * 
	 * @return En ArrayList med strängar kundernas personnummer.
	 */
	public ArrayList<String> getAllCustomerSSNs() {
		ArrayList<String> customers = new ArrayList<String>();
		for (Customer c : customerList) {
			customers.add(c.getSocialSecurityNumber());
		}
		return customers;
	}

	/**
	 * Validera att kundinformationen är giltig.
	 * 
	 * @param name Kundens förnamn
	 * @param surname Kundens efternamn
	 * @param pNo Kundens personnummer
	 * @return En tom sträng om informationen är giltig
	 * @return En sträng med ett felmeddelande om informationen är ogiltig
	 */
	public String validateCustomerInfo(String name, String surname, String pNo) {
		Customer c = new Customer(name, surname, pNo);
		return c.validateCustomerInfo(c);
	}
}