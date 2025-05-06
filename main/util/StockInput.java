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
		String name = null;
		do {
			name = JOptionPane.showInputDialog("Enter product name: ");
			if (name == null) {
				// User clicked Cancel
				return null;
			}
			if (name.trim().isEmpty() || name.contains("|")) {
				JOptionPane.showMessageDialog(null, "Product name may not be empty and cannot contain the character '|'.\nPlease enter a valid product name:");
				name = null;
			}
		} while (name == null);

		int amt = -1;
		do {
			String tempAmt = JOptionPane.showInputDialog("Enter product amount: ");
			if (tempAmt == null) {
				// User clicked Cancel
				return null;
			}
			try {
				amt = Integer.parseInt(tempAmt);
				if (amt < 1) {
					JOptionPane.showMessageDialog(null, "The product amount must be a number and greater than zero.");
					amt = -1;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a valid number.");
				amt = -1;
			}
		} while (amt < 1);

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
		String name = null;
		do {
			name = JOptionPane.showInputDialog("Enter store name: ");
			if (name == null) {
				// User clicked Cancel
				return null;
			}
			if (name.trim().isEmpty() || name.contains("|")) {
				JOptionPane.showMessageDialog(null, "Store name may not be empty and cannot contain the character '|'.\nPlease enter a valid store name:");
				name = null;
			}
		} while (name == null);

		String addr = null;
		do {
			addr = JOptionPane.showInputDialog("Enter store address: ");
			if (addr == null) {
				// User clicked Cancel
				return null;
			}
			if (addr.trim().isEmpty() || addr.contains("|")) {
				JOptionPane.showMessageDialog(null, "Store address may not be empty and cannot contain the character '|'.\nPlease enter a valid store address:");
				addr = null;
			}
		} while (addr == null);

		return new Store(name, addr);
	}
}
