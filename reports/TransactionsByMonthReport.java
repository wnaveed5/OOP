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
	private char transactionType;
	private int month;
	private List<Transaction> transactions;

	public TransactionsByMonthReport(char type, int month, TransactionsManager tm)
	{
		this.transactionType = type;
		this.month = month;
		transactions = new ArrayList<>();

		if (transactionType == 'I')
		{
			for (IncomingTransaction it : tm.getIncomingTransactions())
			{
				if (month == -1 || it.getMonth() == month)
				{
					transactions.add(it);
				}
			}
		} else
		{
			for (OutgoingTransaction ot : tm.getOutgoingTransactions())
			{
				if (month == -1 || ot.getMonth() == month)
				{
					transactions.add(ot);
				}
			}
		}
	}

	@Override
	public void printReport()
	{
		System.out.println("\nAll " + (transactionType == 'I' ? "Incoming" : "Outgoing") + " Transactions" + 
		                  (month == -1 ? "" : " for Month: " + getMonthName(month)));
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
		format += "Store: " + t.getTransactionStoreID() + " Products: ";
		for (Product p : t.getProductListForTransaction())
		{
			format += p.getName() + "(" + p.getID() + "): " + t.getNumProductInTransaction(p) + ", ";
		}
		return format.substring(0, format.length() - 2);
	}

	private String getMonthName(int i)
	{
		String months[] = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		return months[i];
	}

}
