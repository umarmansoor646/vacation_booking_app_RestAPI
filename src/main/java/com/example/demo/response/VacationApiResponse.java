package com.example.demo.response;

import java.util.List;

import com.example.demo.dto.VacationDTO;

import lombok.Data;

@Data
public class VacationApiResponse {
	 private VacationList _embedded;
    private List<String> _links;
    private Object page;
    
    @Data
    public static class VacationList {
    	private List<VacationDTO> vacations;
    }
}
