package hkastr5.main;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen TestBank3 är huvudprogrammet för applikationen Minibanken.
 * 
 */

import java.io.*;

import hkastr5.gui.BankGUI;

public class TestBank3
{	
	public static void main(String[] args) throws FileNotFoundException
	{	
		// Kontrollera första argumentet för att avgöra om testdata ska genereras eller om vi ska starta med en tom bank.
		boolean createTestData = args[0].equals("-testdata");
		BankGUI bankGui = new BankGUI(createTestData);
	}
}
