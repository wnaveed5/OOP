package com.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Store store;
    
    private LocalDateTime date;
    private TransactionType type;
    
    @ElementCollection
    @CollectionTable(name = "transaction_products")
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> products = new HashMap<>();
    
    public enum TransactionType {
        INCOMING, OUTGOING
    }
    
    public Transaction() {
        this.date = LocalDateTime.now();
        this.products = new HashMap<>();
    }
    
    public Transaction(Store store, TransactionType type) {
        this.store = store;
        this.type = type;
        this.date = LocalDateTime.now();
    }
    
    public void addProduct(Product product, int quantity) {
        products.put(product, quantity);
    }
    
    public void execute() {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            
            if (type == TransactionType.INCOMING) {
                product.incrementCount(quantity);
            } else {
                product.decrementCount(quantity);
            }
        }
    }
    
    public void setId(Long id) {
        this.id = id;
    }
} 