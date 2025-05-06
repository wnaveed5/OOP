package com.inventory.controller;

import com.inventory.model.Store;
import com.inventory.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stores")
public class StoreController {
    
    @Autowired
    private StoreRepository storeRepository;
    
    @GetMapping
    public String listStores(Model model) {
        model.addAttribute("stores", storeRepository.findAll());
        return "stores/list";
    }
    
    @GetMapping("/new")
    public String newStoreForm(Model model) {
        model.addAttribute("store", new Store());
        return "stores/form";
    }
    
    @PostMapping
    public String saveStore(@ModelAttribute Store store) {
        storeRepository.save(store);
        return "redirect:/stores";
    }
    
    @GetMapping("/{id}/edit")
    public String editStoreForm(@PathVariable Long id, Model model) {
        model.addAttribute("store", storeRepository.findById(id).orElseThrow());
        return "stores/form";
    }
    
    @PostMapping("/{id}")
    public String updateStore(@PathVariable Long id, @ModelAttribute Store store) {
        store.setId(id);
        storeRepository.save(store);
        return "redirect:/stores";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteStore(@PathVariable Long id) {
        storeRepository.deleteById(id);
        return "redirect:/stores";
    }
} 