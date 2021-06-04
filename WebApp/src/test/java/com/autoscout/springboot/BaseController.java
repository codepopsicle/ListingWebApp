package com.autoscout.springboot;

import com.autoscout.springboot.repository.ContactRepository;
import com.autoscout.springboot.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BaseController {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ResourceLoader resourceLoader;

    @Autowired
    protected ListingRepository listingRepository;

    @Autowired
    protected ContactRepository contactRepository;

    protected void uploadListingFile() throws Exception {

        final File inputFile = resourceLoader.getResource("classpath:listings.csv").getFile();
        final FileInputStream inputStream = new FileInputStream(inputFile);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", inputFile.getName(), "multipart/form-data",inputStream);

        /* Testing if the file uploads successfully */
        mvc.perform(MockMvcRequestBuilders.fileUpload("/validate-and-upload-listing")
                .file(mockMultipartFile))
                .andExpect(status().is3xxRedirection());

    }


    protected void uploadContactFile() throws Exception {

        final File inputFile = resourceLoader.getResource("classpath:contacts_light.csv").getFile();
        FileInputStream inputStream = new FileInputStream(inputFile);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", inputFile.getName(), "multipart/form-data",inputStream);

        /* Testing if the file uploads successfully */
        mvc.perform(MockMvcRequestBuilders.fileUpload("/validate-and-upload-contact")
                .file(mockMultipartFile))
                .andExpect(status().is3xxRedirection());

    }
}
