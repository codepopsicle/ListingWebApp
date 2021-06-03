package com.autoscout.springboot.csv.header;

public enum  ContactHeader {

    LISTING_ID("listing_id"),
    CONTACT_DATE("contact_date");

    public final String label;

    private ContactHeader(String label){
        this.label = label;
    }
}
