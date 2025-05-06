package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stocks.Store;

class StoreTest {
    private Store store;
    private static final String STORE_NAME = "Test Store";
    private static final String STORE_ADDRESS = "123 Test St";

    @BeforeEach
    void setUp() {
        store = new Store(STORE_NAME, STORE_ADDRESS);
    }

    @Test
    void testStoreCreation() {
        assertEquals(STORE_NAME, store.getName());
        assertEquals(STORE_ADDRESS, store.getAddress());
    }

    @Test
    void testToString() {
        String expected = store.getID() + "|" + STORE_NAME + "|" + STORE_ADDRESS;
        assertEquals(expected, store.toString());
    }

    @Test
    void testUniqueIDs() {
        Store anotherStore = new Store("Another Store", "456 Test St");
        assertNotEquals(store.getID(), anotherStore.getID(), "Store IDs should be unique");
    }
} 