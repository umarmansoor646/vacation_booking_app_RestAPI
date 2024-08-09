package com.example.demo.service;

import com.example.demo.dto.PurchaseDTO;

public interface CheckoutService {
    PurchaseResponse placeOrder(PurchaseDTO purchaseDTO);
}
