package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import com.example.demo.dao.ExcursionRepository;
import com.example.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CartItemRepository;
import com.example.demo.dao.CartRepository;
//import com.example.demo.dao.ExcursionCartItemRepository;
import com.example.demo.dto.CartDTO;
import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.ExcursionDTO;
import com.example.demo.dto.PurchaseDTO;
import com.example.demo.util.DtoConverter;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
//	@Autowired
//	private ExcursionCartItemRepository excursionCartItemRepository;
@Autowired
private ExcursionRepository excursionRepository;

    @Override
    public PurchaseResponse placeOrder(PurchaseDTO purchaseDTO) {
    	
        // Logic to place order
        CustomerDTO customerDTO = purchaseDTO.getCustomer();      
        CartDTO cartDTO = purchaseDTO.getCart();
        Set<CartItemDTO> cartItemsDTO = purchaseDTO.getCartItems();

        System.out.println(customerDTO);
        System.out.println(cartDTO);
        System.out.println(cartItemsDTO.size());
        System.out.println(cartItemsDTO);
        
        Customer customer = DtoConverter.CustomerDtoToCustomerModelConverter(customerDTO);
        Cart cart = DtoConverter.CartDtoToCartModelConverter(cartDTO);
      
        // Generate order tracking number
        String orderTrackingNumber = generateOrderTrackingNumber(customer, cart, null);
        cart.setStatus(CartStatus.ordered);
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setCustomer(customer);
        LocalDateTime currentDateTime = LocalDateTime.now();
        cart.setCreateDate(currentDateTime);
        cart.setLastUpdate(currentDateTime);
        
        List<CartItem> cartItems = new ArrayList<>();
        // Fetching Vacation and Set Excursion from CartItemDTO
        List<Vacation> vacationsList = new ArrayList<>();
        List<List<Excursion>> Excursions_2D_list = new ArrayList<>();
        List<List<Excursion>> excursions2DList = new ArrayList<>();

        cartItemsDTO.stream().forEach(cartItemDTO -> {
        	Vacation vacation = DtoConverter.VacationDtoToVacationModelConverter(cartItemDTO.getVacation());
        	vacationsList.add(vacation);
        	CartItem item = new CartItem();
        	item.setCart(cart);
        	item.setVacation(vacation);
            item.setCreateDate(currentDateTime);
            item.setLastUpdate(currentDateTime);
        	cartItems.add(item);

            /////////
            // Create Excursion entities for each CartItem
            List<Excursion> excursions = new ArrayList<>();
            cartItemDTO.getExcursions().forEach(excursionDTO -> {
                Excursion excursion = DtoConverter.ExcursionDtoToExcursionModelConverter(excursionDTO);
                excursion.setVacation(vacation);
                excursions.add(excursion);
            });
            excursions2DList.add(excursions);
            /////////
        });

        // Persist cart
        cartRepository.save(cart);

        // Persist cart items
        cartItemRepository.saveAll(cartItems);

        // Associate and persist excursions with cart items
        for (int i = 0; i < cartItems.size(); ++i) {
            CartItem cartItem = cartItems.get(i);
            List<Excursion> excursions = excursions2DList.get(i);

            for (Excursion excursion : excursions) {
                excursion.getCartItems().add(cartItem);
                cartItem.getExcursions().add(excursion);
                excursionRepository.save(excursion);
            }
        }
        ////////
              

        
//        cartItemsDTO.stream().forEach(cartItemDTO -> {
//        	List<ExcursionDTO> excursionsDTO = cartItemDTO.getExcursions();
//        	List<Excursion> excursions = new ArrayList<>();
//        	excursionsDTO.stream().forEach(excursionDTO -> {
//        		Excursion excursion = DtoConverter.ExcursionDtoToExcursionModelConverter(excursionDTO);
//        		excursion.setVacation(DtoConverter.VacationDtoToVacationModelConverter(cartItemDTO.getVacation()));
//        		excursions.add(excursion);
//        	});
//        	Excursions_2D_list.add(excursions);
//        });
//        List<ExcursionCartItem> excursionCartItems = new ArrayList<>();
//
//        for(int i = 0; i < cartItems.size(); ++i) {
//        	for(int j = 0; j < Excursions_2D_list.size(); ++j) {
//        		for(int k = 0; k < Excursions_2D_list.get(i).size(); ++k) {
//		        	ExcursionCartItem excursionCartItem = new ExcursionCartItem();
//		        	excursionCartItem.setCartItem(cartItems.get(i));
//		        	excursionCartItem.setExcursion(Excursions_2D_list.get(j).get(k));
//		        	excursionCartItems.add(excursionCartItem);
//        		}
//        	}
//        }
//
//        System.out.println(excursionCartItems);
//       // saving in DB
//        cartRepository.save(cart);
//        cartItemRepository.saveAll(cartItems);
//        //excursionCartItemRepository.saveAll(excursionCartItems);
        
        // Return PurchaseResponse with the order tracking number
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber(Customer customer, Cart cart, Set<CartItem> cartItems) {
        // Logic to generate order tracking number
        return UUID.randomUUID().toString();
    }
}
