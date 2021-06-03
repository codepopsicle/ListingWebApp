package com.autoscout.springboot.service;

import com.autoscout.springboot.model.Contact;
import com.autoscout.springboot.model.Listing;
import com.autoscout.springboot.repository.ContactRepository;
import com.autoscout.springboot.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class ReportingService {

    private final ListingRepository listingRepository;

    private final ContactRepository contactRepository;


    @Autowired
    public ReportingService(ListingRepository listingRepository, ContactRepository contactRepository){
        this.listingRepository = listingRepository;
        this.contactRepository = contactRepository;
    }

    public Map getAverageListingPricePerSellerType(){

        final Map<String, String> formattedPriceMap = new HashMap<>();

        final Map<String, Double> averagePriceMap = listingRepository.findAll().stream()
                .collect(Collectors.groupingBy(Listing::getSeller_type, Collectors.averagingLong(Listing::getPrice)));

        if(averagePriceMap.isEmpty()){

        }

        averagePriceMap.forEach((key, value) -> {
            formattedPriceMap.put(key, formatPrice(value));
        });

        return formattedPriceMap;
    }

    public Map getPercentageDistribution(){

        final Map<String, String> formattedPercentageMap = new TreeMap<>();
        final List<Listing> listingList = listingRepository.findAll();
        final long listingSize = listingList.size();

        final Map<String, Long> countMap = listingList.stream()
                .collect(Collectors.groupingBy(Listing::getMake, Collectors.counting()));

        countMap.forEach((key, value) -> {
            formattedPercentageMap.put(key, formatPercentage((int)(value * 100.0 / listingSize + 0.5)));
        });


        return formattedPercentageMap;
    }

    public String getTop30MostFrequentlyContactedListings(){

        /* First generate a Map of Listing ID -> Percentage distribution (sorted descending) */

        final List<Contact> contactList = contactRepository.findAll();
        final Map<Long, Long> countMap = contactList.stream()
                .collect(Collectors.groupingBy(Contact::getListingId, Collectors.counting()));
        final double top30percentCount = 0.3 * countMap.entrySet().size();
        final Map<Long, Long> sortedCountMap = countMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect( toMap(e -> e.getKey(), Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));


        /* Limit the stream to top 30 percent entries */
        List<Long> contactIDList = sortedCountMap.entrySet().stream().map(entry -> entry.getKey()).
                limit((int) top30percentCount).collect(Collectors.toList());

        final List<Listing> listingList = listingRepository.findAll().stream().filter(item -> contactIDList.contains(item.getListingId())).
                collect(Collectors.toList());
        final double averagePrice = listingList.stream().
                collect(Collectors.averagingDouble(Listing::getPrice));

        return formatPrice(averagePrice);

    }

    private String formatPrice(Double price){

        final Locale locale = Locale.GERMANY;
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(price);
    }

    private String formatPercentage(int percentage){
        return percentage + "%";
    }

}
