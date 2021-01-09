package hkastr5.gui;
/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen BankGUI skapar användargränssnittet.
 * 
 */

import javax.swing.JFrame;

import hkastr5.logic.*;

public class BankGui {
	

	private JFrame frame;
	
	public BankGui(BankLogic bank) {
		frame = new BankFrame(bank);
		
		frame.setTitle("Minibanken");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
