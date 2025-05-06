package com.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.inventory.repository.ProductRepository;
import com.inventory.repository.StoreRepository;
import com.inventory.repository.TransactionRepository;

@Controller
public class HomeController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private StoreRepository storeRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - Inventory Management");
        model.addAttribute("productCount", productRepository.count());
        model.addAttribute("storeCount", storeRepository.count());
        model.addAttribute("transactionCount", transactionRepository.count());
        model.addAttribute("recentTransactions", transactionRepository.findTop5ByOrderByDateDesc());
        model.addAttribute("lowStockProducts", productRepository.findByCountLessThan(10));
        return "home";
    }
} 