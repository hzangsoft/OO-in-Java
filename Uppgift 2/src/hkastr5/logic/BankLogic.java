package logic;
/**
 * 
 * Klassen Banklogic hanterar bankens samtliga kunder. Informationen 
 * om kunderna läggs i en ArrayList.
 * 
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 */

import java.util.ArrayList;

import customer.CustomerList;

public class BankLogic {

	// En lista över bankens kunder.
	private CustomerList customerList;

	/**
	 * Defaultkonstruktor för klassen Banklogic.
	 */
	public BankLogic() {
		this.customerList = new CustomerList();
	}



	/**
	 * Skapar en ny kund med namnet name samt personnummer pNo, kunden skapas
	 * endast om det inte finns någon kund med personnummer pNo.
	 * 
	 * @param name
	 *            Kundens namn
	 * @param pNo
	 *            Kundens personnummer
	 * @return True om kund skapades.
	 * @return False om kund inte kunde skapas.
	 */
	public boolean createCustomer(String name, long pNo) {

		// Kontrollera om kunden redan finns
		if (customerList.customerExists(pNo)) {
			return false;
		} else {
			return customerList.addCustomer(name, pNo);
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
	public ArrayList<String> getCustomer(long pNo) {
		// Kontrollera om kunden finns och returnera kundinfo i så fall
		if (customerList.customerExists(pNo)) {
			return customerList.getCustomerInfo(pNo);
		} else {
			return null;
		}
	}

	/**
	 * Byter namn på kund med personnummer pNo till name.
	 * 
	 * @param name
	 *            Nya namnet
	 * @param pNo
	 *            Kundens personnummer
	 * @return True om namnet ändrades,
	 * @return False om namnet inte ändrades, t.ex.om kunden inte fanns.
	 */
	public boolean changeCustomerName(String name, long pNo) {
		// Kontrollera om kunden finns
		if (customerList.customerExists(pNo)) {
			// Ändra kundens namn.
			return customerList.changeCustomerName(name, pNo);
		} else {
			return false;
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

	public ArrayList<String> deleteCustomer(long pNo) {
		// Kontrollera om kunden finns
		if (customerList.customerExists(pNo)) {
			return customerList.deleteCustomer(pNo);
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
	public int createSavingsAccount(long pNo) {
		// Kontrollera om kunden finns
		if (customerList.customerExists(pNo)) {
			// Skapa kontot
			return customerList.createSavingsAccount(pNo);
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
	public String getAccount(long pNo, int accountId) {
		// Kontrollera om kunden finns
		if (customerList.customerExists(pNo)) {
			// Hämta kontoinformationen
			return customerList.getAccount(pNo, accountId);
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
	public boolean deposit(long pNo, int accountId, double amount) {

		// Kontrollera om kunden finns
		if (customerList.customerExists(pNo)) {
			return customerList.deposit(pNo, accountId, amount);
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
	public boolean withdraw(long pNo, int accountId, double amount) {
		// Kontrollera om kunden finns
		if (customerList.customerExists(pNo)) {
			return customerList.withdraw(pNo, accountId, amount);
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
	 *         på pengarna.
	 * @return Returnerar null om inget konto togs bort
	 */
	public String closeAccount(long pNo, int accountId) {
		if (customerList.customerExists(pNo)) {
			return customerList.closeAccount(pNo, accountId);
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
	public int createCreditAccount(long pNo) {
		// Kontrollera om kunden finns
		if (customerList.customerExists(pNo)) {
			// Skapa kontot
			return customerList.createCreditAccount(pNo);
		} else {
			return -1;
		}
	}

	/**
	 * Hämtar en lista som innehåller presentation av konto samt alla
	 * transaktioner som gjorts på kontot.
	 * 
	 * @param pNr
	 *            Kundens personnummer
	 * @param accountId
	 *            Kontonumret för det aktuella kontot.
	 * @return En ArrayList med strängar innehållande relevant information
	 */
	public ArrayList<String> getTransactions(long pNo, int accountId) {
		// Kontrollera om kunden finns
		if (customerList.customerExists(pNo)) {
			// Kontrollera om kontot finns
			return customerList.getTransactions(pNo, accountId);
		} else {
			return null;
		}
	}
	
	public ArrayList<String> getAllCustomers() {
		return customerList.getAllCustomers();
	}
	
}
