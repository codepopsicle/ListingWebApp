package com.autoscout.springboot.repository;

import com.autoscout.springboot.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "SELECT c.listingid FROM Contact c GROUP BY c.listingid", nativeQuery = true)
    List<Contact> getTop30PercentOfContacts();

    @Query(value = "SELECT * FROM Contact LIMIT 0, 100", nativeQuery = true)
    List<Contact> getTop30PercentOfContacts1();

}
