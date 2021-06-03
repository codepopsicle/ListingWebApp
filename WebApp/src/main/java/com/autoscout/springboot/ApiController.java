package com.autoscout.springboot;

import com.autoscout.springboot.service.ReportingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiController {


	private ReportingService reportingService;

	@Autowired
	public ApiController(ReportingService reportingService){
		this.reportingService = reportingService;
	}

	@ApiOperation(value = "API greeting", notes = "")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval",
					response = String.class) })
	@RequestMapping("/welcome")
	public String index() {
		return "Welcome to the AutoScout24 Listing Service !";
	}

	@ApiOperation(value = "API to retrieve exchange rate for currency pair with EUR as reference", notes = "", nickname = "reference")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval",
					response = Map.class) })
	@RequestMapping(path = "/average", method = RequestMethod.GET)
	public Map getAverageListingPricePerSellerType() {

		return reportingService.getAverageListingPricePerSellerType();
	}


	@ApiOperation(value = "API to retrieve exchange rate for currency pair with base currency as reference", notes = "", nickname = "exchange")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval",
					response = Map.class) })
	@RequestMapping(path = "/distribution", method = RequestMethod.GET)
	public Map getPercentageDistribution() {

		return reportingService.getPercentageDistribution();
	}

	@ApiOperation(value = "API to retrieve a map of all available currencies and how many times they were accessed", notes = "", nickname = "currencies")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval",
					response = Map.class) })
	@RequestMapping(path = "/top30", method = RequestMethod.GET)
	public String getTop30MostFrequentlyContactedListings() {

		return reportingService.getTop30MostFrequentlyContactedListings();
	}

	/*@ApiOperation(value = "API to convert an amount of one currency into another", notes = "", nickname = "convert")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval",
					response = BigDecimal.class) })
	@RequestMapping(path = "/convert", method = RequestMethod.POST)
	public BigDecimal convertCurrencyAmount(@RequestBody String inputJSON) throws IllegalArgumentException, JSONException, RatesNotInitializedException{

		final String baseCurrency = new JSONObject(inputJSON).get("base").toString();
		final String notionalCurrency = new JSONObject(inputJSON).get("notional").toString();
		final BigDecimal baseAmount = new BigDecimal(new JSONObject(inputJSON).get("base_amount").toString());

		return exchangeRateService.convertCurrencyAmount(baseCurrency, notionalCurrency, baseAmount);
	}

	@ApiOperation(value = "API to fetch the graph of a particular currency from the ECB website", notes = "", nickname = "graph")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval",
					response = URL.class) })
	@RequestMapping(path = "/graph", method = RequestMethod.POST)
	public URL getGraphURL(@RequestBody String inputJSON) throws MalformedURLException, JSONException{

		final String currency = new JSONObject(inputJSON).get("currency").toString();

		return exchangeRateService.getGraphURL(currency);
	}*/

}
