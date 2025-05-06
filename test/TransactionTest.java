package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import stocks.Product;
import stocks.Store;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;

class TransactionTest {
    private Store store;
    private Product product;
    private IncomingTransaction incomingTransaction;
    private OutgoingTransaction outgoingTransaction;
    private static final int INITIAL_PRODUCT_COUNT = 10;
    private static final int TRANSACTION_AMOUNT = 5;

    @BeforeEach
    void setUp() {
        store = new Store("Test Store", "123 Test St");
        product = new Product("Test Product", INITIAL_PRODUCT_COUNT);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        incomingTransaction = new IncomingTransaction(store.getID(), currentMonth);
        outgoingTransaction = new OutgoingTransaction(store.getID(), currentMonth);
    }

    @Test
    void testIncomingTransaction() {
        incomingTransaction.addProduct(product, TRANSACTION_AMOUNT);
        incomingTransaction.executeTransaction();
        assertEquals(INITIAL_PRODUCT_COUNT + TRANSACTION_AMOUNT, product.getCount());
    }

    @Test
    void testOutgoingTransaction() {
        outgoingTransaction.addProduct(product, TRANSACTION_AMOUNT);
        outgoingTransaction.executeTransaction();
        assertEquals(INITIAL_PRODUCT_COUNT - TRANSACTION_AMOUNT, product.getCount());
    }

    @Test
    void testGetProductListForTransaction() {
        incomingTransaction.addProduct(product, TRANSACTION_AMOUNT);
        Product[] products = incomingTransaction.getProductListForTransaction();
        assertEquals(1, products.length);
        assertEquals(product, products[0]);
    }

    @Test
    void testGetNumProductInTransaction() {
        incomingTransaction.addProduct(product, TRANSACTION_AMOUNT);
        assertEquals(TRANSACTION_AMOUNT, incomingTransaction.getNumProductInTransaction(product));
    }

    @Test
    void testToString() {
        incomingTransaction.addProduct(product, TRANSACTION_AMOUNT);
        String result = incomingTransaction.toString();
        assertTrue(result.startsWith("I,"));
        assertTrue(result.contains(String.valueOf(product.getID())));
        assertTrue(result.contains(String.valueOf(TRANSACTION_AMOUNT)));
    }
} 