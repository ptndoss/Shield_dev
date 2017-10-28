package edu.sjsu.customer.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.customer.portal.dao.CustomerDAO;
import edu.sjsu.customer.portal.service.CustomerAPIService;

@Service
public class CustomerAPIServiceImpl implements CustomerAPIService {

	@Autowired
	CustomerDAO customerDAO;
}
