package com.autoscout.springboot.csv.header;

public enum ListHeader {

    ID("id"),
    MAKE("make"),
    PRICE("price"),
    MILEAGE("mileage"),
    SELLER_TYPE("seller_type");

    public final String label;

    private ListHeader(String label) {
        this.label = label;
    }
}
