package reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.Product;
import transactions.OutgoingTransaction;
import transactions.TransactionsManager;

public class ProductByStoreReport implements Report
{

	private int store;

	private Map<Product, Integer> productList;

	public ProductByStoreReport(int s, TransactionsManager transactionsManager)
	{
		store = s;
		productList = new HashMap<Product, Integer>();
		collectProductsSentToStore(transactionsManager);
	}

	/**
	 * 
	 */
	public void printReport()
	{
		System.out.println("Store Products Report For " + (store == -1 ? "All Stores" : "Store " + store) +
				":\nProducts appear in the format name(id): amount");

		for (Product p : productList.keySet())
			System.out.println(p.getName() + "(" + p.getID() + "): " + productList.get(p));
	}

	/**
	 * 
	 */
	private void collectProductsSentToStore(TransactionsManager transactionsManager)
	{
		List<OutgoingTransaction> outgoing = transactionsManager.getOutgoingTransactions();
		for (OutgoingTransaction transaction: outgoing)
			if (store == -1 || (store != -1 && transaction.getTransactionStoreID() == store))
			{
				Product[] productsInTransaction = transaction.getProductListForTransaction();
				for (Product newProduct : productsInTransaction)
				{
					if (productList.get(newProduct) != null)
						productList.put(newProduct, productList.get(newProduct) + transaction.getNumProductInTransaction(newProduct));
					else
						productList.put(newProduct, transaction.getNumProductInTransaction(newProduct));
				}
			}
	}
}