package reports;

import java.util.ArrayList;
import java.util.List;

import stocks.Product;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.Transaction;
import transactions.TransactionsManager;

public class TransactionsByMonthReport implements Report
{

	private char incomingOrOutgoing;
	private int month;

	private List<Transaction> transactions;

	public TransactionsByMonthReport(char inOrOut, int mon, TransactionsManager tr)
	{
		incomingOrOutgoing = inOrOut;
		month = mon;
		transactions = new ArrayList<Transaction>();

		collectTransactions(tr);
	}

	@Override
	public void printReport()
	{
		System.out.println("All Transactions Report" + (month != -1 ? " for " + getMonthName(month) : "") + ":\nProducts appear in the format: name(id): amount");
		for (Transaction t : transactions)
			System.out.println(formatTransaction(t));
	}

	private void collectTransactions(TransactionsManager transactionsManager)
	{
		if (incomingOrOutgoing == 'i')
		{
			List<IncomingTransaction> incoming = transactionsManager.getIncomingTransactions();
			for (IncomingTransaction it: incoming)
				if (month == -1 || it.getMonth() == month)// -1 means no filter, otherwise filter by month
					transactions.add(it);
		} else // incomingOrOutgoing must be 'o'
		{
			List<OutgoingTransaction> outgoing = transactionsManager.getOutgoingTransactions();
			for (OutgoingTransaction ot: outgoing)
				if (month == -1 || ot.getMonth() == month)// -1 means no filter, otherwise filter by month
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
			format += p.getName() + "(" + p.getID() + "): " + t.getNumProductInTransaction(p) + ", ";
		return format.substring(0, format.length() - 2);
	}

	private String getMonthName(int i)
	{
		String months[] = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		return months[i];
	}

}
