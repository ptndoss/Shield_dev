package edu.sjsu.customer.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.customer.portal.dao.CustomerDAO;
import edu.sjsu.customer.portal.model.Customer;
import edu.sjsu.customer.portal.request.CreateCustomerRequest;
import edu.sjsu.customer.portal.response.CustomerDTO;
import edu.sjsu.customer.portal.service.CustomerAPIService;

@Service
public class CustomerAPIServiceImpl implements CustomerAPIService {

	@Autowired
	CustomerDAO customerDAO;
	
	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
	}


	@Override
	public CustomerDTO createCustomer(CreateCustomerRequest request) {
		CustomerDTO customerDTO = null;
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer  = customerDAO.insert(customer);
		if(null != customer) {
			customerDTO = mapper.convertValue(customer, CustomerDTO.class);
		}
		return customerDTO;
	}
}
