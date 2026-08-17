package com.learn.bank_crud_app.controller;

import com.learn.bank_crud_app.entity.Customer;
import com.learn.bank_crud_app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
    @Autowired
    private CustomerRepository repo;

    @GetMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", repo.findAll());
        return "customers"; // maps to customers.html or customers.jsp
    }

    @PostMapping("/customers")
    public String addCustomer(Customer customer) {
        repo.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/customers";
    }
}

