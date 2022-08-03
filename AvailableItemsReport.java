package reports;

import stocks.Product;
import stocks.StockManager;

public class AvailableItemsReport implements Report
{

	private StockManager inventory;

	/**
	 * Default constructor
	 */
	public AvailableItemsReport(StockManager s)
	{
		inventory = s;
	}

	/**
	 * 
	 */
	@Override
	public void printReport()
	{
		System.out.println("Available Products Report:\nProducts appear in the format: name(id): amount");
		for (Product p : inventory.getProductList())
			if (p.getCount() > 0)
				System.out.println(p.getName() + "(" + p.getID() + "): " + p.getCount());
	}
}