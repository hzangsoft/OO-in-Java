package hkastr5;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 1
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen Banklogic hanterar bankens samtliga kunder. Informationen 
 * om kunderna läggs i en ArrayList.
 * 
 */

import java.util.ArrayList;
import java.util.ListIterator;

public class BankLogic {

	// En lista över bankens kunder.
	private final ArrayList<Customer> customerList;

	/**
	 * Defaultkonstruktor för klassen Banklogic.
	 */
	public BankLogic() {
		super();
		this.customerList = new ArrayList<Customer>();
	}

	/**
	 * Returnerar en ArrayList som innehåller en presentation av bankens alla
	 * kunder (namn och personnummer?
	 * 
	 * @return En lista över bankens kunder.
	 */
	public ArrayList<String> getAllCustomers() {
		ArrayList<String> customers = new ArrayList<String>();
		for (Customer c : customerList) {
			customers.add(c.toString());
		}
		return customers;
	}

	/**
	 * Kontrollerar om en kund med personnummer pNo existerar.
	 * 
	 * @param pNo
	 *            Kundens personnummer
	 * @return True om kunden existerar.
	 * @return False om kunden inte existerar.
	 */
	private boolean customerExists(String pNo) {
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
	 * Skapar en ny kund med förnamnet name, efternamnet surname samt personnummer pNo. Kunden skapas
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
		// Kontrollera om kunden finns
		if (customerExists(pNo)) {
			return false;
		} else {
			// Lägg till kunden.
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
	public ArrayList<String> getCustomer(String pNo) {
		int index = getCustomerIndex(pNo);

		// Kontrollera om kunden finns och returnera kundinfo i så fall
		if (index >= 0) {
			return customerList.get(index).getInfo();
		} else {
			return null;
		}
	}

	/**
	 * Byter för- och efternamn på kund med personnummer pNo till name och surname.
	 * 
	 * @param name
	 *            Nya förnamnet
	 * @param surname
	 *            Nya efternamnet
	 * @param pNo
	 *            Kundens personnummer
	 * @return True om namnet ändrades,
	 * @return False om namnet inte ändrades, t.ex.om kunden inte fanns.
	 */
	public boolean changeCustomerName(String name, String surname, String pNo) {
		int index = getCustomerIndex(pNo);
		boolean nameChanged = false;

		// Kontrollera om kunden finns
		if (index > -1) {

			Customer customerToChange = customerList.get(index);

			// Kontrollera om förnamnet ska ändras och utför ändringen i så fall
			if (!name.equals("")) {
				customerToChange.setName(name);
				nameChanged = true;
			}
			// Kontrollera om efterrnamnet ska ändras och utför ändringen i så fall
			if (!surname.equals("")) {
				customerToChange.setSurname(surname);
				nameChanged = true;
			}

			// Spara den nya kundinformation om något av namnen har ändrats			
			if (nameChanged) {
				customerList.set(index, customerToChange);
			}
		}
		return nameChanged;
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
		if (index >= 0) {
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
			return customerList.get(index).createAccount();
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
	public String getAccount(String pNo, int accountId) {
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

		int index = getCustomerIndex(pNo);

		// Kontrollera om kunden finns
		if (index >= 0) {
			return customerList.get(index).deposit(accountId, amount);
		} else {
			return false;
		}
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

		// Kontrollera om kunden finns
		int index = getCustomerIndex(pNo);
		if (index >= 0) {
			return customerList.get(index).withdraw(accountId, amount);
		} else {
			return false;
		}
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
	 *         på kontobeloppet.
	 * @return Returnerar null om inget konto togs bort
	 */
	public String closeAccount(String pNo, int accountId) {
		// Kontrollera om kunden finns
		int index = getCustomerIndex(pNo);
		if (index >= 0) {
			return customerList.get(index).closeAccount(accountId);
		} else {
			return null;
		}
	}

}
