package edu.sjsu.customer.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.customer.portal.request.CreateCustomerRequest;
import edu.sjsu.customer.portal.response.CustomerDTO;
import edu.sjsu.customer.portal.service.CustomerAPIService;

@Controller
@RequestMapping("/customer/api")
public class CustomerAPIController {

	@Autowired
	CustomerAPIService apiService;
	
	
	@RequestMapping(value = "/add/custromer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CreateCustomerRequest request) {
		ResponseEntity<CustomerDTO> responseEntity = null;
		try {
			CustomerDTO response = apiService.createCustomer(request);
			responseEntity = new ResponseEntity<CustomerDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			CustomerDTO response = new CustomerDTO();
			response.setMessage(e.getMessage());
			response.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
			responseEntity = new ResponseEntity<CustomerDTO>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
}
