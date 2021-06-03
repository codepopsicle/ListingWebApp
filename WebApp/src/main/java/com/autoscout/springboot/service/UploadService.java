package com.autoscout.springboot.service;

import com.autoscout.springboot.csv.header.ContactHeader;
import com.autoscout.springboot.csv.header.HeaderType;
import com.autoscout.springboot.csv.header.ListHeader;
import com.autoscout.springboot.exception.IncorrectFormatException;
import com.autoscout.springboot.model.Contact;
import com.autoscout.springboot.model.Listing;
import com.autoscout.springboot.repository.ContactRepository;
import com.autoscout.springboot.repository.ListingRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

@Service
public class UploadService {

    private final ListingRepository listingRepository;

    private final ContactRepository contactRepository;

    @Autowired
    public UploadService(ListingRepository listingRepository, ContactRepository contactRepository){
        this.listingRepository = listingRepository;
        this.contactRepository = contactRepository;
    }

    public void validateAndUploadFile(InputStream inputStream, HeaderType headerType) throws IOException,
            IncorrectFormatException{

        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String[] headers;
        switch (headerType){

            case CONTACT:
                headers = createArrayFromHeaderEnum(ContactHeader.class);
                validateAndProcessContactFile(fileReader, headers);
                break;

            case LISTING:
                headers = createArrayFromHeaderEnum(ListHeader.class);
                validateAndProcessListingFile(fileReader, headers);
                break;
        }
    }

    private static String[] createArrayFromHeaderEnum(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public void validateAndProcessListingFile(BufferedReader inputReader, String[] headers) throws IOException,
            IncorrectFormatException{

        CSVParser csvParser = new CSVParser(inputReader,
                CSVFormat.DEFAULT.withHeader(headers).withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        try{
            for (CSVRecord record : csvRecords) {
                final long id = Long.parseLong(record.get(ListHeader.ID));
                final String make = record.get(ListHeader.MAKE);
                final long price = Long.parseLong(record.get(ListHeader.PRICE));
                final long mileage = Long.parseLong(record.get(ListHeader.MILEAGE));
                final String sellerType = record.get(ListHeader.SELLER_TYPE);

                final Listing listing;

                if(listingRepository.findByListingId(id).isEmpty()){
                    listing = new Listing(id, make, price, mileage, sellerType);
                }
                else{
                    listing = listingRepository.findByListingId(id).get(0);
                    listing.setMake(make);
                    listing.setMileage(mileage);
                    listing.setPrice(price);
                    listing.setSeller_type(sellerType);
                }

                listingRepository.save(listing);

            }
        } catch(Exception e){

            throw new IncorrectFormatException("File with incorrect format uploaded : " + e.getMessage());
        }
    }

    public void validateAndProcessContactFile(BufferedReader inputReader, String[] headers) throws IOException,
            IncorrectFormatException{

        CSVParser csvParser = new CSVParser(inputReader,
                CSVFormat.DEFAULT.withHeader(headers).withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        try{
            for (CSVRecord record : csvRecords) {
                final long id = Long.parseLong(record.get(ContactHeader.LISTING_ID));
                final String contactDate = record.get(ContactHeader.CONTACT_DATE);

                final Contact contact = new Contact(id, contactDate);

                contactRepository.save(contact);
            }
        } catch(Exception e){

            throw new IncorrectFormatException("File with incorrect format uploaded : " + e.getMessage());
        }
    }
}
