package accounts;

import transactions.TransactionList;

import java.util.ArrayList;

import transactions.Transaction;

/**
 * Klassen Account hanterar gemensamma aspekter av olika kontotyper i banken.
 * 
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 */
abstract public class Account {
	// En klass variabel som används för att generera unika kontonummer.
	private static int latestAccountNumber = 1000;
	private final int accountNumber; // Kontonumret
	private double balance; // Kontots saldo
	private double interestRate; // Kontots räntesats
	private boolean accountOpen; // Anger om kontot är öppet eller inte
	private TransactionList transactions;

	/**
	 * Konstruktor
	 * 
	 * @param balance
	 *            Behållningen på kontot.
	 * @param interestRate
	 *            Räntesatsen på kontot.
	 */
	public Account(double balance, double interestRate) {
		latestAccountNumber++;
		this.accountNumber = latestAccountNumber;
		this.accountOpen = true;
		this.balance = balance;
		this.interestRate = interestRate;
		this.transactions = new TransactionList();
	}

	/**
	 * Default konstruktor. Sätter behållning till 0.0 kr och räntesats till 1.0
	 * %.
	 */
	public Account() {
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
	 * Sätter status på kontot till öppet.
	 * 
	 * @param balance
	 *            Den nya behållningen på kontot.
	 */
	public void setAccountOpen(boolean open) {
		this.accountOpen = open;
	}
	
	/**
	 * Getter-funktion för aktuellt saldo.
	 * 
	 * @return Det aktuella saldot på kontot.
	 */
	public double getBalance() {
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
	abstract public String toString();
	
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
	abstract public boolean deposit(double amount);

	/**
	 * Gör ett uttag från kontot om kontot är öppet och om behållningen på
	 * kontot är tillräckligt stor.
	 * 
	 * @param amount
	 *            Det belopp som skall dras från kontot.
	 * @return True om det gick bra, false i annat fall.
	 */
	abstract public boolean withdraw(double amount);
	
	/**
	 * Beräknar den upplupna räntan på kontot.
	 * 
	 * @return Den upplupna räntan på kontot.
	 */
	abstract public double calculateInterest();

	/**
	 * Om kontot är öppet, så markeras det som stängt och den upplupna räntan
	 * beräknas.
	 * 
	 * @return Den upplupna räntan på kontot.
	 */
	abstract public double closeAccount();
	
	/**
	 * Hämta de transaktioner som har gjorts på kontot.
	 * 
	 * @return En lista med transaktioner som har gjorts på kontot.
	 */
	public ArrayList<String> getTransactions() {
		return transactions.getTransactions();
	}
	
	/**
	 * Om kontot är öppet, så lagras en transaktion i transaktionslistan.
	 * 
	 * @return Den upplupna räntan på kontot.
	 */
	public void logTransaction(double amount, double balance) {
		transactions.add(new Transaction(amount, balance));
	}
}
