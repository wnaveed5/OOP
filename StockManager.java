package stocks;

import java.util.ArrayList;
import java.util.List;

import main.util.FileManager;

/**
 * Manages the {@link Product} and {@link Store} information within the inventory system.
 */
public class StockManager
{
	private FileManager fm;
	/**
	 * Stores a list of all products in the system
	 */
	private List<Product> products;

	/**
	 * Stores a list of all store locations in the system
	 */
	private List<Store> stores;

	/**
	 * Default constructor
	 */
	public StockManager()
	{
		fm = new FileManager();
		products = fm.readProducts();
		stores = fm.readStores();
	}

	/**
	 * Appends a {@link Product} to the list of products.
	 * 
	 * @param p
	 *            The product to be appended to the list.
	 */
	public void addProduct(Product p)
	{
		products.add(p);
		saveUpdatedStock();
	}

	/**
	 * Creates a new store from command-line input and adds it to the store list.
	 */
	public void addStore(Store s)
	{
		stores.add(s);
		fm.writeStores(stores);;
	}

	/**
	 * Gets the current list of all products in the system.
	 * 
	 * @return the current list of all products in the system.
	 */
	public Product[] getProductList()
	{
		Product[] list = new Product[products.size()];
		for (int i = 0; i < list.length; i++)
			list[i] = products.get(i);
		return list;
	}

	/**
	 * Gets the current list of all stores in the system.
	 * 
	 * @return the current list of all stores in the system.
	 */
	public Store[] getStoreList()
	{
		Store[] list = new Store[stores.size()];
		for (int i = 0; i < list.length; i++)
			list[i] = stores.get(i);
		return list;
	}

	/**
	 * Searches the product list for the product with a matching ID.
	 * 
	 * @param id
	 *            the id of the product to find.
	 * @return the matching product, or null if none exists.
	 */
	public Product findProductByID(int id)
	{
		for (Product p : products)
			if (p.getID() == id)
				return p;
		return null;
	}

	/**
	 * Searches the store list for the store with a matching ID.
	 * 
	 * @param id
	 *            the id of the store to find.
	 * @return the matching store, or null if none exists.
	 */
	public Store findStoreByID(int id)
	{
		for (Store p : stores)
			if (p.getID() == id)
				return p;
		return null;
	}

	public void saveUpdatedStock() {
		fm.writeProducts(products);		
	}
}