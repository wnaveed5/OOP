package com.inventory.controller;

import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products/list";
    }
    
    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product("", 0));
        return "products/form";
    }
    
    @PostMapping
    public String saveProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }
    
    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).orElseThrow());
        return "products/form";
    }
    
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow();
        existingProduct.setName(product.getName());
        existingProduct.setCount(product.getCount());
        productRepository.save(existingProduct);
        return "redirect:/products";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
} 