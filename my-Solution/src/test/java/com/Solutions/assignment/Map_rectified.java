package com.Solutions.assignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Map_rectified {

	

	@Test

	public static void primaryExecutor() throws InterruptedException, IOException {

		/* Invoking the Browser */

		WebDriver driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver",("user.dir") + "/src/main/java/com/crm/qa/config/chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("https://www.bizjournals.com/milwaukee/feature/crane-watch");

		/* Initiatizing the variables */
		Cell cell2;
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Locations");
		Row row;
		Cell cell;

		/*
		 * Initializing the Data Structures
		 */
		ArrayList<String> KeyValue = new ArrayList<String>();
		ArrayList<String> AttributeValue = new ArrayList<String>();
		HashMap<String, ArrayList<String>> locations = new HashMap<String, ArrayList<String>>();

		driver.manage().window().maximize();

		/*
		 * Navigating to the Maps
		 * 
		 */

		driver.findElement(By.xpath("//a[@href = 'https://www.bizjournals.com/milwaukee/maps/project-watch'][1]"))
				.click();

		/* Accessing the Section where the Map is developed on the Page */
		WebElement Element = driver.findElement(By.xpath("//iframe[@class='cw-map']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", Element);

		driver.switchTo().frame(Element);
		int imagenos = driver.findElements(By.cssSelector("#milwaukee_7663_layer > image")).size();
		System.out.println(imagenos);

		/* Taking the Latest Row Number from the Excel */

		int RowLastAppend = sheet.getLastRowNum();

		/*
		 * Navigating through all the Locations and Extracting the Data to Excel
		 * 
		 */
		try {

			for (int i = 2; i < imagenos; i++) {

				WebElement image = driver
						.findElement(By.cssSelector("#milwaukee_7663_layer > image:nth-child(" + i + ")"));

				Actions act = new Actions(driver);
				act.moveToElement(image).click().build().perform();

				String tableTitle = driver.findElement(By.xpath("//div[@class='header']")).getText();
				System.out.println(tableTitle);
				int rowcount = driver
						.findElements(
								By.xpath("//div[contains(@id,'esri_dijit__PopupRenderer')]/div/div[3]/table/tbody/tr"))
						.size();
				System.out.println(rowcount);

				for (int k = 1; k <= rowcount; k++) {
					String Label = driver.findElement(By.xpath(
							"(//div[contains(@id,'esri_dijit__PopupRenderer')]//following::tr)[" + k + "]/td[1]"))
							.getText();
					AttributeValue.add(Label);

				}

				for (int j = 1; j <= rowcount; j++) {

					String Value = driver.findElement(By.xpath(
							"(//div[contains(@id,'esri_dijit__PopupRenderer')]//following::tr)[" + j + "]/td[2]"))
							.getText();

					System.out.println(Value);

					KeyValue.add(Value);

				}
				locations.put(tableTitle, KeyValue);

				for (String tableHeader : locations.keySet()) {
					row = sheet.createRow(++RowLastAppend);
					int cellvalue = 0;
					cell = row.createCell(cellvalue);
					cell.setCellValue(tableHeader);
					ArrayList<String> list = (ArrayList<String>) locations.get(tableHeader);
					for (String navigator : list) {
						cell2 = row.createCell(++cellvalue);
						cell2.setCellValue(navigator);
					}

				}

				Actions act1 = new Actions(driver);
				WebElement anywhere = driver
						.findElement(By.xpath("//img[@id ='map_World_Street_Map_8421_tile_10_2_0']"));
				act1.moveToElement(anywhere).click().build().perform();

			}

			/*
			 * int cellValue1 = 0; row = sheet.createRow(0); for(String navigator1:
			 * AttributeValue){ cell3 = row.createCell(++cellValue1);
			 * cell3.setCellValue(navigator1); }
			 */

		} catch (Exception e) {
			System.out.println(e);
		}

		/*
		 * Extracting the Output to the Excel File
		 * 
		 * 
		 */
		finally {

			FileOutputStream out = new FileOutputStream(
					new File("./src/main/java/com/outputFiles/repository/LocationDetails.xlsx"));

			wb.write(out);
			out.close();
		}

	}

}
