package com.autoscout.springboot.repository;

import com.autoscout.springboot.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
