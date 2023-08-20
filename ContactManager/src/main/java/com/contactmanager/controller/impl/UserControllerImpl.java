package com.contactmanager.controller.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.contactmanager.controller.UserController;
import com.contactmanager.dao.ContactDAO;
import com.contactmanager.dao.UserDAO;
import com.contactmanager.model.Contact;
import com.contactmanager.model.User;
@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserDAO userDAO;
	@Autowired
	ContactDAO contactDAO;
	
	// Method for adding common data to views
	@ModelAttribute
	public void addCommonData(ModelAndView model, Principal principal) {
		String email = principal.getName();
		User user = userDAO.getUserByUserName(email);
		model.addObject("user", user);
	}
	
	@Override
	public ModelAndView showDashbaord(ModelAndView model, Principal principal) {
		String name = principal.getName();
		System.out.println(name);
		User user = userDAO.getUserByUserName(name);
		//model.addObject("user", user);
		model.setViewName("user_dashboard");
		return model;
	}

	@Override
	public ModelAndView openAddContact(ModelAndView model, Principal principal) {
		String name = principal.getName();
		User user = userDAO.getUserByUserName(name);
		model.addObject("contact", new Contact());
		model.addObject("heading", "Add Your Contact");
		//model.addObject("user", user);
		model.setViewName("normal/add_contact");
		return model;
	}

	@Override
	public ModelAndView addContact(@ModelAttribute Contact contact,@RequestParam("image") MultipartFile file, ModelAndView model, Principal principal) {
		System.out.println("File status: "+file.isEmpty());
		System.out.println("Id : "+contact.getcId());
		
			//Contact oldContactDetails = contactDAO.findById(contact.getcId()).get();
		try {
			
			if(contact.getcId()>0) {
				if(!file.isEmpty()) {
					Contact oldContactDetails = contactDAO.findById(contact.getcId()).get();
					File delFile= new ClassPathResource("static/css/images").getFile();
					File file1=new File(delFile,oldContactDetails.getImageUrl());
					file1.delete();
					
					
					File saveFile= new ClassPathResource("static/css/images").getFile();
					Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+contact.getcId()+file.getOriginalFilename());
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					contact.setImageUrl(contact.getcId()+file.getOriginalFilename());
					
				}else {
					Contact oldContactDetails = contactDAO.findById(contact.getcId()).get();
					contact.setImageUrl(contact.getcId()+oldContactDetails.getImageUrl());
				}
				
			}else {
				if(file.isEmpty()) {
					System.out.println("File is empty");
					contact.setImageUrl("contact.png");
					
				}else {
					System.out.println("File is not empty");
					contact.setImageUrl(contact.getcId()+file.getOriginalFilename());
					File file2 = new ClassPathResource("static/css/images").getFile();
					Path path = Paths.get(file2.getAbsolutePath()+File.separator+contact.getcId()+file.getOriginalFilename());
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				}
				
			}
		}catch (Exception e) {
			System.out.println("Exception :"+ e);
		}	
		
		/*
		 * if(file.isEmpty()) { System.out.println("File is empty");
		 * contact.setImageUrl("contact.png");
		 * 
		 * }else { System.out.println("File is not empty");
		 * contact.setImageUrl(file.getOriginalFilename()); try { File file2 = new
		 * ClassPathResource("static/css/images").getFile(); Path path =
		 * Paths.get(file2.getAbsolutePath()+File.separator+file.getOriginalFilename());
		 * Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		 * }catch (Exception e) { System.out.println("Exception :"+ e); } }
		 */
				
			
		
		
		String name = principal.getName();
		User user = userDAO.getUserByUserName(name);
		contact.setUser(user);
		user.getContacts().add(contact);
		userDAO.save(user);
		System.out.println("Contact added");
		//model.addObject("user", user);
		model.setViewName("redirect:/user/showContacts");
		return model;
	}

	@Override
	public ModelAndView showContacts(ModelAndView model, Principal principal) {
		String username = principal.getName();
		User user = userDAO.getUserByUserName(username);
		//model.addObject("user", user);
		
		List<Contact> contacts = contactDAO.getContactsByUser(user.getId());
		//System.out.println(contacts);
		model.addObject("contacts", contacts);
		model.setViewName("normal/show_contacts");
		return model;
	}

	@Override
	public ModelAndView showContactDetail(int cId, ModelAndView model, Principal principal) {
		String username = principal.getName();
		System.out.println("Cid "+cId);
		User user = userDAO.getUserByUserName(username);
		//model.addObject("user", user);
		//System.out.println("Image: "+user.getImageUrl());
		Optional<Contact> contactOptional = contactDAO.findById(cId);
		Contact contact = contactOptional.get();
		if(contact.getUser().getId()==user.getId())
			model.addObject("contact", contact);
		model.setViewName("normal/contact_details");
		return model;
	}

	//Update Contact
	@Override
	public ModelAndView openUpdateContact(@PathVariable int cId, ModelAndView model, Principal principal) {
		
		String name = principal.getName();
		User user = userDAO.getUserByUserName(name);
		//model.addObject("contact", new Contact());
		//model.addObject("user", user);
		
		
		Contact contact = contactDAO.findById(cId).get();
		model.addObject("heading", "Update Your Contact");
		model.addObject("contact", contact);
		model.setViewName("normal/add_contact");
		return model;
	}

	@Override
	public ModelAndView userProfile(ModelAndView model) {
		model.setViewName("normal/user_profile");
		return model;
	}
}
