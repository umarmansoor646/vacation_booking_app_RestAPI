package com.example.demo.response;

import java.util.List;

import com.example.demo.dto.CountryDTO;

import lombok.Data;

@Data
public class CountryApiResponse {
	private CountryList _embedded = new CountryList();
	
	@Data
	public static class CountryList{
		private List<CountryDTO> countries;
	}
	
}
