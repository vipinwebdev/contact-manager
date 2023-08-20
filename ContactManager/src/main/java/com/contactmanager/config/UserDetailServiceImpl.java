package com.contactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.contactmanager.dao.UserDAO;
import com.contactmanager.model.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Fetching user details from database
		User user = userDAO.getUserByUserName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not found user");  
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}

}
