package gui;

import javax.swing.JFrame;

import logic.BankLogic;

public class BankGui {
	

	private JFrame frame;
	
	public BankGui(BankLogic bank) {
		frame = new BankFrame(bank);
		
		frame.setTitle("Minibanken");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
