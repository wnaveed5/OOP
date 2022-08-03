package main.util;

import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import reports.AllItemsEnteredReport;
import reports.AllTransactionForProductReport;
import reports.AvailableItemsReport;
import reports.HighVolumeReport;
import reports.ProductByStoreReport;
import reports.TransactionsByMonthReport;
import stocks.Product;
import stocks.StockManager;
import transactions.TransactionsManager;

/**
 * Helper class containing methods related to input for the reporting section of the program
 */
public class ReportInput
{
	public static void getReportDetails(Scanner sc, StockManager systemInventory, TransactionsManager transactionsManager)
	{
		String menu = "";//
		String[] choices = {"All Products Report", "Available Products Report", "Store Products Report(all stores or one)",
							"All Transactions Report (all or monthly)","Product Transactions Report",
							"High Volume Products Report (incoming or outgoing)", "Return to Main Menu"};
		
		while (!menu.equals("Return to Main Menu"))
		{
			//printReportSubmenu();
			menu = (String) JOptionPane.showInputDialog(null, "<html>Inventory Management Report Submenu<br><br></html>", "Submenu",JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

			if (menu == null)//
				break;
			
			switch(menu)
			{

				case "All Products Report":
					new AllItemsEnteredReport(systemInventory).printReport();
					break;
				//##########################################################################################################
				case "Available Products Report":
					new AvailableItemsReport(systemInventory).printReport();
					break; 
				//##########################################################################################################
				case "Store Products Report(all stores or one)":
					FilterCheck filter = new FilterCheck(-1)// -1 will be the value returned if the user selects all stores
					{
						@Override
						public boolean isValid(int i)
						{
							return i == this.getLowerBound() || systemInventory.findStoreByID(i) != null;
						}
					};
					int store = InputHelper.allOrNum(sc, filter, "Enter a store ID to report on ", "Invalid input ");// returns a valid store id to filter by, or -1 which means all stores
	
					new ProductByStoreReport(store, transactionsManager).printReport();
					break;
				//##########################################################################################################
				case "All Transactions Report (all or monthly)":
					System.out.print("Incoming or outgoing transactions? (i for incoming, o for outgoing): ");
					String incomingOrOutgoing = sc.nextLine();
					while (!incomingOrOutgoing.equals("i") && !incomingOrOutgoing.equals("o"))
					{
						System.out.print("Invalid input. (i for incoming, o for outgoing): ");
						incomingOrOutgoing = sc.nextLine();
					}
					// input must be 'i' or 'o' to exit loop.
	
					FilterCheck filter2 = new FilterCheck(0)// 0 will be the value returned if the user selects all months, 1-12 represent months
					{
						@Override
						public boolean isValid(int i)
						{
							return super.isValid(i) && i <= 12;
						}
					};
					int month = InputHelper.allOrNum(sc, filter2, "Enter the number representation of a month, 1=January, 12=December ", "Invalid input ");// returns a valid store id to filter by, or -1 which means all stores
	
					// subtract 1 from month so that it's on the same range as Date object months.
					new TransactionsByMonthReport(incomingOrOutgoing.charAt(0), month - 1, transactionsManager).printReport();
					break;
				//##########################################################################################################
				case "Product Transactions Report":
					System.out.print("Incoming or outgoing transactions? (i for incoming, o for outgoing): ");
					String incomingOrOutgoing2 = sc.nextLine();
					while (!incomingOrOutgoing2.equals("i") && !incomingOrOutgoing2.equals("o"))
					{
						System.out.print("Invalid input. (i for incoming, o for outgoing): ");
						incomingOrOutgoing2 = sc.nextLine();
					}
					// input must be 'i' or 'o' to exit loop.
	
					FilterCheck modifiedFilter = new FilterCheck(0)
					{
						public boolean isValid(int i)
						{
							return systemInventory.findProductByID(i) != null;
						}
					};
	
					int validID = InputHelper.getIntegerInput(sc, modifiedFilter, "Select a product: ", "Invalid selection, select a valid product: ");
					Product p = systemInventory.findProductByID(validID);
	
					new AllTransactionForProductReport(incomingOrOutgoing2.charAt(0), p, transactionsManager).printReport();
					break;
				//##########################################################################################################
				case "High Volume Products Report (incoming or outgoing)":
					System.out.print("Incoming or outgoing transactions? (i for incoming, o for outgoing): ");
					String incomingOrOutgoing3 = sc.nextLine();
					while (!incomingOrOutgoing3.equals("i") && !incomingOrOutgoing3.equals("o"))
					{
						System.out.print("Invalid input. (i for incoming, o for outgoing): ");
						incomingOrOutgoing3 = sc.nextLine();
					}
					// input must be 'i' or 'o' to exit loop.
					new HighVolumeReport(incomingOrOutgoing3.charAt(0), transactionsManager).printReport();
					break;
				//##########################################################################################################
				case "Return to Main Menu":
					break;
				//##########################################################################################################
				default:
					System.out.println("Invalid input.");
					break;

			}
		}
		
		System.out.println("Returning to main menu...");
	}
}

