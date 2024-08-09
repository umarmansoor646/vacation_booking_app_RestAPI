package com.example.demo.dto;

import java.math.BigDecimal;

import com.example.demo.entities.CartStatus;

import lombok.Data;

@Data
public class CartDTO {

	private Long id;
	private BigDecimal package_price;
	private int party_size;
	private CartStatus status;
	private CustomerDTO customer;
}
