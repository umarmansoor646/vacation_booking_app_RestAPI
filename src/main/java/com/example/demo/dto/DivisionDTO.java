package com.example.demo.dto;

import lombok.Data;

@Data
public class DivisionDTO {

    private Long id;
    private String division_name; 
    private Long country_id;
    private Links _links = new Links(); 
    
    @Data
    public static class Links{
    	private Self self;
    	private CountryLink country;
    	
    	public Links() {
    		self = new Self();
    		country = new CountryLink();
    	}
    	
    	@Data
    	public static class Self{
    		private String href;
    	}
    	
    	@Data
    	public static class CountryLink{
    		private String href;
    	}
    }

}

