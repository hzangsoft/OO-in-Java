package accounts;
/**
 * 
 */

/**
 * @author Håkan
 *
 */
public class SavingsAccount extends Account {
	
	private boolean freeWithdrawalUsed; 

	public SavingsAccount() {
		super(0.0, 1.0);

		freeWithdrawalUsed = false;
	}
	public SavingsAccount(double balance, double interestRate) {
		super(balance, interestRate);

		freeWithdrawalUsed = false;
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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

	public boolean deposit (double amount) {
		if (isOpen()) {
			setBalance(getBalance() + amount);
			logTransaction(amount, getBalance());
			return true;
		} else {
			return false;
		}
	}
}
