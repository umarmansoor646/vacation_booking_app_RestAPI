package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.example.demo.entities.Excursion;
import com.example.demo.response.Links;

import lombok.Data;


@Data
public class VacationDTO {

	private Long id;
    private String vacation_title;
    private String description;
    private BigDecimal travel_price;
    private String image_URL;
    private Date create_date;
    private Date last_update;
    private Links _links;
    private List<ExcursionDTO> excursions;

}
