package com.autoscout.springboot.model;

import javax.persistence.*;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private long listingId;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private long mileage;

    @Column(nullable = false)
    private String seller_type;

    public Listing(){
        // empty for access
    }

    public Listing(long listingId, String make, long price, long mileage, String seller_type) {
        this.listingId = listingId;
        this.make = make;
        this.price = price;
        this.mileage = mileage;
        this.seller_type = seller_type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getListingId() {
        return listingId;
    }

    public void setListingId(long listing_id) {
        this.listingId= listing_id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }
}
