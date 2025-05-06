package transactions;

import java.util.Date;
import stocks.Product;

public class IncomingTransaction extends Transaction {
	public IncomingTransaction(int storeID, int month) {
		super(storeID, month);
	}

	public IncomingTransaction(Date date, int storeID) {
		super(date, storeID);
	}

	@Override
	public void executeTransaction() {
		for (Product p : productList.keySet()) {
			p.incrementCount(productList.get(p));
		}
	}

	@Override
	public String toString() {
		return "I," + super.toString();
	}
}