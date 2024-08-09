package com.example.demo.service;

import java.util.Set;

import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Purchase {
    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;
}
