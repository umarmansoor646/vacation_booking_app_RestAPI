package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.example.demo.response.Links;

import lombok.Data;

@Data
public class ExcursionDTO {
	  private String excursion_title;
	  private BigDecimal excursion_price;
	  private String image_URL;
	  private Date create_date;
	  private Date last_update;
	  private Links _links;
	  private Long id;
}
