package reports;

import stocks.Product;
import stocks.StockManager;

/**
 * 
 */
public class AllItemsEnteredReport implements Report
{
	private StockManager inventory;

	/**
	 * Default constructor
	 */
	public AllItemsEnteredReport(StockManager s)
	{
		inventory = s;
	}

	/**
	 * 
	 */
	@Override
	public void printReport()
	{
		System.out.println("All Products Report:\nProducts appear in the format: name(id): amount");
		for (Product p : inventory.getProductList())
			System.out.println(p.getName() + "(" + p.getID() + "): " + p.getCount());
	}
}