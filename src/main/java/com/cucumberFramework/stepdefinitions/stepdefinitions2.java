package com.cucumberFramework.stepdefinitions;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.*;
import io.restassured.response.Response;
import java.io.*;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class stepdefinitions2
{
	
	private String jsonFilePath;
    private Response response;
    private Map<String, Object> requestBody = new HashMap<>();
    
    
	@Given("^json \"([^\"]*)\"$")
	public void json(String jsonPath) throws Throwable {
		
		 this.jsonFilePath = jsonPath;
		 
		 ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(new File(jsonPath));
	        
	        // Initialize request body with values from JSON file
	        requestBody.put("signerName", jsonNode.get("signerName").asText());
	        requestBody.put("moduleId", jsonNode.get("moduleId").asText());
	        requestBody.put("moduleResponseUrl", jsonNode.get("moduleResponseUrl").asText());
	        requestBody.put("docName", jsonNode.get("docName").asText());
	        
	        // Initialize query parameters from JSON file
	        JsonNode queryParamsNode = jsonNode.get("queryParams");
	        if (queryParamsNode != null) {
	            requestBody.put("txn", queryParamsNode.get("txn").asText());
	            requestBody.put("status", queryParamsNode.get("status").asText());
	        }

	}

	@When("^adding esign data \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
	public void adding_esign_data(String signerName, String moduleId, String moduleResponseUrl, String docName) throws Throwable {
	
		 requestBody.put("signerName", signerName);
	        requestBody.put("moduleId", moduleId);
	        requestBody.put("moduleResponseUrl", moduleResponseUrl);
	        requestBody.put("docName", docName);
	}

	@When("^query parameters are \"([^\"]*)\", \"([^\"]*)\"$")
	public void query_parameters_are(String txn, String status) throws Throwable {
		
		requestBody.put("txn", txn);
        requestBody.put("status", status);
	    
	}

	@Then("^status code should be (\\d+)$")
	public void status_code_should_be(int statusCode) throws Throwable {
		
		 response =  given()
	                .header("Content-Type", "application/json")
	                .body(requestBody)
	                .when()
	                .post("http://localhost:3000/");

	        // Assert the status code
	        Assert.assertEquals(statusCode, response.getStatusCode());
	    
	}
}
