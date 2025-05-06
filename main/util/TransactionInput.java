package main.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Calendar;

import stocks.Product;
import stocks.StockManager;
import stocks.Store;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.Transaction;
import transactions.TransactionsManager;

/**
 * Used to contain all the command-line and file input methods for constructing {@link IncomingTransaction} and {@link OutgoingTransaction}.
 */
public class TransactionInput
{
	/**
	 * Creates an incoming transaction from input. Most of the functionality is in {@link #addProductsToTransaction(Scanner, Transaction, StockManager)}
	 * 
	 * @param sc
	 *            the object from which to get user input.
	 * @param inventory
	 *            current stock inventory for generating transaction.
	 * @return the generated IncomingTransaction object.
	 */
	public static IncomingTransaction doNewIncomingTransaction(Scanner sc, StockManager inventory)
	{
		System.out.println("\nNew Incoming Transaction");
		System.out.println("------------------------");

		// Get the store ID
		System.out.println("\nAvailable stores:");
		for (Store s : inventory.getStoreList()) {
			System.out.println(s.getID() + ": " + s.getName());
		}

		FilterCheck modifiedFilter = new FilterCheck(0) {
			@Override
			public boolean isValid(int i) {
				return super.isValid(i) && inventory.findStoreByID(i) != null;
			}
		};

		int storeId = InputHelper.getIntegerInput(sc, modifiedFilter, "Select a store: ", "Invalid selection, select a valid store from the list: ");
		
		// Create the transaction
		IncomingTransaction transaction = new IncomingTransaction(storeId, Calendar.getInstance().get(Calendar.MONTH));
		
		// Add products to the transaction
		return (IncomingTransaction) addProductsToTransaction(sc, transaction, inventory);
	}

	/**
	 * Creates an outgoing transaction from input. Most of the functionality is in {@link #addProductsToTransaction(Scanner, Transaction, StockManager)}
	 * 
	 * @param sc
	 *            the object from which to get user input.
	 * @param inventory
	 *            current stock inventory for generating transaction.
	 * @return the generated OutgoingTransaction object.
	 */
	public static OutgoingTransaction doNewOutgoingTransaction(Scanner sc, StockManager inventory)
	{
		System.out.println("\nNew Outgoing Transaction");
		System.out.println("------------------------");

		// Get the store
		System.out.println("\nAvailable stores:");
		for (Store s : inventory.getStoreList()) {
			System.out.println(s.getID() + ": " + s.getName());
		}

		FilterCheck modifiedFilter = new FilterCheck(0) {
			@Override
			public boolean isValid(int i) {
				return super.isValid(i) && inventory.findStoreByID(i) != null;
			}
		};

		int storeId = InputHelper.getIntegerInput(sc, modifiedFilter, "Select a store: ", "Invalid selection, select a valid store from the list: ");
		
		// Create the transaction
		OutgoingTransaction transaction = new OutgoingTransaction(storeId, Calendar.getInstance().get(Calendar.MONTH));
		
		// Add products to the transaction
		return (OutgoingTransaction) addProductsToTransaction(sc, transaction, inventory);
	}

	/**
	 * Prompts the user to add products to a provided transaction.
	 * 
	 * @param sc
	 *            used to get input from the user.
	 * @param transaction
	 *            the transaction to which products will be added.
	 * @param inventory
	 *            used to get instances of product to add to the transaction.
	 * @return the provided transaction, with the products added.
	 */
	private static Transaction addProductsToTransaction(Scanner sc, Transaction transaction, StockManager inventory)
	{
		String input = "";
		while (!input.equals("x")) {
			System.out.println("\nProducts in transaction:");
			for (Product p : transaction.getProductListForTransaction()) {
				System.out.println(p.getID() + ": " + p.getName() + " x" + transaction.getNumProductInTransaction(p));
			}

			System.out.println("\nAvailable products:");
			for (Product p : inventory.getProductList()) {
				System.out.println(p.getID() + ": " + p.getName());
			}

			FilterCheck modifiedFilter = new FilterCheck(0) {
				@Override
				public boolean isValid(int i) {
					return super.isValid(i) && inventory.findProductByID(i) != null;
				}
			};

			int validID = InputHelper.getIntegerInput(sc, modifiedFilter, "Select a product: ", "Invalid selection, select a valid product from the list: ");
			Product p = inventory.findProductByID(validID);

			int count = InputHelper.getIntegerInput(sc, new FilterCheck(1), "Enter product amount: ", "The product amount must be a number and greater than zero. Please enter a valid product amount: ");

			transaction.addProduct(p, count);

			System.out.println("\nAdd another product? (x to finish)");
			input = sc.nextLine();
		}

		return transaction;
	}

	/**
	 * Loops each line of the provided transaction file and processes the transactions. Each line must be of
	 * the format "storeID productID productCount productID productCount ...".
	 * StoreID must be a valid store ID for outgoing transactions, or the letter "i" for incoming transactions.
	 * ProductID must be a valid product ID and productCount must be the designated count for the preceding product ID.
	 * The number of productID/productCount pairs can be as many as needed, so long as they are always in pairs.
	 * <p>
	 * For example, the line "i 1 33 2 44"
	 * <p>
	 * Would result in an incoming transaction where the product with id 1 adds 33 to its stock and the product with
	 * id 2 adds 44 to its stock.
	 * <p>
	 * Similarly, "1 2 5 3 6"
	 * <p>
	 * Would result in an outgoing transaction to the store with id 1 in which product id 2 sends 5 stock and product id 3 sends 6 stock.
	 * 
	 * @param fileName
	 *            the name of the file to be processed
	 * @param inventory
	 *            the inventory to be used for getting product instances
	 * @param transactionManager
	 *            the transaction manager to which the generated transactions will be recorded.
	 */
	public static void processTransactionFile(String fileName, StockManager inventory, TransactionsManager transactionManager)
	{
		Scanner sc = null;

		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			sc = new Scanner(fis);
		} catch (FileNotFoundException e)
		{
			sc = null;
		}
		if (sc == null) // something went wrong with opening output file and creating PrintWriter
		{
			System.out.println("There was an error with the provided transaction input file. Please verify the file name and try again.");
			return; // cancel attempted file write
		}

		int fileLn = 0;
		while (sc.hasNext())
		{
			fileLn++;

			String input = sc.nextLine();
			String data[] = input.split(" ");
			/* If there isnt a type indicator and at least one product id-count pair, then the transaction is malformed.
			   Also the number of elements must be odd, 1 for the type indicator and 2*n for n products, where a product is
			   a pair of entrys in the form "ID count".
			 */
			if (data.length < 3 || data.length % 2 != 1)
			{
				System.out.println("Encountered malformed transaction on line " + fileLn + ". This line will be skipped. 1");
				continue;
			}

			Transaction transaction = null;
			if (data[0].trim().equals("i"))// incoming transaction type indicator
			{
				// Get the current month
				int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
				transaction = new IncomingTransaction(0, currentMonth); // Using 0 as default store ID for incoming transactions
			} else if (InputHelper.isNumber(data[0]))// outgoing transaction type indicator
			{
				int storeNumber = Integer.parseInt(data[0].trim());
				Store store = inventory.findStoreByID(storeNumber);
				if (store == null)// couldnt find store in list
				{
					System.out.println("Encountered malformed transaction on line " + fileLn + ". This line will be skipped. 2");
					continue;
				}
				// Get the current month
				int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
				transaction = new OutgoingTransaction(store.getID(), currentMonth);
			} else// not 'i' and not a number meaning it is malformed
			{
				System.out.println("Encountered malformed transaction on line " + fileLn + ". This line will be skipped. 3");
				continue;
			}
			// can assume transaction successfully setup and formatted correctly from here

			boolean productError = false;
			for (int i = 1; i < data.length; i += 2)
			{
				if (!InputHelper.isNumber(data[i]) || !InputHelper.isNumber(data[i + 1]))// stop if the product id or count arent numbers
				{
					productError = true;
					break;
				}

				int productID = Integer.parseInt(data[i].trim());
				Product product = inventory.findProductByID(productID);
				if (product == null)// stop if product id doesnt lead to real product
				{
					productError = true;
					break;
				}
				int productCount = Integer.parseInt(data[i + 1].trim());

				transaction.addProduct(product, productCount);// no errors, add product to transaction
			}
			if (productError)
			{
				System.out.println("Encountered malformed transaction on line " + fileLn + ". This line will be skipped. 5");
				continue;
			}
			transactionManager.addTransaction(transaction);
		}
	}

	public static Transaction parseTransactionFromString(String data, StockManager inventory) {
		String[] parts = data.split(",");
		if (parts.length < 2) {
			return null;
		}

		try {
			Transaction transaction = null;
			int storeId = Integer.parseInt(parts[0]);
			Store store = inventory.findStoreByID(storeId);
			if (store == null) {
				return null;
			}

			// Get the current month
			int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

			if (parts[1].equals("I")) {
				transaction = new IncomingTransaction(store.getID(), currentMonth);
			} else if (parts[1].equals("O")) {
				transaction = new OutgoingTransaction(store.getID(), currentMonth);
			} else {
				return null;
			}

			for (int i = 2; i < parts.length - 1; i += 2) {
				if (!InputHelper.isNumber(parts[i]) || !InputHelper.isNumber(parts[i + 1])) {
					return null;
				}

				int productID = Integer.parseInt(parts[i]);
				int count = Integer.parseInt(parts[i + 1]);

				Product product = inventory.findProductByID(productID);
				if (product == null) {
					return null;
				}

				transaction.addProduct(product, count);
			}

			return transaction;
		} catch (Exception e) {
			return null;
		}
	}
}
