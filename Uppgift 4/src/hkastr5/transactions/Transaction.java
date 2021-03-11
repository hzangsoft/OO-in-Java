package hkastr5.transactions;

import java.io.Serializable;
/**
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 4
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen Transaction implementerar tidsstämpling av transaktioner   

 * 
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7723353703900936480L;
	private Date date; // Tidstämpeln för transaktion.
	private double amount; // Summan för transaktionen
	private double balance; // Saldot efter transaktionen.

	/**
	 * Konstruktor
	 * 
	 * @param amount
	 *            Transaktionsbeloppet.
	 * @param balance
	 *            Behållningen på kontot.
	 */
	public Transaction(double amount, double balance) {
		this.amount = amount;
		this.balance = balance;
		date = new Date();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Transaktionsbelopp och saldo skrivs ut med två decimaler.
	 */
	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return String.format("%19s %10.2f kr%10.2f kr", df.format(date), amount, balance);
	}
}