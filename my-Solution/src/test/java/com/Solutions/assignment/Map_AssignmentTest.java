package com.Solutions.assignment;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class Map_AssignmentTest {
	static WebDriver driver;
	static int i;
	static int j;

	public void insertExcel() throws IOException {

		WebElement table = driver
				.findElement(By.xpath("#map_root > div.esriPopup.esriPopupVisible > div.esriPopupWrapper"));
		List<WebElement> rows = table.findElements(By.xpath("//table[@class  = 'attrTable']/tbody/tr"));
		int row_count = rows.size();

		System.out.println(row_count);

		FileInputStream fis = new FileInputStream("/Mayuresh/src/Excel_Writer.xlsx");
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheetAt(1);

		String Firtcol_rows1 = "//table[@class  = 'attrTable']/tbody/tr[";
		String Firstcol_rows1 = "]/td[1]";

		String Secondcol_row1 = "//table[@class  = 'attrTable']/tbody/tr[";
		String Secondcol_row2 = "]/td[2]";

		j = 1;

		String TableHeader = driver
				.findElement(By.cssSelector("#esri_dijit__PopupRenderer_3 > div.mainSection > div.header")).getText();
		sh.getRow(j).createCell(1).setCellValue(TableHeader);

		for (i = 1; i <= row_count; i++) {

			String FirstColCompletePAth = Firtcol_rows1 + i + Firstcol_rows1;
			String SeconmdColCompletePAth = Secondcol_row1 + i + Secondcol_row2;

			String Attributes = driver.findElement(By.xpath(FirstColCompletePAth)).getText();
			String Values = driver.findElement(By.xpath(SeconmdColCompletePAth)).getText();
			sh.getRow(i + 1).createCell(1).setCellValue(Attributes);
			sh.getRow(i + 1).createCell(2).setCellValue(Values);

		}
		j = i + 1;
		i = i + 2;

	}

	@Test
	public static void readMap() throws InterruptedException, IOException {

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to("https://www.bizjournals.com/milwaukee/feature/crane-watch");
		driver.manage().window().maximize();

		driver.findElement(By.xpath("//a[@href = 'https://www.bizjournals.com/milwaukee/maps/project-watch'][1]"))
				.click();

		Thread.sleep(10000);
		WebElement Element = driver.findElement(By.xpath(
				"//iframe[@src = 'https://acbj.maps.arcgis.com/apps/webappviewer/index.html?id=7cb649b4f47e461497cc32e663734e97']"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		// This will scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);

		driver.switchTo().frame(Element);

		Thread.sleep(10000);
		driver.findElement(By.cssSelector("#milwaukee_7663_layer > image:nth-child(31)")).click();

		Map_AssignmentTest a = new Map_AssignmentTest();

		a.insertExcel();

	

	}

	@AfterTest

	public void closeBrowser() {

		driver.quit();
	}

}
