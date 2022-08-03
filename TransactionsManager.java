package transactions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import main.util.FileManager;
import stocks.StockManager;

/**
 * Manages all transactions in the system.
 */
public class TransactionsManager
{
	private FileManager fm;
	private List<IncomingTransaction> incomingTransactions;
	private List<OutgoingTransaction> outgoingTransactions;

	/**
	 * Default constructor
	 */
	public TransactionsManager(StockManager sm)
	{
		fm = new FileManager();
		incomingTransactions = fm.readIncomingTransactions(sm);
		outgoingTransactions = fm.readOutgoingTransactions(sm);
	}

	/**
	 * Applies the transaction to the system and records it in the manager
	 * 
	 * @param tr
	 *            the transaction to add to the manager.
	 */
	public void addTransaction(IncomingTransaction tr)
	{
		UpdateIncomingThread myThing = new UpdateIncomingThread(tr);
		Thread myThread = new Thread(myThing);
		myThread.start();
		incomingTransactions.add((IncomingTransaction) tr);
		fm.writeIncomingTransactions(incomingTransactions);
	}

	/**
	 * Applies the transaction to the system and records it in the manager
	 * 
	 * @param tr
	 *            the transaction to add to the manager.
	 */
	public void addTransaction(OutgoingTransaction tr)
	{
		UpdateOutgoingThread myThing = new UpdateOutgoingThread(tr.getStore());
		myThing.setOutgoingTransaction(tr);
		Thread myThread = new Thread(myThing);
		myThread.start();
		outgoingTransactions.add((OutgoingTransaction) tr);
		fm.writeOutgoingTransactions(outgoingTransactions);
	}

	/**
	 * Applies the transaction to the system and records it in the manager
	 * 
	 * @param tr
	 *            the transaction to add to the manager.
	 */
	public void addTransaction(Transaction tr)
	{
		if (tr instanceof IncomingTransaction) {
			addTransaction((IncomingTransaction) tr);
		}
			
		else if (tr instanceof OutgoingTransaction)
			addTransaction((OutgoingTransaction) tr);
	}

	/**
	 * lists all outgoing transactions from this session.
	 * 
	 * @return a list of all outgoing transactions performed this session.
	 */
	public List<OutgoingTransaction> getOutgoingTransactions()
	{
		return outgoingTransactions;
	}

	/**
	 * lists all incoming transactions from this session.
	 * 
	 * @return a list of all incoming transactions performed this session.
	 */
	public List<IncomingTransaction> getIncomingTransactions()
	{
		return incomingTransactions;
	}
}
