package com.contactmanager.controller.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.contactmanager.controller.AppController;
import com.contactmanager.dao.UserDAO;
import com.contactmanager.model.Contact;
import com.contactmanager.model.User;

@Controller
public class AppControllerImpl implements AppController {
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserDAO userDAO;

	@Override
	public ModelAndView homePage(ModelAndView model) {
		model.setViewName("home");
		return model;
	}

	@Override
	public ModelAndView signUpPage(ModelAndView model) {
		model.setViewName("signup");
		return model;
	}

	@Override
	public ModelAndView registerUser(@ModelAttribute User user ,ModelAndView model) {
		user.setRole("USER");
		user.setImageUrl("default.png");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDAO.save(user);
		model.setViewName("home");
		return model;
	}

	@Override
	public String loginPage(ModelAndView model) {
		return "login";
	}
	
	
	/*
	 * @Override public ModelAndView showDashbaord(ModelAndView model,Principal
	 * principal) { String name = principal.getName(); System.out.println(name);
	 * User user = userDAO.getUserByUserName(name); model.addObject("user", user);
	 * model.setViewName("user_dashboard"); return model; }
	 * 
	 * @Override public ModelAndView openAddContact(ModelAndView model,Principal
	 * principal) { String name = principal.getName(); User user =
	 * userDAO.getUserByUserName(name); model.addObject("contact", new Contact());
	 * model.addObject("user", user); model.setViewName("normal/add_contact");
	 * return model; }
	 */

}
