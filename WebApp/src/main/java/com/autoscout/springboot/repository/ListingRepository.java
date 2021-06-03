package com.autoscout.springboot.repository;

import com.autoscout.springboot.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByListingId(long listingID);

    List<Listing> findByListingIdIn(List<Long> listingIDList);

}
