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

	private char incomingOrOutgoing;
	private Product product;

	private List<Transaction> transactions;

	public AllTransactionForProductReport(char inOrOut, Product p, TransactionsManager tr)
	{
		incomingOrOutgoing = inOrOut;
		product = p;
		transactions = new ArrayList<Transaction>();

		collectTransactions(tr);
	}

	@Override
	public void printReport()
	{
		System.out.println("Transactions Report for " + product.getName() + ":\nProducts appear in the format: name(id): amount");
		for (Transaction t : transactions)
			System.out.println(formatTransaction(t));
	}

	private void collectTransactions(TransactionsManager transactionsManager)
	{
		if (incomingOrOutgoing == 'i')
		{
			List<IncomingTransaction> incoming = transactionsManager.getIncomingTransactions();
			for (IncomingTransaction it: incoming)
				if (it.getNumProductInTransaction(product) != -1)
					transactions.add(it);
		} else // incomingOrOutgoing must be 'o'
		{
			List<OutgoingTransaction> outgoing = transactionsManager.getOutgoingTransactions();
			for (OutgoingTransaction ot: outgoing)
				if (ot.getNumProductInTransaction(product) != -1)
					transactions.add(ot);
		}
	}

	private String formatTransaction(Transaction t)
	{
		String format = "ID: " + t.getID() + " ";
		if (t instanceof OutgoingTransaction)
			format += "Store: " + ((OutgoingTransaction) t).getTransactionStoreID() + " ";
		format += "Products: ";
		for (Product p : t.getProductListForTransaction())
			format += p.getName() + "(" + p.getID() + "): " + p.getCount() + ", ";
		return format.substring(0, format.length() - 2);
	}
}
