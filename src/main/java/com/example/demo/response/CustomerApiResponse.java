package com.example.demo.response;

import java.util.List;

import com.example.demo.dto.CustomerDTO;

import lombok.Data;

@Data
public class CustomerApiResponse {
	private CustomerList _embedded;
	
	@Data
	public static class CustomerList {
		private List<CustomerDTO> customers;
	}
}
