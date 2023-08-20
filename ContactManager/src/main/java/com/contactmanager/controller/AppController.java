package com.contactmanager.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.contactmanager.model.User;

public interface AppController {
	@GetMapping("/")
	public ModelAndView homePage(ModelAndView model);
	@GetMapping("/signup")
	public ModelAndView signUpPage(ModelAndView model);
	@PostMapping("/registerUser")
	public ModelAndView registerUser(@ModelAttribute User user ,ModelAndView model);
	@GetMapping("/signin")
	public String loginPage(ModelAndView model);
	
	/*
	 * @GetMapping("/user_dashbaord") public ModelAndView showDashbaord(ModelAndView
	 * model,Principal principal);
	 * 
	 * // Add contact form handler
	 * 
	 * @GetMapping("/addContact") public ModelAndView openAddContact(ModelAndView
	 * model,Principal principal);
	 */
	
}
