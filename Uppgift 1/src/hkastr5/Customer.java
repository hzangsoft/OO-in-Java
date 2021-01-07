package hkastr5;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 1
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen Customer hanterar all information om en av bankens kunder.
 * Förutom den grundläggande informationen om en kunden så hanteras
 * också kundens alla konton som lagras i en ArrayList.
 * 
 * 
 */

import java.util.ArrayList;
import java.util.ListIterator;

public class Customer {

	private String name; // Kundens förnamn
	private String surname; // Kundens efternamn
	private String socialSecurityNumber; // Kundens personnummer
	private final ArrayList<SavingsAccount> accountList; // Kundens konton

	/**
	 * Konstruktor
	 * 
	 * @param name
	 *            Kundens namn
	 * @param surname           
	 *            Kundens efternamn
	 * @param socialSecurityNumber
	 *            Kundens personnummer
	 */
	public Customer(String name, String surname, String socialSecurityNumber) {
		super();
		this.name = name;
		this.surname = surname;
		this.socialSecurityNumber = socialSecurityNumber;
		this.accountList = new ArrayList<SavingsAccount>();
	}

	/**
	 * Getter-funktion för kundens förnamn
	 * 
	 * @return Kundens förnamn
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter-funktion för kundens förnamn
	 * 
	 * @param name
	 *            Kundens nya förnamn.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter-funktion för kundens efternamn
	 * 
	 * @return Kundens efternamn
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Setter-funktion för kundens efternamn
	 * 
	 * @param name
	 *            Kundens nya efternamn.
	 */

	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Getter-funktion för kundens personnummer,
	 * 
	 * @return Kundens personnummer
	 */
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	/**
	 * Setter-funktion för kundens personnummer.
	 * 
	 * @param socialSecurityNumber
	 *            Kundens nya personnummer
	 */
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * 
	 * @return En strängrepresentation av kundinformationen innehållande namn
	 * och personnummer.
	 */
	@Override
	public String toString() {
		return socialSecurityNumber + " " + name + " " + surname;
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
			for (SavingsAccount a : accountList) {
				String s = a.currentAccountStatement();
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
		ListIterator<SavingsAccount> accountIterator = accountList
				.listIterator();

		// Iterera över alla konton tills kontonumret har hittas eller
		// tills listan är slut.
		while (accountIterator.hasNext()) {
			if (accountIterator.next().getAccountNumber() == accountNo) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returnerar indexet i accountlist till konto med nummer accountNo.
	 * 
	 * @param accountNo
	 *            Kontonumret
	 * @return Indexet om kontot existerar.
	 * @return -1 om kontot inte existerar.
	 */
	public int getAccountIndex(int accountNo) {
		int index = -1;
		ListIterator<SavingsAccount> accountIterator = accountList
				.listIterator();
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
	 * Skapa ett nytt konto för kunden.
	 * 
	 * @return Kontonumret för det nyskapade kontot
	 */
	public int createAccount() {
		SavingsAccount newAccount = new SavingsAccount();
		accountList.add(newAccount);
		return newAccount.getAccountNumber();
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
			result = accountList.get(index).closingAccountStatement();
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
			for (SavingsAccount a : accountList) {
				String accountInfo = new String();
				accountInfo = a.closingAccountStatement();
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
			return accountList.get(index).currentAccountStatement();
		} else {
			return null;
		}
	}
}
