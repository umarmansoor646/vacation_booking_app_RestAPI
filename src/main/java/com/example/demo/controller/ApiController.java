package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.ExcursionDTO;
import com.example.demo.dto.VacationDTO;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Division;
import com.example.demo.response.CountryApiResponse;
import com.example.demo.response.CustomerApiResponse;
import com.example.demo.response.CustomerApiResponse.CustomerList;
import com.example.demo.response.DivisionApiResponse;
import com.example.demo.response.DivisionApiResponse.DivisionList;
import com.example.demo.response.ExcursionApiResponse;
import com.example.demo.response.ExcursionApiResponse.ExcursionList;
import com.example.demo.response.Links;
import com.example.demo.response.VacationApiResponse;
import com.example.demo.response.VacationApiResponse.VacationList;
import com.example.demo.service.ApiService;

@RestController
@RequestMapping("${spring.data.rest.base-path}")
@CrossOrigin(origins = "http://localhost:4200")
public class ApiController {
	
	@Autowired
	private ApiService service;
	
	@PostMapping("/customers")
	public void addCustomer(@RequestBody CustomerDTO customer) {
		service.addCustomer(customer);	
	}
	
	@PutMapping("/customers/{customerId}")
	public HttpStatus editCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customer){
		service.editCustomer(customerId, customer);
		return HttpStatus.OK;
	}
	
	@GetMapping("/customers")
	public CustomerApiResponse getAllCustomers(){
		CustomerApiResponse response = new CustomerApiResponse();
		CustomerList customers = new CustomerList();
		customers.setCustomers(service.getAllCustomers());
		response.set_embedded(customers);
        return response;
	}
	
	
	@GetMapping("/customers/{customerId}")
	public CustomerDTO getCustomer(@PathVariable Long customerId){
        return service.getCustomer(customerId);
	}
	
	@GetMapping("/customers/{customerId}/division")
	public Division getCustomerDivision(@PathVariable Long customerId) {
		return service.getCustomerDivision(customerId);
	}
	
	@GetMapping("/divisions")
	public DivisionApiResponse getAllDivisions(){
		DivisionApiResponse response = new DivisionApiResponse();
		DivisionList divisions = new DivisionList();
		divisions.setDivisions(service.getAllDivisions());
		
		divisions.getDivisions().stream().forEach(divisionDTO -> {
			divisionDTO.get_links().getSelf().setHref("http://localhost:8080/api/divisions/" + divisionDTO.getId());
			divisionDTO.get_links().getCountry().setHref("http://localhost:8080/api/countries/" + divisionDTO.getCountry_id());
		});
		
		response.set_embedded(divisions);
		return response;
	}
	
	@GetMapping("/countries")
	public CountryApiResponse getAllCountries(){
		CountryApiResponse response = new CountryApiResponse();
		response.get_embedded().setCountries(service.getAllCountries());
		response.get_embedded().getCountries().stream().forEach(countryDTO -> {
			countryDTO.get_links().getSelf().setHref("http://localhost:8080/api/countries/" + countryDTO.getId());
		});
		return response;
	}
	
	@GetMapping("/vacations")
	public VacationApiResponse getAllVacations() {
		List<VacationDTO> vacations = service.getAllVacations();
        
		vacations.stream().forEach(vacation -> {
			List<ExcursionDTO> excursionDTO = service.getAllVacationExcursions(vacation.getId());
			excursionDTO.stream().forEach(excursion -> {
				Links link = new Links();
	        	link.getSelf().setHref("http://localhost:8080/api/vacations/" + Long.toString(vacation.getId()) + "/excursion/" + Long.toString(excursion.getId()));
				excursion.set_links(link);
			});
			vacation.setExcursions(excursionDTO);
		});
		
        VacationList vacationList = new VacationList();
        vacationList.setVacations(vacations);
        
        VacationApiResponse response = new VacationApiResponse();
        response.set_embedded(vacationList);
        
        List<String> links = new ArrayList<>();
        vacations.stream().forEach(vacation -> {
        	Links link = new Links();
        	link.getSelf().setHref("http://localhost:8080/api/vacations/" + Long.toString(vacation.getId()));
        	vacation.set_links(link);
        	links.add("http://localhost:8080/api/vacations/" + Long.toString(vacation.getId()));
        });
        response.set_links(links);
        return response;
	}
	
	@GetMapping("/vacations/{vacationId}")
	public VacationDTO getVacation(@PathVariable Long vacationId) {
		VacationDTO vacation = service.getVacation(vacationId);
		Links link = new Links();
    	link.getSelf().setHref("http://localhost:8080/api/vacations/" + Long.toString(vacation.getId()));
    	vacation.set_links(link);
    	List<ExcursionDTO> excursionDTO = service.getAllVacationExcursions(vacation.getId());
		excursionDTO.stream().forEach(excursion -> {
			Links linke = new Links();
        	linke.getSelf().setHref("http://localhost:8080/api/vacations/" + Long.toString(vacation.getId()) + "/excursion/" + Long.toString(excursion.getId()));
			excursion.set_links(linke);
		});
		vacation.setExcursions(excursionDTO);
		return vacation;
	}
	
	@GetMapping("/vacations/{vacationId}/excursions/{excursionId}")
	public ExcursionDTO getVacationExcursion(@PathVariable Long vacationId, @PathVariable Long excursionId) {
		 return service.getVacationExcursion(vacationId, excursionId);
	}
	
	@GetMapping("/vacations/{vacationId}/excursions")
	public ExcursionApiResponse getAllVacationExcursions(@PathVariable Long vacationId) {
		ExcursionApiResponse response = new ExcursionApiResponse();
		ExcursionList excursions = new ExcursionList();
		excursions.setExcursions(service.getAllVacationExcursions(vacationId));
		excursions.getExcursions().stream().forEach(excursion -> {
			Links linke = new Links();
        	linke.getSelf().setHref("http://localhost:8080/api/excursion/" + Long.toString(excursion.getId()));
			excursion.set_links(linke);
		});
		response.set_embedded(excursions);
		return response;
	}
	
	@GetMapping("/carts")
	public List<Cart> getAllCarts() {
		return service.getAllCarts();
	}
	
	@GetMapping("/cartItems")
	public List<CartItem> getAllCartItems() {
		return service.getAllCartItems();
	}
	
}
