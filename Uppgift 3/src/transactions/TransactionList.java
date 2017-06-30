package transactions;

import java.util.ArrayList;
import java.util.ListIterator;

public class TransactionList {

	private ArrayList<Transaction> transactionList;

	/**
	 * 
	 */
	public TransactionList() {
		this.transactionList = new ArrayList<Transaction>();
	}
	
	public void add(Transaction transaction) {
		transactionList.add(transaction);
	}
	

	public ArrayList<String> getTransactions() {

		String s ="";
		if (!transactionList.isEmpty()) {
			
			ListIterator<Transaction> iterator = transactionList.listIterator();
			while (iterator.hasNext()) {
				s += iterator.next().toString();
				if (iterator.hasNext()) {
					s += ", "; 
				}
			}
		}
		ArrayList<String> result = new ArrayList<String>();
		result.add(s);
		return result;
	}
}
