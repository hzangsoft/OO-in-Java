package customer;
/**
 * Klassen Customer hanterar all information om en av bankens kunder.
 * Förutom den grundläggande informationen om en kunden så hanteras
 * också kundens alla konton som lagras i en ArrayList.
 * 
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 */

import java.util.ArrayList;
import java.util.ListIterator;

import accounts.Account;
import accounts.CreditAccount;
import accounts.SavingsAccount;

public class Customer {

	private String name; // Kundens namn
	private long socialSecurityNumber; // Kundens personnummer
	private final ArrayList<Account> accountList; // Kundens konton



	/**
	 * Konstruktor
	 * 
	 * @param name
	 *            Kundens namn
	 * @param socialSecurityNumber
	 *            Kundens personnummer
	 */
	public Customer(String name, long socialSecurityNumber) {
		super();
		this.name = name;
		this.socialSecurityNumber = socialSecurityNumber;
		this.accountList = new ArrayList<Account>();
	}

	
	
	/**
	 * Getter-funktion för kundens namn
	 * 
	 * @return Kundens namn
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter-funktion för kundens namn
	 * 
	 * @param name
	 *            Kundens nya namn.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter-funktion för kundens personnummer,
	 * 
	 * @return Kundens personnummer
	 */
	public long getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	/**
	 * Setter-funktion för kundens namn
	 * 
	 * @param socialSecurityNumber
	 *            Kundens nya personnummer
	 */
	public void setSocialSecurityNumber(long socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * 
	 * @return En strängrepresentation av kundens alla kontonummer.
	 */
	
	
	public ArrayList<String> getAccountList() {
		ArrayList<String> result = new ArrayList<String>();
		if (!accountList.isEmpty()) {
		
			// Iterera över alla konton
			for (Account a : accountList) {
				Integer accountNumber;
				accountNumber = a.getAccountNumber();
				result.add(accountNumber.toString());
			}
		}
		return result;
	}
	
	
	
	@Override
	public String toString() {
		return name + " " + socialSecurityNumber;
	}

	/*
	 * Returnerar en ArrayList som innehåller en presentation av kunden och
	 * kundens alla konton.
	 * 
	 * @return En lista med kund- och kontoinformation.
	 */
	public ArrayList<String> getInfo() {
		ArrayList<String> result = new ArrayList<String>();
		result.add(this.toString());
		if (!accountList.isEmpty()) {
			for (Account a : accountList) {
				String s = a.toString();
				result.add(s);
			}
		}
		return result;
	}

	/**
	 * Kontrollerar om ett konto med nummer accountNo existerar.
	 * 
	 * @param accountNo
	 *            Kontonumret
	 * @return True om kontonumret existerar.
	 * @return False om kontonumret inte existerar.
	 */
	public boolean accountExists(int accountNo) {
		return getAccountIndex(accountNo) >= 0;

	}

	/**
	 * Returnerar indexet i accountlist till konto med nummer accountNo.
	 * 
	 * @param accountNo
	 *            Kontonumret
	 * @return Indexet om kontot existerar.
	 * @return -1 om kontot inte existerar.
	 */
	private int getAccountIndex(int accountNo) {
		int index = -1;
		ListIterator<Account> accountIterator = accountList.listIterator();
		// Iterera över alla konton tills kontonumret har hittas eller
		// tills listan är slut.
		while ((accountIterator.hasNext()) && (index == -1)) {
			if (accountIterator.next().getAccountNumber() == accountNo) {
				index = accountIterator.previousIndex();
			}
		}
		return index;
	}

	/**
	 * Skapa ett nytt sparkonto för kunden.
	 * 
	 * @return Kontonumret för det nyskapade kontot
	 */
	public int createSavingsAccount() {
		Account newAccount = new SavingsAccount();
		accountList.add(newAccount);
		return newAccount.getAccountNumber();
	}

	/**
	 * Skapa ett nytt kreditkonto för kunden.
	 * 
	 * @return Kontonumret för det nyskapade kontot
	 */
	public int createCreditAccount() {
		Account newAccount = new CreditAccount();
		accountList.add(newAccount);
		return newAccount.getAccountNumber();
	}

	/**
	 * Skapa en strängrepresentation av ett avslutat konto
	 * 
	 * @param Indexet
	 *            till det konto i kontolistan som avses.
	 * @return Kontoinformation inkl. räntebesked.
	 */
	private String getClosingStatement(int index) {
		String result = new String();
		result = accountList.get(index).toString();
		result += " " + accountList.get(index).closeAccount() + " kr";
		return result;
	}

	/**
	 * Stäng ett konto för kunden.
	 * 
	 * @param accountId
	 *            Kontonumret för det konto som skall stängas.
	 * @return Information om det stängda kontot
	 */
	public String closeAccount(int accountId) {
		int index = getAccountIndex(accountId);
		// Kontrollera om konto finns
		if (index >= 0) {
			// Sätt samman informationen som skall returneras
			String result = new String();
			result = accountList.get(index).toString();
			result += " " + accountList.get(index).closeAccount() + " kr";
			// Ta bort kontot ur listan
			accountList.remove(index);
			return result;
		} else {
			return null;
		}
	}

	/**
	 * Stäng alla konton som kunden har, och returnera en ArrayList med strängar
	 * innehållande information om de konton som stängts.
	 * 
	 * @return Information om det stängda kontona
	 */
	public ArrayList<String> closeAllAccounts() {
		ArrayList<String> result = new ArrayList<String>();
		result.add(this.toString());
		if (!accountList.isEmpty()) {
		
			// Iterera över alla konton
			for (Account a : accountList) {
				String accountInfo = new String();
				accountInfo = a.toString();
				accountInfo += " " + a.closeAccount() + " kr";
				result.add(accountInfo);
			}
			// Töm hela kontolistan.
			accountList.clear();
		}
		return result;
	}

	/**
	 * Gör en insättning på konto med kontonnummer accountId.
	 * 
	 * @param accountId
	 *            Kundens kontonummer
	 * @param amount
	 *            Summan som sätts in
	 * @return True om det gick bra annars false
	 */
	public boolean deposit(int accountId, double amount) {
		boolean result = true;
		int index = getAccountIndex(accountId);
		// Kontrollera om kontot finns, och gör i så fall insättningen.
		if (index >= 0) {
			return accountList.get(index).deposit(amount);
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Gör ett uttag på konto med kontonnummer accountId.
	 * 
	 * @param accountId
	 *            Kundens kontonummer
	 * @param amount
	 *            Summan som tas ut
	 * @return True om det gick bra annars false
	 */
	public boolean withdraw(int accountId, double amount) {
		boolean result = true;
		// Kontrollera om kontot finns, och gör i så fall insättningen.
		int index = getAccountIndex(accountId);
		if (index >= 0) {
			return accountList.get(index).withdraw(amount);
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Returnerar en sträng med information om kontot.
	 * 
	 * @param accountId
	 *            Kundens kontonummer
	 * @return Null om kontot inte fanns
	 * @return En sträng med kontoinformation om kontot fanns
	 */
	public String getAccount(int accountId) {
		int index = getAccountIndex(accountId);
		// Kontrollera om kontot finns, och hämta i så fall informationen.
		if (index >= 0) {
			return accountList.get(index).toString();
		} else {
			return null;
		}
	}
	
	/**
	 * Hämtar en lista som innehåller presentation av konto samt alla
	 * transaktioner som gjorts på kontot.
	 * 
	 * @param accountId
	 *            Kontonumret för det aktuella kontot.
	 * @return En ArrayList med strängar innehållande relevant information
	 */
	public ArrayList<String> getTransactions(int accountId) {
		int accountIndex = getAccountIndex(accountId);

		// Kontrollera om kontot finns
		if (accountIndex >= 0) {
				return accountList.get(accountIndex).getTransactions();
		} else {
			return null;
		}
	}
}
