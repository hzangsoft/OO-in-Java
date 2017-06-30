package main;
/**
 * D0018D, Lab 2: Checks the classes Account, Customer and BankLogic 
 * This class can be updated during the course.
 * Last changes:  2015-01-19
 * @author Susanne Fahlman, susanne.fahlman@ltu.se       
 */

import java.io.*;
import java.util.ArrayList;

import logic.BankLogic;

public class TestBank2
{
	private BankLogic bank = new BankLogic();
	
	//-----------------------------------------------------------------------------------
	// Runs the tests
	//-----------------------------------------------------------------------------------
	public void test() throws FileNotFoundException
	{
		String 	customerName;
		Long 	personalNumber;
		int 	accountNumber;
		double 	amount;
		
		// Sends error messages in an text file called err.txt instead of to the console
		// err.txt should be empty if everything is implemented correctly
		File file = new File("err.txt");
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setErr(ps);
		
		System.out.println("Testing begins, check the printout below but also check that the file err.txt");
		
		// Create customers
		customerName = "Karl Karlsson";
		personalNumber = 8505221898L;
		if(!testingCreateCustomer(customerName, personalNumber))
			System.err.println("Error: createCustomer(" + customerName +","+ personalNumber +")");
		
		customerName = "Donald Duck";
		personalNumber = 8505221898L;
		if(testingCreateCustomer(customerName, personalNumber)) // Should not work, personal number should be unique
			System.err.println("Error: createCustomer(" + customerName +","+ personalNumber +")");

		customerName = "Pelle Persson";
		personalNumber = 6911258876L;
		if(!testingCreateCustomer(customerName, personalNumber))
			System.err.println("Error: createCustomer(" + customerName +","+ personalNumber +")");
			
		customerName = "Lotta Larsson";
		personalNumber = 7505121231L;
		if(!testingCreateCustomer(customerName, personalNumber))
			System.err.println("Error: createCustomer(" + customerName +","+ personalNumber +")");
		
		// Change a customers name
		customerName = "Kalle Karlsson";
		personalNumber = 8505221898L;
		if(!testingChangeName(customerName, personalNumber))
			System.err.println("Error: changeCustomerName(" + customerName +","+ personalNumber +")");

		// Creates accounts
		personalNumber = 8505221898L;
		if(1001 != testingCreateCreditAccount(personalNumber))
			System.err.println("Error: createCreditAccount(" + personalNumber +")");
		personalNumber = 11L;
		if(-1 != testingCreateCreditAccount(personalNumber))
			System.err.println("Error: createCreditAccount(" + personalNumber +")");
		personalNumber = 6911258876L;
		if(1002 != testingCreateCreditAccount(personalNumber))
			System.err.println("Error: createCreditAccount(" + personalNumber +")");
		personalNumber = 8505221898L;
		if(1003 != testingCreateSavingsAccount(personalNumber))
			System.err.println("Error: createSavingsAccount(" + personalNumber +")");
		personalNumber = 7505121231L;
		if(1004 != testingCreateSavingsAccount(personalNumber))
			System.err.println("Error: createSavingsAccount(" + personalNumber +")");
		
		testingGetTransactions(8505221898L, 1002);
		System.out.println("# null");	
		
		// Get information about the customer including accounts
		System.out.println();
		testingGetCustomer(8505221898L);
		System.out.print("# [Kalle Karlsson 8505221898, ");
		System.out.print("1001 0.0 kr Kreditkonto 0.5 %, ");
		System.out.println("1003 0.0 kr Sparkonto 1.0 %]");
		System.out.println();

		// Get information about the customer including accounts
		testingGetCustomer(6911258876L);
		System.out.print("# [Pelle Persson 6911258876, ");
		System.out.println("1002 0.0 kr Kreditkonto 0.5 %]");
		System.out.println();

		// Get information about the customer including accounts
		testingGetCustomer(7505121231L);
		System.out.print("# [Lotta Larsson 7505121231, ");
		System.out.println("1004 0.0 kr Sparkonto 1.0 %]");
		System.out.println();
		
		personalNumber 	= 8505221898L;
		accountNumber  	= 1001;
		amount 			= 500;		
		if(!testingWithdraw(personalNumber, accountNumber, amount))	
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");

		personalNumber 	= 8505221898L;
		accountNumber  	= 1001;
		amount 			= 4000;
		if(!testingWithdraw(personalNumber, accountNumber, amount))
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");	
		
		personalNumber 	= 8505221898L;
		accountNumber  	= 1001;
		amount 			= 501;
		if(testingWithdraw(personalNumber, accountNumber, amount))	// Should not work, not enough money
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");

		personalNumber 	= 8505221898L;
		accountNumber  	= 1003;
		amount 			= 500;
		if(testingWithdraw(personalNumber, accountNumber, amount)) // Should not work, not enough money
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");

		personalNumber 	= 8505221898L;
		accountNumber  	= 1003;
		amount 			= 500;
		if(!testingDeposit(personalNumber, accountNumber, amount))	
			System.err.println("Error: deposit("+personalNumber + "," + accountNumber + "," + amount+")");
				
		// Get information about the customer including accounts
		System.out.println();
		testingGetCustomer(8505221898L);
		System.out.print("# [Kalle Karlsson 8505221898, ");
		System.out.print("1001 -4500.0 kr Kreditkonto 7.0 %, ");
		System.out.println("1003 500.0 kr Sparkonto 1.0 %]");
		System.out.println();
		
		testingGetTransactions(8505221898L, 1001);
		System.out.println("# [YYYY-MM-DD hh:mm:ss -500.0 -500.0, YYYY-MM-DD hh:mm:ss -4000.0 -4500.0]");
		System.out.println();
		testingGetTransactions(8505221898L, 1003);
		System.out.println("# [YYYY-MM-DD hh:mm:ss 500.0 500.0]");
		System.out.println();
		
		personalNumber 	= 6911258876L;
		accountNumber  	= 1002;
		amount 			= 500;
		if(!testingDeposit(personalNumber, accountNumber, amount))	
			System.err.println("Error: deposit("+personalNumber + "," + accountNumber + "," + amount+")");

		personalNumber 	= 6911258876L;
		accountNumber  	= 1002;
		amount 			= 1000;
		if(!testingDeposit(personalNumber, accountNumber, amount))	
			System.err.println("Error: deposit("+personalNumber + "," + accountNumber + "," + amount+")");

		personalNumber 	= 6911258876L;
		accountNumber  	= 1002;
		amount 			= 500;
		if(!testingWithdraw(personalNumber, accountNumber, amount))	
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");
		
		// Get information about the customer including accounts
		System.out.println();
		testingGetCustomer(6911258876L);
		System.out.print("# [Pelle Persson 6911258876, ");
		System.out.println("1002 1000.0 kr Kreditkonto 0.5 %]");
		System.out.println();
		
		testingGetTransactions(6911258876L, 1002);
		System.out.println("# [YYYY-MM-DD hh:mm:ss 500.0 500.0, YYYY-MM-DD hh:mm:ss 1000.0 1500.0, YYYY-MM-DD hh:mm:ss -500.0 1000.0]");
		System.out.println();
		
		personalNumber 	= 7505121231L;
		accountNumber  	= 1004;
		amount 			= 500;
		if(testingWithdraw(personalNumber, accountNumber, amount))	 // Should not work, not enough money
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");	
		
		personalNumber 	= 7505121231L;
		accountNumber  	= 1004;
		amount 			= 1000;
		if(!testingDeposit(personalNumber, accountNumber, amount))	
			System.err.println("Error: deposit("+personalNumber + "," + accountNumber + "," + amount+")");	
		
		personalNumber 	= 7505121231L;
		accountNumber  	= 1004;
		amount 			= 100;
		if(!testingWithdraw(personalNumber, accountNumber, amount))	 
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");	
		
		personalNumber 	= 7505121231L;
		accountNumber  	= 1004;
		amount 			= 890;
		if(testingWithdraw(personalNumber, accountNumber, amount))	 // Should not work, not enough money
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");	
		
		personalNumber 	= 7505121231L;
		accountNumber  	= 1004;
		amount 			= 100;
		if(!testingWithdraw(personalNumber, accountNumber, amount))	 
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");	

		// Get information about the customer including accounts
		System.out.println();
		testingGetCustomer(7505121231L);
		System.out.print("# [Lotta Larsson 7505121231, ");
		System.out.println("1004 798.0 kr Sparkonto 1.0 %]");
		System.out.println();
		
		testingGetTransactions(7505121231L, 1004);
		System.out.println("# [YYYY-MM-DD hh:mm:ss 1000.0 1000.0, YYYY-MM-DD hh:mm:ss -100.0 900.0, YYYY-MM-DD hh:mm:ss -102.0 798.0]");
		System.out.println();
		
		// Closes the account
		testingCloseAccount(8505221898L, 1001);	
		System.out.println("# 1001 -4500.0 kr Kreditkonto 7.0 % -315.0 kr");
		System.out.println();
		
		// Creates account
		personalNumber = 8505221898L;
		if(1005 != testingCreateCreditAccount(personalNumber))
			System.err.println("Error: createCreditAccount(" + personalNumber +")");
		
		personalNumber 	= 8505221898L;
		accountNumber  	= 1005;
		amount 			= 1000;
		if(!testingWithdraw(personalNumber, accountNumber, amount))
			System.err.println("Error: withdraw("+personalNumber + "," + accountNumber + "," + amount+")");	
				
		// Deletes the customer including accounts
		System.out.println();
		testingDeleteCustomer(8505221898L);
		System.out.print("# [Kalle Karlsson 8505221898, ");
		System.out.print("1003 500.0 kr Sparkonto 1.0 % 5.0 kr, ");
		System.out.println("1005 -1000.0 kr Kreditkonto 7.0 % -70.0 kr]");
		System.out.println();
		
		// Closes the account
		testingCloseAccount(7505121231L, 1004);	
		System.out.println("# 1004 798.0 kr Sparkonto 1.0 % 7.98 kr");
		System.out.println();
		
		// Get information about the account
		testingGetAccount(7505121231L, 1004); // Should not work
		System.out.println("# null");

		// Get information about the customer including accounts
		System.out.println();
		testingGetCustomer(7505121231L);
		System.out.println("# [Lotta Larsson 7505121231]");
		System.out.println();

		// Deletes the customer
		testingDeleteCustomer(6911258876L);
		System.out.print("# [Pelle Persson 6911258876, ");	
		System.out.println("1002 1000.0 kr Kreditkonto 0.5 % 5.0 kr]");	
		System.out.println();
		
		// Deletes the customer
		testingDeleteCustomer(7505121231L);
		System.out.print("# [Lotta Larsson 7505121231]");
		
		// Get all customers in the list and prints it
		System.out.println();
		testingGetAllCustomers();
		
		ps.close();
	}

	
	/**
	 * Prints the customer list
	 */
	private void testingGetAllCustomers()
	{
		System.out.println("getAllCustomers()");
		ArrayList<String> result = bank.getAllCustomers();
		System.out.println("  " + result);
	}
	
	/**
	 * Prints the customer
	 * @param pNr - Personal number of customer that will be printed
	 */
	private void testingGetCustomer(long pNr)
	{
		System.out.println("getCustomer(" + pNr + ")");
		System.out.println("  " + bank.getCustomer(pNr));
	}

	/**
	 * Prints the account
	 * @param pNr - Personal number of customer that owns the account
	 * @param accountId - Id of the account that will be printed
	 */
	private void testingGetAccount(long pNr, int accountId)
	{
		System.out.println("getAccount(" + pNr + "," + accountId + ")");
		System.out.println("  " + bank.getAccount(pNr, accountId));
	}

	/**
	 * Creates a cusomer if no customer exists wit hte same personal number
	 * @param name - Name of the customer
	 * @param pNr - Personal number of customer
	 * @return true if customer is created otherwise false
	 */
	private boolean testingCreateCustomer(String name, long pNr)
	{
		System.out.println("createCustomer(" + name + "," + pNr + ")");
		return bank.createCustomer(name, pNr); 
	}

	/**
	 * Changes the name of the customer
	 * @param name - The new name
	 * @param pNr - Personal number of customer that is getting a new name
	 * @return true if customer name is changed otherwise false
	 */
	private boolean testingChangeName(String name, long pNr)
	{
		System.out.println("changeCustomerName(" + name + "," + pNr + ")");
		return bank.changeCustomerName(name, pNr);
	}

	/**
	 * Creates a account
	 * @param pNr - Personal number of customer that is getting a new account
	 * @return true if account is created otherwise false
	 */
	private int testingCreateSavingsAccount(long pNr)
	{
		System.out.println("createSavingsAccount(" + pNr + ")");
		return bank.createSavingsAccount(pNr);
	}
	
	/**
	 * Creates a account
	 * @param pNr - Personal number of customer that is getting a new account
	 * @return true if account is created otherwise false
	 */
	private int testingCreateCreditAccount(long pNr)
	{
		System.out.println("createCreditAccount(" + pNr + ")");
		return bank.createCreditAccount(pNr);
	}

	/**
	 * Deposit the amount
	 * @param pNr - The personal number of the customer that owns the account
	 * @param accountId -  The id of the account
	 * @param amount - The amount
	 * @return true if amount is deposited otherwise false
	 */
	private boolean testingDeposit(long pNr, int accountId, double amount)
	{
		System.out.println("deposit(" + pNr + "," + accountId + "," + amount + ")");
		return bank.deposit(pNr, accountId, amount);						
	}
	
	/**
	 * Withdraws the amount
	 * @param pNr - The personal number of the customer that owns the account
	 * @param accountId -  The id of the account
	 * @param amount - The amount
	 * @return true if amount is withdrawn otherwise false
	 */
	private boolean testingWithdraw(long pNr, int accountId, double amount)
	{
		System.out.println("withdraw(" + pNr + "," + accountId + "," + amount + ")");
		return bank.withdraw(pNr, accountId, amount);						
	}
	
	/**
	 * The account is deleted and result is printed
	 * @param pNr - The personal number of the customer that is to be deleted
	 * @param accountId - The id of the account that is closed
	 */
	private void testingCloseAccount(long pNr, int accountId)
	{
		System.out.println("closeAccount(" + pNr + "," + accountId + ")");
		System.out.println("  " + bank.closeAccount(pNr, accountId));							
	}
		
	/**
	 * The customer is deleted and result is printed
	 * @param pNr - The personal number of the customer that is to be deleted
	 */
	private void testingDeleteCustomer(long pNr)
	{
		System.out.println("deleteCustomer(" + pNr + ")");
		System.out.println("  " + bank.deleteCustomer(pNr));				
	}
	
	private void testingGetTransactions(long pNr, int accountId)
	{
		System.out.println("getTransactions(" + pNr + ", " + accountId + ")");
		System.out.println("  " + bank.getTransactions(pNr, accountId));							
	}

	public static void main(String[] args) throws FileNotFoundException
	{		
		TestBank2 bankMenu = new TestBank2();
		bankMenu.test();	
	}
}
