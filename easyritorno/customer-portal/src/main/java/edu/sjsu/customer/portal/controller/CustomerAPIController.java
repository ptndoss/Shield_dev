package edu.sjsu.customer.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sjsu.customer.portal.service.CustomerAPIService;

@Controller
@RequestMapping("/customer/api")
public class CustomerAPIController {

	@Autowired
	CustomerAPIService service;
}
