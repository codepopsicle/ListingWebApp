package com.autoscout.springboot;

import java.text.NumberFormat;
import java.util.Locale;

public class ServiceUtils {

    public static String formatMileage(String mileage){
        return mileage + " KM";
    }

    public static String formatPrice(Double price){

        final Locale locale = Locale.GERMANY;
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(price);
    }

    public static String formatPercentage(int percentage){
        return percentage + "%";
    }
}
