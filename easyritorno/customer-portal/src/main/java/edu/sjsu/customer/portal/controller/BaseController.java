package edu.sjsu.customer.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/customer")
public class BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String startPage(ModelMap model) {
		return "index";
	}
}
