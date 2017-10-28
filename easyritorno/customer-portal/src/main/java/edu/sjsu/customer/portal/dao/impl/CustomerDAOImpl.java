package edu.sjsu.customer.portal.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import edu.sjsu.customer.portal.dao.CustomerDAO;
import edu.sjsu.customer.portal.model.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
    private MongoOperations mongoOperations;
	
	@Override
	public Customer insert(Customer customer) {
		mongoOperations.insert(customer);
		return customer;
	}

}
