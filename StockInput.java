package main.util;
import javax.swing.JOptionPane;
import java.util.Scanner;


import stocks.Product;
import stocks.Store;

/**
 * 
 * Used to contain all the command-line input methods for constructing {@link Product} and {@link Store}.
 *
 */
public class StockInput
{

	/**
	 * Constructs a product using a command-line input stream.
	 * 
	 * @param sc
	 *            The active input stream to be used in constructing the Product object, expected to be command-line.
	 * @return A {@link Product} constructed with the data provided by input from sc parameter.
	 */
	public static Product doNewProductInput(Scanner sc)
	{
		//String name = "";
		//System.out.print("Enter product name: ");
		String name = JOptionPane.showInputDialog("Enter product name: ");
		while (name.contains("|")) { // values cannot contain the | character because that is used in file formatting while ((name = sc.nextLine().trim()).equals("") && !name.contains("|"))
			//System.out.print("Product name may not be empty and cannot contain the character '|'.\nPlease enter a valid product name:");
			JOptionPane.showMessageDialog(null, "Product name may not be empty and cannot contain the character '|'.\nPlease enter a valid product name:");
			 name = JOptionPane.showInputDialog("Enter product name: ");
		}
		//int amt = InputHelper.getIntegerInput(sc, new FilterCheck(1), "Enter product amount: ", "The product amount must be a number and greater than zero. Please enter a valid product amount: ");
		String tempAmt = JOptionPane.showInputDialog("Enter product amount: ");
		int amt= Integer.parseInt(tempAmt);
		while(amt<1) {
			JOptionPane.showMessageDialog(null, "The product amount must be a number and greater than zero.");
			tempAmt = JOptionPane.showInputDialog("Enter product amount: ");
			amt= Integer.parseInt(tempAmt);
		}
		return new Product(name, amt);
	}

	/**
	 * Constructs a store using a command-line input stream.
	 * 
	 * @param sc
	 *            The active input stream to be used in constructing the Store object, expected to be command-line.
	 * @return A {@link Store} constructed with the data provided by input from sc parameter.
	 */
	public static Store doNewStoreInput(Scanner sc)
	{
		//String name = "";
		//System.out.print("Enter store name: ");
		String name = JOptionPane.showInputDialog("Enter store name: ");
		while (name.contains("|")) { // values cannot contain the | character because that is used in file formatting

			JOptionPane.showMessageDialog(null, "Store name may not be empty and cannot contain the character '|'.\nPlease enter a valid Store name:");
			 name = JOptionPane.showInputDialog("Enter store name: ");
		}
		
		//String addr = "";
		//System.out.print("Enter store address: ");
		String addr = JOptionPane.showInputDialog("Enter store address: ");
		while (addr.contains("|")) {// values cannot contain the | character because that is used in file formatting
			//System.out.print("Store address may not be empty and cannot contain the character '|'.\nPlease enter a valid store address:");
			JOptionPane.showMessageDialog(null, "Store address may not be empty and cannot contain the character '|'.\nPlease enter a valid Store name:");
			 name = JOptionPane.showInputDialog("Enter store address: ");
		}
		return new Store(name, addr);
	}
}
