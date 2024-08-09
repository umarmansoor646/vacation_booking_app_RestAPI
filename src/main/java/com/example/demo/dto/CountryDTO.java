package com.example.demo.dto;

import com.example.demo.response.Links;

import lombok.Data;

@Data
public class CountryDTO {

    private Long id;
    private String country_name;
    private Links _links = new Links();

}

