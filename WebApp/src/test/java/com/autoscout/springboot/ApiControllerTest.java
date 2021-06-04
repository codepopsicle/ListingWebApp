package com.autoscout.springboot;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApiControllerTest extends BaseController{

	@BeforeEach
	public void loadDB() throws Exception{

		uploadContactFile();
		uploadListingFile();

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

	@Test
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

	}

	@Test
	public void top30ContactTest() throws Exception {


		MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/top30"))
				.andExpect(status().isOk())
				.andReturn();

		Assert.assertEquals(response.getResponse().getContentAsString().replaceAll("[^a-zA-Z0-9]", "").trim(),
				"2396905");

	}

	@Test
	public void monthlyReportTest() throws Exception {

		final File outputFile = resourceLoader.getResource("classpath:monthly-report.json").getFile();
		final String outputString = new String(Files.readAllBytes(outputFile.toPath()));

		MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/top5monthly"))
				.andExpect(status().isOk())
				.andReturn();

		System.out.println("Resp " + response.getResponse().getContentAsString());

		Assert.assertEquals(response.getResponse().getContentAsString().replaceAll("[^a-zA-Z0-9]", "").trim(),
				outputString.replaceAll("[^a-zA-Z0-9]", "").trim());

	}


}
