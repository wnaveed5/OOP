package reports;

import java.util.ArrayList;
import java.util.List;

import stocks.Product;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.Transaction;
import transactions.TransactionsManager;

public class AllTransactionForProductReport implements Report
{
	private char transactionType;
	private Product product;
	private List<Transaction> transactions;

	public AllTransactionForProductReport(char type, Product p, TransactionsManager tm)
	{
		transactionType = type;
		product = p;
		transactions = new ArrayList<>();

		if (transactionType == 'I')
		{
			for (IncomingTransaction it : tm.getIncomingTransactions())
			{
				if (it.getNumProductInTransaction(product) != -1)
				{
					transactions.add(it);
				}
			}
		} else
		{
			for (OutgoingTransaction ot : tm.getOutgoingTransactions())
			{
				if (ot.getNumProductInTransaction(product) != -1)
				{
					transactions.add(ot);
				}
			}
		}
	}

	@Override
	public void printReport()
	{
		System.out.println("\nAll " + (transactionType == 'I' ? "Incoming" : "Outgoing") + " Transactions for Product: " + product.getName());
		System.out.println("--------------------------------------------------");
		for (Transaction t : transactions)
		{
			System.out.println(formatTransaction(t));
		}
		System.out.println();
	}

	private String formatTransaction(Transaction t)
	{
		String format = "";
		if (t instanceof IncomingTransaction)
		{
			format = "ID: " + ((IncomingTransaction)t).getID() + " ";
		} else
		{
			format = "ID: " + ((OutgoingTransaction)t).getID() + " ";
		}
		format += "Store: " + t.getTransactionStoreID() + " Amount: " + t.getNumProductInTransaction(product);
		return format;
	}
}
