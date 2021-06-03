package com.autoscout.springboot.service;

import com.autoscout.springboot.ServiceUtils;
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
            formattedPriceMap.put(key, ServiceUtils.formatPrice(value));
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
            formattedPercentageMap.put(key, ServiceUtils.formatPercentage((int)(value * 100.0 / listingSize + 0.5)));
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

        return ServiceUtils.formatPrice(averagePrice);

    }

    public Map getMonthlyReport(){

        /* First generate a Map of Month -> Map<ListingID, Count> */

        final List<Contact> contactList = contactRepository.findAll();
        final Map<Integer, Map<Long, Long>> countMap = contactList.stream()
                .collect(Collectors.groupingBy(Contact::getDay, Collectors.groupingBy(Contact::getListingId, Collectors.counting())));

        /* Sorting and limiting to 5 entries per month */
        final Map<Integer, Map<Long, Long>> sortedCountMap = new HashMap<>();
        countMap.forEach((key, value) -> {
            sortedCountMap.put(key, value.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .limit(5)
                    .collect( toMap(e -> e.getKey(), Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new)));
        });

        /* Create a new Map<Month, List<Listing>> */
        final Map<Integer, List<Listing>> reportMap = new LinkedHashMap<>();
        sortedCountMap.forEach((key, value) -> {
            List<Listing> listingList = new ArrayList<>();
            value.forEach((k, v) -> {
                listingList.add(listingRepository.findByListingId(k).get(0));
            });
            reportMap.put(key, listingList);
        });

        return reportMap;

    }

}
