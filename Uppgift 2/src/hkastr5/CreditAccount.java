package hkastr5;
/**
 * 
 */

/**
 *
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 1
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 *
 */
public class CreditAccount extends Account {
	private final static double CREDIT_LIMIT = 5000.0;
	private final static double CREDIT_RATE = 7.0;
	private final static double DEBIT_RATE = 0.5;
	
	public CreditAccount() {
		super(0.0, 0.5);
	}

	public CreditAccount(double balance, double interestRate) {
		super(balance, interestRate);
	}


/* (non-Javadoc)
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

	private void updateInterestRate() {
		if (getBalance() < 0.0) {
			setInterestRate(CREDIT_RATE);		
		} else {
			setInterestRate(DEBIT_RATE);
		}
	}

	/*
	 * Generera en strängrepresentation av kontoinformationen
	 * 
	 * @return En sträng med kontoinformation
	 */
	@Override
	public String currentAccountStatement() {
		String s = new String();
		s += getAccountNumber() + " ";
		s += String.format("%.2f", getBalance()) + " kr ";
		s += "Kreditkonto ";
		s += String.format("%.1f", getInterestRate()) + " %";
		return s;
	}

	/*
	 * Generera en strängrepresentation av kontoinformationen
	 * 
	 * @return En sträng med kontoinformation
	 */
	@Override
	public String closingAccountStatement() {
		String s = new String();
		s += getAccountNumber() + " ";
		s += String.format("%.2f", getBalance()) + " kr ";
		s += "Kreditkonto ";
		s += String.format("%.2f", calculateInterest()) + " kr";
		return s;
	}
	
}
