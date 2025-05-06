package com.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String address;
    
    public Store() {
        this.name = "";
        this.address = "";
    }
    
    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
} 