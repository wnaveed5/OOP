package transactions;

import java.util.Date;
import stocks.Product;

public class OutgoingTransaction extends Transaction {
	public OutgoingTransaction(int storeID, int month) {
		super(storeID, month);
	}

	public OutgoingTransaction(Date date, int storeID) {
		super(date, storeID);
	}

	@Override
	public void executeTransaction() {
		for (Product p : productList.keySet()) {
			p.decrementCount(productList.get(p));
		}
	}

	@Override
	public String toString() {
		return "O," + super.toString();
	}
}