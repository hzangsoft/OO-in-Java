package hkastr5.gui;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 4
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen BankGUI skapar användargränssnittet.
 * 
 */

import javax.swing.JFrame;

import hkastr5.logic.*;

public class BankGUI {


	private JFrame frame;

	/**
	 * Konstruktor för klassen BankGUI.
	 * 
	 * @param createTestData
	 * 			Anger om de testdata som finns i klassen ska användas.
	 */

	public BankGUI(boolean createTestData) {
		BankLogic bank = new BankLogic(createTestData);
		frame = new BankFrame(bank);
		frame.setTitle("Minibanken");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}