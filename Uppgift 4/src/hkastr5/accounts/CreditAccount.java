package hkastr5.accounts;

import java.util.ArrayList;

/**
 *
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 4
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen CreditAccount hanterar de aspekter av kreditkonton som är specifika för kreditkontot.
 *
 */


public class CreditAccount extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2510030218633716459L;
	private final static double CREDIT_LIMIT = 5000.0;
	private final static double CREDIT_RATE = 7.0;
	private final static double DEBIT_RATE = 0.5;
	private final static String accountType = "Kreditkonto";
	/**
	 * Defaultkonstruktor
	 *
	 * Skapar ett nytt kreditkonto med saldot 0 kronor och räntan 0.5%
	 */
	public CreditAccount() {
		this(0.0, DEBIT_RATE);
	}

	/**
	 * Konstruktor
	 * 
	 * @param balance
	 *            Behållningen på kontot.
	 * @param interestRate
	 *            Räntesatsen på kontot.
	 */
	public CreditAccount(double balance, double interestRate) {
		super(balance, interestRate);
	}


	/** (non-Javadoc)
	 * @see Account#withdraw(double)
	 */
	@Override
	public boolean withdraw(double amount) {
		if (isOpen()) {
			if (getBalance() >= amount - CREDIT_LIMIT) {
				setBalance(getBalance() - amount);
				logTransaction(-amount, getBalance());
				updateInterestRate();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Gör en insättning på konto.
	 * 
	 * @param amount
	 *            Summan som ska sättas in på kontot.
	 * @return TRUE om allt gick bra
	 * @return FALSE om något gick fel, t.ex. att konto inte är öppet.
	 */
	public boolean deposit (double amount) {
		if (isOpen()) {
			setBalance(getBalance() + amount);
			logTransaction(amount, getBalance());
			updateInterestRate();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Uppdaterat räntesatsen på konton beroende på om saldot är positiv eller negativt..
	 * 
	 */
	private void updateInterestRate() {
		if (getBalance() < 0.0) {
			setInterestRate(CREDIT_RATE);		
		} else {
			setInterestRate(DEBIT_RATE);
		}
	}


	/**
	 * Generera en strängrepresentation av kontotypen.
	 * 
	 * @return En sträng med kontoinformation
	 */
	@Override
	public String getAccountType() {
		return accountType;
	}


	/**
	 * Generera en strängrepresentation av kontoinformationen vid kontoavslut
	 * 
	 * @return En sträng med kontoinformation
	 */
	@Override
	public String closingAccountStatement() {
		String s = new String();
		s += String.format("%15d",getAccountNumber()) +" ";
		s += String.format("%15.2f", getBalance()) + " kr ";
		s += "   Kreditkonto  ";
		s += String.format("%15.2f", calculateInterest()) + " kr";
		return s;
	}

	/**
	 * Generera en strängrepresentation av kontoinformationen vid kontoavslut
	 * 
	 * @return En ArrayList med strängar innehållande kontoinformation
	 */
	@Override
	public ArrayList<String> closingStatement() {
		ArrayList <String> result = new ArrayList<String>();
		result.add("Kontonummer: " + String.format("%15d",getAccountNumber()));
		result.add("Behållning:  " + String.format("%15.2f", getBalance()) + " kr ");
		result.add("Ränta:       " + String.format("%15.2f", calculateInterest()) + " kr");
		return result;
	}
}