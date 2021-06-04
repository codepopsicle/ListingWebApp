package com.autoscout.springboot.repository;

import com.autoscout.springboot.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByListingId(long listingID);

}
