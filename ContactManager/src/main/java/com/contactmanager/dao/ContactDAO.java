package com.contactmanager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contactmanager.model.Contact;

public interface ContactDAO  extends JpaRepository<Contact, Integer>{

	@Query("from Contact as c where c.user.id=:userId")
	public List<Contact> getContactsByUser(@Param("userId") int userId);
}


