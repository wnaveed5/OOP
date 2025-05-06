package transactions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import stocks.Product;

public abstract class Transaction {
    private static int GLOBAL_TRANSACTION_ID_COUNTER = 0;
    protected int transactionStoreID;
    protected Map<Product, Integer> productList;
    protected int month;
    protected Date date;
    protected int ID;

    public Transaction(int storeID, int month) {
        this.transactionStoreID = storeID;
        this.month = month;
        this.date = new Date();
        this.ID = GLOBAL_TRANSACTION_ID_COUNTER++;
        this.productList = new HashMap<>();
    }

    public Transaction(Date date, int storeID) {
        this.transactionStoreID = storeID;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.month = calendar.get(Calendar.MONTH);
        this.date = date;
        this.ID = GLOBAL_TRANSACTION_ID_COUNTER++;
        this.productList = new HashMap<>();
    }

    public int getTransactionStoreID() {
        return transactionStoreID;
    }

    public int getMonth() {
        return month;
    }

    public String getDate() {
        return date.toString();
    }

    public int getID() {
        return ID;
    }

    public void addProduct(Product product, int count) {
        productList.put(product, count);
    }

    public Product[] getProductListForTransaction() {
        return productList.keySet().toArray(new Product[0]);
    }

    public int getNumProductInTransaction(Product product) {
        return productList.getOrDefault(product, -1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ID).append(",").append(date.toString()).append(",").append(productList.size()).append(",").append(System.lineSeparator());
        for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
            sb.append(entry.getKey().getID()).append(",").append(entry.getValue()).append(",").append(System.lineSeparator());
        }
        return sb.toString();
    }

    public abstract void executeTransaction();
} 