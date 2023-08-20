package com.contactmanager.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.contactmanager.model.Contact;

public interface UserController {
	@GetMapping("/user_dashbaord")
	public ModelAndView showDashbaord(ModelAndView model, Principal principal);

	// Add contact form handler
	@GetMapping("/addContact")
	public ModelAndView openAddContact(ModelAndView model, Principal principal);
	@GetMapping("/updateContact/{cId}")
	public ModelAndView openUpdateContact(@PathVariable int cId,ModelAndView model, Principal principal);
	@PostMapping("/process-contact")
	public ModelAndView addContact(@ModelAttribute Contact contact,@RequestParam("image") MultipartFile file, ModelAndView model, Principal principal);
	
	
	// Show contact handler
	@GetMapping("/showContacts")
	public ModelAndView showContacts(ModelAndView model, Principal principal);
	
	@GetMapping("/contact/{cId}")
	public ModelAndView showContactDetail(@PathVariable("cId") int cId, ModelAndView model, Principal principal);
	
	@GetMapping("/profile")
	public ModelAndView userProfile(ModelAndView model);
}
