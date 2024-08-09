package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartItemDTO {
	private VacationDTO vacation;
	private List<ExcursionDTO> excursions;
}
