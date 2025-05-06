package com.inventory.controller;

import com.inventory.model.Transaction;
import com.inventory.model.Transaction.TransactionType;
import com.inventory.repository.TransactionRepository;
import com.inventory.repository.StoreRepository;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private StoreRepository storeRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    public String listTransactions(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactions/list";
    }
    
    @GetMapping("/new")
    public String newTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("stores", storeRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("transactionTypes", TransactionType.values());
        return "transactions/form";
    }
    
    @PostMapping
    public String saveTransaction(@ModelAttribute Transaction transaction) {
        transaction.execute();
        transactionRepository.save(transaction);
        return "redirect:/transactions";
    }
    
    @GetMapping("/{id}")
    public String viewTransaction(@PathVariable Long id, Model model) {
        model.addAttribute("transaction", transactionRepository.findById(id).orElseThrow());
        return "transactions/view";
    }
} 