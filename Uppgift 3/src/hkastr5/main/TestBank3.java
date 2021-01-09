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

import hkastr5.gui.BankGui;
import hkastr5.logic.*;

public class TestBank3
{	
	public static void main(String[] args) throws FileNotFoundException
	{	
		BankGui bankGui = new BankGui(new BankLogic());
	}
}
