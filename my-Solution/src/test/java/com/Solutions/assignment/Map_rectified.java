package com.Solutions.assignment;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class Map_rectified{
	
	/* Initializing the variables */

    public static WebDriver driver;
    public static Properties prop;
	public static String tableTitle;
	public static HashMap<String, ArrayList<String>> locations;
	/*
	 * Initializing the Data Structures
	 */
	public static ArrayList<String> KeyValue;
	public static ArrayList<String> AttributeValue;
	
	@Test
	public static void primaryExecutor() throws InterruptedException, IOException {
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);

		/* Invoking the Browser */
		prop = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/config.properties");
		prop.load(ip);
		driver = new ChromeDriver(options);
		System.setProperty("webdriver.chrome.driver",("user.dir") + "/src/main/java/com/crm/qa/config/chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to(prop.getProperty("URL"));
		driver.manage().window().maximize();	
		locations = new HashMap<String, ArrayList<String>>();
		AttributeValue = new ArrayList<String>();
		KeyValue = new ArrayList<String>();
		/*
		 * Navigating to the Maps
		 * 
		 */
		driver.findElement(By.xpath("//a[@href = 'https://www.bizjournals.com/milwaukee/maps/project-watch'][1]")).click();
		/*
		 * Accessing the Section where the Map is developed on the Page 
		 * 
		 */	
		WebElement Element = driver.findElement(By.xpath("//iframe[@class='cw-map']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", Element);
		driver.switchTo().frame(Element);
		int imagenos = driver.findElements(By.cssSelector("#milwaukee_7663_layer > image")).size();
		System.out.println(imagenos);

		/*
		 * Navigating through all the Locations and Extracting the Data to Excel
		 * 
		 */
		
		try {

			for (int i = 2; i < imagenos; i++) {

				WebElement image = driver.findElement(By.cssSelector("#milwaukee_7663_layer > image:nth-child(" + i + ")"));
				Actions act = new Actions(driver);
				act.moveToElement(image).click().build().perform();
				System.out.println(tableTitle);
				int rowcount = driver.findElements(By.xpath("//div[contains(@id,'esri_dijit__PopupRenderer')]/div/div[3]/table/tbody/tr"))
						.size();
				System.out.println(rowcount);
												
				for (int j = 1; j <= rowcount; j++) {
					String Value = driver.findElement(By.xpath(
							"(//div[contains(@id,'esri_dijit__PopupRenderer')]//following::tr)[" + j + "]/td[2]"))
							.getText();

					System.out.println(Value);
                    KeyValue.add(Value);
				}
							
				Actions act1 = new Actions(driver);
				WebElement anywhere = driver.findElement(By.xpath("//img[@id ='map_World_Street_Map_8421_tile_10_2_0']"));
				act1.moveToElement(anywhere).click().build().perform();
			}
			locations.put(tableTitle, KeyValue);
		} catch (Exception e) {
			System.out.println(e);
	}	
	}
	
	
	@Test
	public void outPutPrinter() throws IOException {	
		/*
		 * Extracting the Output to the Excel File
		 * 
		 * 
		 */	
		HashmapReturn mp = new HashmapReturn (locations);
		mp.outputValues();
	}
	
		
	@AfterTest
	public void quiteBrowser() {
	driver.quit();
	}
	
	
	







		
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


}
