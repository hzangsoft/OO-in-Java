package hkastr5.customers;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
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
import hkastr5.accounts.*;


public class Customer {

	private String name; // Kundens förnamn
	private String surname; // Kundens efternamn
	private String socialSecurityNumber; // Kundens personnummer
	private final ArrayList<Account> accountList; // Kundens konton

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
		this.accountList = new ArrayList<Account>();
	}

	/**
	 * Copy constructor
	 * 
	 * @param c
	 *            Kunden som ska kopieras
	 */
	public Customer(Customer c) {
		super();
		this.name = c.name;
		this.surname = c.surname;
		this.socialSecurityNumber = c.socialSecurityNumber;
		this.accountList = new ArrayList<Account>();
		// Är detta det korrekta/bästa sättet att göra en deep copy av en ArrayList
		accountList.addAll(c.accountList);
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
	 * @param surname
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


	/**
	 * Validera att ett kundobjekt innehåller giltig information
	 * 
	 * @param c Kundobjektet som kontrolleras
	 * @return En tom sträng om informationen är giltig
	 * @return En sträng med ett felmeddelande om informationen är ogiltig
	 */
	public String validateCustomerInfo(Customer c) {
		String validationMessage = "";

		if (c.name.equals("")) {
			validationMessage+= "Förnamnsfältet får inte vara tomt." + System.lineSeparator(); 
		} 

		if (c.surname.equals("")) {
			validationMessage+= "Efternamnsfältet får inte vara tomt." + System.lineSeparator(); 
		} 
		if (c.socialSecurityNumber.equals("")) {
			validationMessage+="Personnummerfältet får inte vara tomt." + System.lineSeparator(); 
		}
		// Kontrollera att personnumret består av 10 siffror. 
		if (!c.socialSecurityNumber.matches("\\d{10}")) {
			validationMessage+= "Personnumret måste bestå av 10 siffror." + System.lineSeparator();
		}
		return validationMessage;
	}


	/**
	 * Getter-funktion för kundens fullständiga namn,
	 * 
	 * @return Kundens fullständiga namn
	 */
	public String getFullName() {
		return name + " " + surname;
	}


	/**
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


	/**
	 * Returnerar en ArrayList som innehåller en kunds alla kontonummer.
	 * 
	 * @return En lista med kundens kontonummer.
	 * @return En tom lista om kunde saknar konton
	 */
	public ArrayList<String> getAccountNoList() {
		ArrayList<String> result = new ArrayList<String>();
		if (!accountList.isEmpty()) {
			for (Account a : accountList) {
				result.add(Integer.toString(a.getAccountNumber()));
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
		ListIterator<Account> accountIterator = accountList.listIterator();
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
		SavingsAccount newAccount = new SavingsAccount();
		accountList.add(newAccount);
		return newAccount.getAccountNumber();
	}

	/**
	 * Skapa ett nytt kreditkonto för kunden.
	 * 
	 * @return Kontonumret för det nyskapade kontot
	 */
	public int createCreditAccount() {
		CreditAccount newAccount = new CreditAccount();
		accountList.add(newAccount);
		return newAccount.getAccountNumber();
	}

	/**
	 * Stäng ett konto för kunden.
	 * 
	 * @param accountId
	 *            Kontonumret för det konto som skall stängas.
	 * @return Information om det stängda kontot
	 * @return null om kontonumret inte ägs av kunden.
	 */
	public ArrayList <String> closeAccount(int accountId) {
		int index = getAccountIndex(accountId);
		ArrayList<String> result = new ArrayList<String>();
		// Kontrollera om konto finns
		if (index >= 0) {
			// Sätt samman informationen som skall returneras
			result = accountList.get(index).closingStatement();

			// Ta bort kontot ur listan
			accountList.remove(index);
		}
		return result;
	}

	/**
	 * Stäng alla konton som kunden har, och returnera en ArrayList med strängar
	 * innehållande information om de konton som stängts.
	 * 
	 * @return Information om det stängda kontona
	 */
	public ArrayList<String> closeAllAccounts() {
		ArrayList<String> result = new ArrayList<String>();
		if (!accountList.isEmpty()) {
			// Skapa rubrikrader
			result.add("Slutinformation för " + this.getSocialSecurityNumber() + " " + this.getFullName() +  System.lineSeparator());
			result.add("Kontonummer   Behållning    Kontotyp             Ränta" +  System.lineSeparator());
			// Iterera över alla konton
			for (Account a : accountList) {
				String accountInfo = new String();
				accountInfo = a.closingAccountStatement();
				result.add(accountInfo);
			}
			// Töm hela kontolistan.
			accountList.clear();
		} else {
			result.add("Kunden har inga konton.");
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
		boolean result = false;
		int index = getAccountIndex(accountId);
		// Kontrollera om kontot finns, och gör i så fall insättningen.
		if (index >= 0) {
			result = accountList.get(index).deposit(amount);
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
		boolean result = false;
		// Kontrollera om kontot finns, och gör i så fall uttaget.
		int index = getAccountIndex(accountId);
		if (index >= 0) {
			result = accountList.get(index).withdraw(amount);
		}
		return result;
	}

	/**
	 * Returnerar en sträng med information om kontot.
	 * 
	 * @param accountId
	 *            Kundens kontonummer
	 * @return Null om kontot inte fanns
	 * @return Kontot om kontot fanns
	 */
	public Account getAccount(int accountId) {
		int index = getAccountIndex(accountId);
		// Kontrollera om kontot finns, och hämta i så fall informationen.
		if (index >= 0) {
			return accountList.get(index);
		} else {
			return null;
		}
	}


	/**
	 * Hämtar kontotranskationer för ett givet konto.
	 * 
	 * @param accountId
	 *            Kundens kontonummer
	 * @return Null om kontot inte fanns
	 * @return En ArrayList med textsträngar innehållande transaktioninformation.
	 */
	public ArrayList <String> getTransactions(int accountId) {
		int index = getAccountIndex(accountId);
		// Kontrollera om kontot finns, och hämta i så fall informationen.
		if (index >= 0) {
			return accountList.get(index).getTransactions();
		} else {
			return null;
		}
	}
}
