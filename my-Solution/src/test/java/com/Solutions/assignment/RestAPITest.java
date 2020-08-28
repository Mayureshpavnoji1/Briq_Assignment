package com.Solutions.assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAPITest {

	static WebDriver driver;
	static Cell cell;
	static Row row;
	Properties prop;

	@Test
	public void getEndPointData() throws IOException {

		RestAssured.baseURI = "https://data.sfgov.org";
		RequestSpecification httprequest = RestAssured.given();
		Response response = httprequest.request(Method.GET, "/resource/p4e4-a5a7.json");
		String responseBody = response.getBody().asString();
		// System.out.println(responseBody);

		XSSFWorkbook workbook = new XSSFWorkbook();

		prop = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/config.properties");
		prop.load(ip);

		XSSFSheet sheet = workbook.createSheet("APIInfo");

		File json = new File("./src/main/java/com/outputFiles/repository/Restoutput.txt");
		FileWriter fw = new FileWriter(json);
		@SuppressWarnings("resource")
		PrintWriter p = new PrintWriter(fw);
		p.print(responseBody);

		File json1 = new File("./src/main/java/com/outputFiles/repository/Restoutput.txt");

		ArrayList<String> a = new ArrayList<String>();

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(json1);
		while (sc.hasNextLine()) {

			a.add(sc.nextLine());

		}

		int cellnum = 0;
		int rownum = sheet.getLastRowNum();

		for (String c : a) {
			System.out.println(c);
			String b[] = c.split(":");
			row = sheet.createRow(rownum++);
			for (String d : b) {

				cell = row.createCell(cellnum++);
				cell.setCellValue(d);
			}
			cellnum = 0;

		}

		FileOutputStream out = new FileOutputStream(prop.getProperty("RestAPIJsonExcelData"));

		workbook.write(out);
		out.close();

	}

}
