package reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.Product;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.TransactionsManager;

public class HighVolumeReport implements Report
{

	private char incomingOrOutgoing;
	private String output;

	public HighVolumeReport(char inOrOut, TransactionsManager t)
	{
		output = "";
		incomingOrOutgoing = inOrOut;
		collectProductVolumes(t);
	}

	@Override
	public void printReport()
	{
		System.out.println("High Volume " + (incomingOrOutgoing == 'i' ? " Incoming " : " Outgoing ") + "Items:\nProducts appear in the format: name(id): amount");
		System.out.println(output);
	}

	private void collectProductVolumes(TransactionsManager transactionsManager)
	{
		Map<Product, Integer> volumeReport = new HashMap<Product, Integer>();
		if (incomingOrOutgoing == 'i')
		{
			List<IncomingTransaction> incoming = transactionsManager.getIncomingTransactions();
			for (IncomingTransaction it: incoming)
				for (Product p : it.getProductListForTransaction())
				{
					int amt = it.getNumProductInTransaction(p);
					if (volumeReport.get(p) != null)
					{
						int volume = volumeReport.get(p);
						volumeReport.put(p, volume + amt);
					} else
						volumeReport.put(p, amt);
				}
		} else // incomingOrOutgoing must be 'o'
		{
			List<OutgoingTransaction> outgoing = transactionsManager.getOutgoingTransactions();
			for (OutgoingTransaction ot: outgoing)
				for (Product p : ot.getProductListForTransaction())
				{
					int amt = ot.getNumProductInTransaction(p);
					if (volumeReport.get(p) != null)
					{
						int volume = volumeReport.get(p);
						volumeReport.put(p, volume + amt);
					} else
						volumeReport.put(p, amt);
				}
		}

		while (!volumeReport.isEmpty())
		{
			Product highest = null;
			for (Product p : volumeReport.keySet())
				if (highest == null)
					highest = p;
				else if (volumeReport.get(p) > volumeReport.get(highest))
					highest = p;
			output += highest.getName() + "(" + highest.getID() + "): " + volumeReport.remove(highest) + "\n";
		}

	}
}
