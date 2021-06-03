package com.autoscout.springboot;

import com.autoscout.springboot.repository.ContactRepository;
import com.autoscout.springboot.repository.ListingRepository;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc
public class ApiControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webAppContext;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private ListingRepository listingRepository;

	@Autowired
	private ContactRepository contactRepository;


	@Test
	public void uploadListingFileTest() throws Exception {

		final File inputFile = resourceLoader.getResource("classpath:listings.csv").getFile();
		final FileInputStream inputStream = new FileInputStream(inputFile);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", inputFile.getName(), "multipart/form-data",inputStream);

		/* Testing if the file uploads successfully */
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/validate-and-upload-listing")
				.file(mockMultipartFile))
				.andExpect(status().is3xxRedirection());

		/* Testing if the number of records insert into the database are correct */
		Assert.assertEquals(300, listingRepository.findAll().size());

	}

	@Test
	public void uploadContactFileTest() throws Exception {

		final File inputFile = resourceLoader.getResource("classpath:contacts_light.csv").getFile();
		FileInputStream inputStream = new FileInputStream(inputFile);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", inputFile.getName(), "multipart/form-data",inputStream);

		/* Testing if the file uploads successfully */
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/validate-and-upload-contact")
				.file(mockMultipartFile))
				.andExpect(status().is3xxRedirection());

		/* Testing if the number of records insert into the database are correct */
		Assert.assertEquals(599, contactRepository.findAll().size());

	}

	@Test
	public void averagePriceTest() throws Exception {

		final File outputFile = resourceLoader.getResource("classpath:average-price.json").getFile();
		final String outputString = new String(Files.readAllBytes(outputFile.toPath()));

		MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/average"))
				.andExpect(status().isOk())
				.andReturn();

		Assert.assertEquals(response.getResponse().getContentAsString().replaceAll("[^a-zA-Z0-9]", "").trim(),
				outputString.replaceAll("[^a-zA-Z0-9]", "").trim());

	}

	/*@Test
	public void percentageDistributionTest() throws Exception {

		final File outputFile = resourceLoader.getResource("classpath:percentage-distribution.json").getFile();
		final String outputString = new String(Files.readAllBytes(outputFile.toPath()));

		System.out.println("OP " + outputString);

		MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/distribution"))
				.andExpect(status().isOk())
				.andReturn();

		Assert.assertEquals(response.getResponse().getContentAsString().replaceAll("[^a-zA-Z0-9]", "").trim(),
				outputString.replaceAll("[^a-zA-Z0-9]", ""));
		System.out.println("Per " + response.getResponse().getContentAsString());

	}*/

	@Test
	public void top30ContactTest() throws Exception {


		MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/top30"))
				.andExpect(status().isOk())
				.andReturn();

		Assert.assertEquals(response.getResponse().getContentAsString().replaceAll("[^a-zA-Z0-9]", "").trim(),
				"2396905");

	}

	/*@Test
	public void monthlyReportTest() throws Exception {

		final File outputFile = resourceLoader.getResource("classpath:monthly-report.json").getFile();
		final String outputString = new String(Files.readAllBytes(outputFile.toPath()));

		MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/monthly-report"))
				.andExpect(status().isOk())
				.andReturn();

		Assert.assertEquals(response.getResponse().getContentAsString().replaceAll("[^a-zA-Z0-9]", "").trim(),
				outputString.replaceAll("[^a-zA-Z0-9]", "").trim());

	}*/


}
