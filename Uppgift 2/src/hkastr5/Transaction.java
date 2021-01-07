package hkastr5;

/**
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 1
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen  
 * 
 * 
 * 
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	private Date date;
	private double amount;
	private double balance;
	
	public Transaction(double amount, double balance) {
		this.amount = amount;
		this.balance = balance;
		date = new Date();
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date) + " " +
				String.format("%.2f", amount) + " kr" +
		        " Saldo: "  + String.format("%.1f", balance) + " kr";
	}
}
