package com.autoscout.springboot.model;

import javax.persistence.*;

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
}
