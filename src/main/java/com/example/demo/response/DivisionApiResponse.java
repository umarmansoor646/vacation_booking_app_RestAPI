package com.example.demo.response;

import java.util.List;

import com.example.demo.dto.DivisionDTO;

import lombok.Data;

@Data
public class DivisionApiResponse {
	private DivisionList _embedded;
	
	@Data
	public static class DivisionList{
		private List<DivisionDTO> divisions;
	}
}
