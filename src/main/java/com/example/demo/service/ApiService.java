package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CartItemRepository;
import com.example.demo.dao.CartRepository;
import com.example.demo.dao.CountryRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
//import com.example.demo.dao.ExcursionCartItemRepository;
import com.example.demo.dao.ExcursionRepository;
import com.example.demo.dao.VacationRepository;
import com.example.demo.dto.CountryDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.DivisionDTO;
import com.example.demo.dto.ExcursionDTO;
import com.example.demo.dto.VacationDTO;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Country;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import com.example.demo.entities.Excursion;
import com.example.demo.entities.Vacation;
import com.example.demo.response.Links;
import com.example.demo.util.DtoConverter;

@Service
public class ApiService {
	
	
	private final CustomerRepository customerRepository;
	private final CountryRepository countryRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final DivisionRepository divisionRepository;
	private final VacationRepository vacationRepository;
	private final ExcursionRepository excursionRepository;
	//private final ExcursionCartItemRepository excursionCartItemRepository;
	
	
	@Autowired
	public ApiService(CustomerRepository customerRepository, CountryRepository countryRepository,
			CartRepository cartRepository, CartItemRepository cartItemRepository, DivisionRepository divisionRepository,
			VacationRepository vacationRepository, ExcursionRepository excursionRepository
			//ExcursionCartItemRepository excursionCartItemRepository
					   ) {
		super();
		this.customerRepository = customerRepository;
		this.countryRepository = countryRepository;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.divisionRepository = divisionRepository;
		this.vacationRepository = vacationRepository;
		this.excursionRepository = excursionRepository;
		//this.excursionCartItemRepository = excursionCartItemRepository;
	}

	public void addCustomer(CustomerDTO customerDTO) {
		Customer customer = DtoConverter.CustomerDtoToCustomerModelConverter(customerDTO);
		String division = customerDTO.getDivision();
		division = division.substring(division.lastIndexOf("/") + 1, division.length());
		
		Division Div = divisionRepository.findById(Long.parseLong(division)).orElse(null);
		customer.setDivision(Div);
		
		customerRepository.save(customer);
	}
	
	public boolean editCustomer(Long customerId, CustomerDTO customerDTO) {
		Customer customer = DtoConverter.CustomerDtoToCustomerModelConverter(customerDTO);
		Customer oldCustomer = customerRepository.findById(customerId).orElse(null);
		
		customer.setCustomerId(oldCustomer.getCustomerId());
		customer.setDivision(oldCustomer.getDivision());
		customerRepository.save(customer);
		return true;
	}
	
	public List<CustomerDTO> getAllCustomers(){
		List<Customer> customers = customerRepository.findAll();
		return customers.stream()
				.map(DtoConverter::CustomerModelToCustomerDtoConverter)
				.collect(Collectors.toList());
	}
	
	public CustomerDTO getCustomer(Long customerId) {
		return DtoConverter.CustomerModelToCustomerDtoConverter(customerRepository.findById(customerId).orElse(null));
	}
	
	public Division getCustomerDivision(Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		return customer.isPresent() ? customer.get().getDivision() : null;
	}
	
	public List<DivisionDTO> getAllDivisions() {
		List<Division> divisions =  divisionRepository.findAll();
		List<DivisionDTO> divisionsDTO = divisions.stream()
                .map(DtoConverter::DivisionModelToDivisionDtoConverter)
                .collect(Collectors.toList());
		return divisionsDTO;
	}
	
	public List<CountryDTO> getAllCountries() {
		List<Country> countries = countryRepository.findAll();
		return countries.stream()
				.map(DtoConverter::CountryModelToCountryDtoConverter)
				.collect(Collectors.toList());
	}
	
	public List<VacationDTO> getAllVacations() {
		List<Vacation> vacations = vacationRepository.findAll();
		
		return vacations.stream()
				.map(DtoConverter::VacationModelToVacationDtoConverter)
				.collect(Collectors.toList());
	}
	
	public VacationDTO getVacation(Long vacationId) {
		return DtoConverter.VacationModelToVacationDtoConverter(vacationRepository.findById(vacationId).orElse(null));
	}
	
	public ExcursionDTO getVacationExcursion(Long vacationId, Long excursionId) {
		Optional<Vacation> vacation = vacationRepository.findById(vacationId);
		Optional<Excursion> excursion = excursionRepository.findById(excursionId);
		if (excursion.isPresent() && vacation.isPresent()) {
			Links linke = new Links();
	    	linke.getSelf().setHref("http://localhost:8080/api/vacations/" + Long.toString(vacationId) + "/excursion/" + Long.toString(excursionId));
			ExcursionDTO excursionDTO = DtoConverter.ExcursionModelToExcursionDtoConverter(excursion.get());
	    	excursionDTO.set_links(linke);
			return excursionDTO;
		}
		
		return null;
	}
	
	public List<ExcursionDTO> getAllVacationExcursions(Long vacationId) {
		Optional<Vacation> vacation = vacationRepository.findById(vacationId);
		List<Excursion> excursions = excursionRepository.findAll();
		List<Excursion> excursionDTO = excursions.stream()
				.filter(excursion -> excursion.getVacation() == vacation.get())
				.collect(Collectors.toList());
		
		return excursionDTO.stream()
				.map(DtoConverter::ExcursionModelToExcursionDtoConverter)
				.collect(Collectors.toList());
	}
	
	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}
	
	public List<CartItem> getAllCartItems() {
		return cartItemRepository.findAll();
	}
}
