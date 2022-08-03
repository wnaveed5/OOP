package main;

import java.util.Scanner;
import java.util.Collections;

import java.util.*;
import javax.swing.JOptionPane;

import main.util.ReportInput;
import main.util.StockInput;
import main.util.TransactionInput;
import stocks.StockManager;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.TransactionsManager;

/**
 * 
 */
public class Main
{
	/**
	 * Manages the data for stores and products in the system.
	 */
	public StockManager systemInventory;

	/**
	 * Manages transactions in the system.
	 */
	public TransactionsManager transactionsManager;

	private Main()
	{
		systemInventory = new StockManager();
		transactionsManager = new TransactionsManager(systemInventory);
	}

	public static void main(String args[]) throws Exception
	{
		Main main = new Main();
		main.runInputLoop();
	}

	/**
	 * Runs the basic program loop for interacting with the system.
	 */
	public void runInputLoop()
	{
		Scanner sc = new Scanner(System.in);
		
		String menu = "";
		String[] choices = {"Add Product", "Add Store", "Perform Incoming Transaction", "Perform Outgoing Transaction","Generate Reports", "Exit Program"};
		
		while (!menu.equals("Exit Program"))
		{
			//printMenu();
			menu = (String) JOptionPane.showInputDialog(null, "<html>Inventory Management System Menu<br><br></html>", "Main Menu",JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			
			if (menu == null)
				break;
			
			switch (menu)
			{
				case "Add Product":
					systemInventory.addProduct(StockInput.doNewProductInput(sc));
					break;
				
				case "Add Store":
					systemInventory.addStore(StockInput.doNewStoreInput(sc));
					break;
				
				case "Perform Incoming Transaction":
					IncomingTransaction it = TransactionInput.doNewIncomingTransaction(sc, systemInventory);
					transactionsManager.addTransaction(it);
					systemInventory.saveUpdatedStock();
					break;
				
				case "Perform Outgoing Transaction":
					OutgoingTransaction ot = TransactionInput.doNewOutgoingTransaction(sc, systemInventory);
					transactionsManager.addTransaction(ot);
					systemInventory.saveUpdatedStock();
					break;
				
				case "Generate Reports":
					ReportInput.getReportDetails(sc, systemInventory, transactionsManager);
					break;
				
				case "Exit Program":
					break;
				
				default:
					System.out.println("Invalid input.");
					break;
			}
		}
		
		System.out.println("Exiting program...");
		System.exit(0);
	}

	/**
	 * Prints menu of possible actions, convenience method used by {@link #runInputLoop()}.
	 */
	
	/*
	private void printMenu()
	{
		System.out.println("\n\nInventory Management System Menu");
		System.out.println("--------------------------------");
		System.out.println("p: Add Product");
		System.out.println("s: Add Store");
		System.out.println("i: Perform Incoming Transaction");
		System.out.println("o: Perform Outgoing Transaction");
		System.out.println("r: Generate Reports");
		System.out.println("x: Exit Program");

		System.out.println();
	}*/
}