package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stocks.Product;

class ProductTest {
    private Product product;
    private static final String PRODUCT_NAME = "Test Product";
    private static final int INITIAL_COUNT = 10;

    @BeforeEach
    void setUp() {
        product = new Product(PRODUCT_NAME, INITIAL_COUNT);
    }

    @Test
    void testProductCreation() {
        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(INITIAL_COUNT, product.getCount());
    }

    @Test
    void testIncrementCount() {
        int incrementAmount = 5;
        product.incrementCount(incrementAmount);
        assertEquals(INITIAL_COUNT + incrementAmount, product.getCount());
    }

    @Test
    void testDecrementCount() {
        int decrementAmount = 5;
        product.decrementCount(decrementAmount);
        assertEquals(INITIAL_COUNT - decrementAmount, product.getCount());
    }

    @Test
    void testDecrementBelowZero() {
        int decrementAmount = INITIAL_COUNT + 1;
        product.decrementCount(decrementAmount);
        assertEquals(0, product.getCount(), "Count should not go below 0");
    }

    @Test
    void testToString() {
        String expected = product.getID() + "|" + PRODUCT_NAME + "|" + INITIAL_COUNT;
        assertEquals(expected, product.toString());
    }
} 