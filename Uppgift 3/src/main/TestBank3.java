package main;
/**
 * D0018D, Lab 3: Checks the classes Account, Customer and BankLogic 
 * This class can be updated during the course.
 * Last changes:  2015-01-19
 * @author Susanne Fahlman, susanne.fahlman@ltu.se       
 */

import java.io.*;

import gui.BankGui;
import logic.BankLogic;

public class TestBank3
{	
	public static void main(String[] args) throws FileNotFoundException
	{	
		BankGui bankGui = new BankGui(new BankLogic());
	}
}
