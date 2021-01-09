package hkastr5.transactions;

/**
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen Transaction implementerar tidsstämpling av transaktioner   

 * 
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
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
		return df.format(date) + " " +
				String.format("%.2f", amount) + " kr" +
		        " Saldo: "  + String.format("%.2f", balance) + " kr";
	}
}
