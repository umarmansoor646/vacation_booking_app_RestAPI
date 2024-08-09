package com.example.demo.util;

import com.example.demo.dto.CartDTO;
import com.example.demo.dto.CountryDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.DivisionDTO;
import com.example.demo.dto.ExcursionDTO;
import com.example.demo.dto.VacationDTO;
import com.example.demo.entities.Cart;
import com.example.demo.entities.Country;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import com.example.demo.entities.Excursion;
import com.example.demo.entities.Vacation;

public class DtoConverter {
	
	public static final DivisionDTO DivisionModelToDivisionDtoConverter(Division divisionModel) {
		DivisionDTO divisionDTO = new DivisionDTO();
		divisionDTO.setDivision_name(divisionModel.getDivision());
		divisionDTO.setId(divisionModel.getDivisionId());
		divisionDTO.setCountry_id(divisionModel.getCountry().getCountryId());
		return divisionDTO;
	}
	
	public static final VacationDTO VacationModelToVacationDtoConverter(Vacation vacationModel) {
		VacationDTO vacationDTO = new VacationDTO();
		vacationDTO.setId(vacationModel.getVacationId());
		vacationDTO.setVacation_title(vacationModel.getVacationTitle());
		vacationDTO.setTravel_price(vacationModel.getTravelFarePrice());
		vacationDTO.setCreate_date(vacationModel.getCreateDate());
		vacationDTO.setDescription(vacationModel.getDescription());
		vacationDTO.setImage_URL(vacationModel.getImageUrl());
		vacationDTO.setLast_update(vacationModel.getLastUpdate());
		
		return vacationDTO;
	}
	
	public static final CountryDTO CountryModelToCountryDtoConverter(Country countryModel) {
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setId(countryModel.getCountryId());
		countryDTO.setCountry_name(countryModel.getCountry());
		return countryDTO;
	}
	
	public static final ExcursionDTO ExcursionModelToExcursionDtoConverter(Excursion excursion) {
		ExcursionDTO excursionDto = new ExcursionDTO();
		excursionDto.setId(excursion.getExcursionId());
		excursionDto.setExcursion_title(excursion.getExcursionTitle());
		excursionDto.setCreate_date(excursion.getCreateDate());
		excursionDto.setExcursion_price(excursion.getExcursionPrice());
		excursionDto.setImage_URL(excursion.getImageUrl());
		excursionDto.setLast_update(excursion.getLastUpdate());
		
		return excursionDto;
	}
	
	public static final CustomerDTO CustomerModelToCustomerDtoConverter(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(customer.getCustomerId());
		customerDTO.setAddress(customer.getAddress());
		customerDTO.setDivision_id(customer.getDivision().getDivisionId());
		customerDTO.setFirstName(customer.getFirstName());
		customerDTO.setLastName(customer.getLastName());
		customerDTO.setPhone(customer.getPhone());
		customerDTO.setPostal_code(customer.getPostal_Code());
		return customerDTO;
	}
	
	public static final Customer CustomerDtoToCustomerModelConverter(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setCustomerId(customerDTO.getId());
		customer.setAddress(customerDTO.getAddress());
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setPhone(customerDTO.getPhone());
		customer.setPostal_Code(customerDTO.getPostal_code());;
		return customer;
	}
	
	public static final Cart CartDtoToCartModelConverter(CartDTO cartDTO) {
		Cart cart = new Cart();
		cart.setPackagePrice(cartDTO.getPackage_price());
		cart.setPartySize(cartDTO.getParty_size());
		cart.setStatus(cartDTO.getStatus());
		cart.setCustomer(DtoConverter.CustomerDtoToCustomerModelConverter(cartDTO.getCustomer()));
		return cart;
	}
	
	public static final Vacation VacationDtoToVacationModelConverter(VacationDTO vacationDTO) {
		Vacation vacation = new Vacation();
		vacation.setVacationId(vacationDTO.getId());
		vacation.setVacationTitle(vacationDTO.getVacation_title());
		vacation.setImageUrl(vacationDTO.getImage_URL());
		vacation.setDescription(vacationDTO.getDescription());
		vacation.setTravelFarePrice(vacationDTO.getTravel_price());
		vacation.setCreateDate(vacationDTO.getCreate_date());
		vacation.setLastUpdate(vacationDTO.getLast_update());
		return vacation;
	}
	
	public static final Excursion ExcursionDtoToExcursionModelConverter(ExcursionDTO excursionDTO) {
		Excursion excursion = new Excursion(); 
		excursion.setExcursionId(excursionDTO.getId());
		excursion.setExcursionPrice(excursionDTO.getExcursion_price());
		excursion.setExcursionTitle(excursionDTO.getExcursion_title());
		excursion.setImageUrl(excursionDTO.getImage_URL());
		excursion.setCreateDate(excursionDTO.getCreate_date());
		excursion.setLastUpdate(excursionDTO.getLast_update());
		return excursion;
	}
}
