package hkastr5.accounts;

import java.util.ArrayList;

/**
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 4
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Enum Accounts innehåller en lista med de olika kontotyper som banken hanterar.

 * 
 */
public enum AccountTypes {
	// Ett värde för varje kontotyp tillsammans med dess strängrepresentation
	SAVINGSACCOUNT("Sparkonto"), CREDITACCOUNT("Kreditkonto"), UNKNOWNACCOUNTTYPE("Okänd kontotype");

	// Strängrepresentationen.
	private String accountString;

	/**
	 * Initiera enumvärdet med motsvarande textsträng.
	 * @param accountString Strängen.
	 */
	private AccountTypes(String accountString)
	{
		this.accountString = accountString;
	}

	/**
	 * Hitta det enumvärde som motsvarar en sträng.
	 * @param accountString Sträng som ska kontrolleras
	 * @return Kontotypen som motsvarar strängen om den finns
	 * @return UNKNOWNACCOUNTTYPE om det är en okänd kontotyp.
	 */
	public static AccountTypes getAccountType(String accountString)
	{
		AccountTypes accountType = AccountTypes.UNKNOWNACCOUNTTYPE;

		for (AccountTypes a : AccountTypes.values()) {
			if(a.toString().equalsIgnoreCase(accountString)) {
				accountType= a;
			}
		}        
		return accountType;
	}
	/**
	 * Returnera en ArrayList med strängrepresentationer av alla kontotyper förutom UNKKNOWNACCOUNTTYPE.
	 * @return Listan med strängrepresentationer.
	 */
	public static ArrayList<String> allAccountTypes() 
	{
		ArrayList<String> result = new ArrayList<String>();
		for (AccountTypes a : AccountTypes.values()) {
			if (a != AccountTypes.UNKNOWNACCOUNTTYPE) {
				result.add(a.accountString);
			}
		}
		return result;
	}

	/**
	 * @return Strängrepresentationen av kontotypen.
	 */
	public String toString()
	{
		return accountString;
	}
}