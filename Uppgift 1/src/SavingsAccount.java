/**
 * Klassen SavingsAccount hanterar ett sparkonto i banken.
 * 
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 */
public class SavingsAccount {
	// En klassvariabel som används för att generera unika kontonummer.
	private static int latestAccountNumber = 1000;
	
	private final int accountNumber; // Kontonumret
	private double balance; // Kontots saldo
	private double interestRate; // Kontots räntesats
	private boolean accountOpen; // Anger om kontot är öppet eller inte

	/**
	 * Konstruktor
	 * 
	 * @param balance
	 *            Behållningen på kontot.
	 * @param interestRate
	 *            Räntesatsen på kontot.
	 */
	public SavingsAccount(double balance, double interestRate) {
		latestAccountNumber++;
		this.accountNumber = latestAccountNumber;
		this.accountOpen = true;
		this.balance = balance;
		this.interestRate = interestRate;
	}

	/**
	 * Default konstruktor. Sätter behållning till 0.0 kr och räntesats till 1.0%.
	 */
	public SavingsAccount() {
		this(0.0, 1.0);
	}

	/**
	 * Getter-funktion för kontonumret.
	 * 
	 * @return Kontonumret
	 */
	public int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Getter-funktion för aktuellt saldo.
	 * 
	 * @return Det aktuella saldot på kontot.
	 */
	private double getBalance() {
		return balance;
	}

	/**
	 * Sätter behållningen på kontot.
	 * 
	 * @param balance
	 *            Den nya behållningen på kontot.
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Getter-funktion för aktuellt räntesats.
	 * 
	 * @return Den aktuella ränteatsen på kontot.
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * Sätter räntesatsen för kontot.
	 * 
	 * @param interestRate
	 *            Den nya räntesatsen för kontot.
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return Det senast använda kontonumret.
	 */
	public static int getLatestAccountNumber() {
		return latestAccountNumber;
	}

	/*
	 * Generera en strängrepresentation av kontoinformationen
	 * 
	 * @return En sträng med kontoinformation
	 */
	@Override
	public String toString() {
		String s = new String();
		s += accountNumber + " ";
		s += balance + " kr ";
		s += "Sparkonto ";
		s += interestRate + " %";
		return s;
	}

	/**
	 * Kontrollerar om kontot är öppet.
	 * 
	 * @return True om kontot är öppet.
	 * @return Falskt om kontot är stängt.
	 */
	public boolean isOpen() {
		return accountOpen;
	}

	/**
	 * Sätter in ett belopp på kontot om kontot är öppet.
	 * 
	 * @param amount
	 *            Det belopp som skall aättas in på kontot.
	 * @return Sant om det gick bra, falst annars.
	 */
	public boolean deposit(double amount) {
		if (accountOpen) {
			balance += amount;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gör ett uttag från kontot om kontot är öppet och om behållningen på
	 * kontot är tillräckligt stor.
	 * 
	 * @param amount
	 *            Det belopp som skall dras från kontot.
	 * @return True om det gick bra, false i annat fall.
	 */
	public boolean withdraw(double amount) {
		if (accountOpen) {
			if (balance >= amount) {
				balance -= amount;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * Beräknar den upplupna räntan på kontot.
	 * 
	 * @return Den upplupna räntan på kontot.
	 */
	private double calculateInterest() {
		return balance * interestRate / 100;
	}

	/**
	 * Om kontot är öppet, så markeras det som stängt och den upplupna räntan
	 * beräknas.
	 * 
	 * @return Den upplupna räntan på kontot om kontot var öppet.
	 * @return -1 om kontot redan var stängt.
	 */
	public double closeAccount() {
		if (accountOpen) {
			accountOpen = false;
			return calculateInterest();

		} else {
			return -1.0;
		}
	}
}
