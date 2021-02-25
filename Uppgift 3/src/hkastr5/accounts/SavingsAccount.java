package hkastr5.accounts;

import java.util.ArrayList;

/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen Account hanterar de aspekter av sparkonton som är specifika för sparkontot.
 *
 */
public class SavingsAccount extends Account {
	
	// Indikerar om det fria kontoutdraget har använts eller inte
	private boolean freeWithdrawalUsed; 
	private final static String accountType = "Sparkonto";


	
	/**
	 * Defaultkonstruktor
	 *
	 * Skapar ett nytt sparkonto med saldot 0 kronor och räntan 1.0%
	 */
	public SavingsAccount() {
		this(0.0, 1.0);
	}
	

	/**
	 * Konstruktor
	 * 
	 * @param balance
	 *            Behållningen på kontot.
	 * @param interestRate
	 *            Räntesatsen på kontot.
	 */
	public SavingsAccount(double balance, double interestRate) {
		super(balance, interestRate);
		freeWithdrawalUsed = false;
	}
	
	
	/** (non-Javadoc)
	 * @see Account#toString()
	 */
	@Override
	public String toString() {
		String s = new String();
		s += getAccountNumber() + " ";
		s += getBalance() + " kr ";
		s += "Sparkonto ";
		s += getInterestRate() + " %";
		return s;
	}

	/** (non-Javadoc)
	 * @see Account#withdraw(double)
	 */
	@Override
	public boolean withdraw(double amount) {
		double amountToWithdraw = amount;
		if (freeWithdrawalUsed) {
			final double WITHDRAWAL_FEE_RATE = 0.02;
			amountToWithdraw += amountToWithdraw * WITHDRAWAL_FEE_RATE;
		}
		if (isOpen()) {
			if (getBalance() >= amountToWithdraw) {
				setBalance(getBalance() - amountToWithdraw);
				logTransaction(-amountToWithdraw, getBalance());
				freeWithdrawalUsed = true;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/** (non-Javadoc)
	 * @see Account#deposit(double)
	 */
	@Override
	public boolean deposit (double amount) {
		if (isOpen()) {
			setBalance(getBalance() + amount);
			logTransaction(amount, getBalance());
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Generera en strängrepresentation av kontoinformationen
	 * 
	 * @return En sträng med kontoinformation
	 */
	@Override
	public String currentAccountStatement() {
		String s = new String();
		s += getAccountNumber() + " ";
		s += String.format("%.2f", getBalance()) + " kr ";
		s += "Sparkonto ";
		s += String.format("%.1f", getInterestRate()) + " %";
		return s;
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
	 * Generera en strängrepresentation av kontoinformationen
	 * 
	 * @return En sträng med kontoinformation
	 */
	@Override
	public String closingAccountStatement() {
		String s = "";
		s += String.format("%15d",getAccountNumber()) +" ";
		s += String.format("%15.2f", getBalance()) + " kr ";
		s += "   Sparkonto     ";
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
