package com.example.demo.dto;

import lombok.Data;


@Data
public class CustomerDTO {
    
	private Long id;
    private String firstName;	  
    private String lastName;
    private String address;
    private String phone;
    private String postal_code;
    private Long division_id;
    private String division;
    private String country;
}
