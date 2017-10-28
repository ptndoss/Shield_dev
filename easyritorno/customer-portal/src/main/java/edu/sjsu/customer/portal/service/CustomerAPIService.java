package edu.sjsu.customer.portal.service;

import edu.sjsu.customer.portal.request.CreateCustomerRequest;
import edu.sjsu.customer.portal.response.CustomerDTO;

public interface CustomerAPIService {

	CustomerDTO createCustomer(CreateCustomerRequest request);

}
