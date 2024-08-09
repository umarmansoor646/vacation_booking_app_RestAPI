package com.example.demo.response;

import java.util.List;

import com.example.demo.dto.ExcursionDTO;

import lombok.Data;

@Data
public class ExcursionApiResponse {
	private ExcursionList _embedded;
	
	@Data
	public static class ExcursionList{
		List<ExcursionDTO> excursions;
	}
}
