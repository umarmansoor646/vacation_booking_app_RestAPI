package com.example.demo.dto;

import java.util.Set;

import lombok.Data;

@Data
public class PurchaseDTO {
	private CustomerDTO customer;
	private CartDTO cart;
	private Set<CartItemDTO> cartItems;
}
