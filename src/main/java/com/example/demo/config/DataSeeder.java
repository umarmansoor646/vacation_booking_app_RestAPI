package com.example.demo.config;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;

@Component
public class DataSeeder implements ApplicationRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    @Autowired
    public DataSeeder(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (customerRepository.count() == 1) {
            // Retrieve all divisions from the database
            List<Division> divisions = divisionRepository.findAll();
            Division selectedDivision = divisions.get(0);
            
            // Create Customer objects with the selected Division
            customerRepository.saveAll(Arrays.asList(
                new Customer("123 Elm St", new Date(), "John", "Cena", new Date(), "123-456-7890", "12345", selectedDivision),
                new Customer("456 Oak Ave", new Date(), "Jane", "Smith", new Date(), "234-567-8901", "23456", selectedDivision),
                new Customer("789 Pine Rd", new Date(), "Alice", "Johnson", new Date(), "345-678-9012", "34567", selectedDivision),
                new Customer("321 Maple Dr", new Date(), "Bob", "Williams", new Date(), "456-789-0123", "45678", selectedDivision),
                new Customer("654 Cedar Blvd", new Date(), "Charlie", "Brown", new Date(), "567-890-1234", "56789", selectedDivision)
            ));
        } 
    }
}
