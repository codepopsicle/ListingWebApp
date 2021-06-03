package com.autoscout.springboot.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    public long listingid;

    @Column(nullable = false)
    private String contactDate;

    public Contact(){
        // empty for access
    }

    public Contact(long listingId, String contactDate){
        this.listingid = listingId;
        this.contactDate = contactDate;
    }

    public long getListingId() {
        return listingid;
    }

    public void setListingId(long listingId) {
        this.listingid = listingId;
    }

    public String getContactDate() {
        return contactDate;
    }

    public void setContactDate(String contactDate) {
        this.contactDate = contactDate;
    }

    private Date convertUtcEpochToDate(String epochTime){

        return new Date(Long.parseLong(epochTime));
    }

    public int getDay(){
        return convertUtcEpochToDate(this.contactDate).getDay();
    }

    public int getMonth() {
        return convertUtcEpochToDate(this.contactDate).getMonth();
    }

    public int getYear(){
        return convertUtcEpochToDate(this.contactDate).getYear();
    }
}
