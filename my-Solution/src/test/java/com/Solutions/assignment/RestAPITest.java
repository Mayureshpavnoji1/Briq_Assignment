package com.Solutions.assignment;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAPITest {

	static WebDriver driver;

	@Test
	public void getEndPointData() {

		RestAssured.baseURI = "https://data.sfgov.org";
		RequestSpecification httprequest = RestAssured.given();
		Response response = httprequest.request(Method.GET, "/resource/p4e4-a5a7.json");
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);

	}

}
