package com.Solutions.assignment;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class LeadsTest {
	public static String nameRead;
	private static FileWriter file;
	private static Properties prop;

	@SuppressWarnings("unchecked")
	@Test
	public void readLeadsExcelFile() throws IOException {
		prop = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/config.properties");
		prop.load(ip);
		String Xslpath = prop.getProperty("LeadsExcel");
		FileInputStream fis = new FileInputStream(Xslpath);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheetAt(0);
		int rowCount = sh.getLastRowNum();
		System.out.println(rowCount);
		JSONObject jsonObject1 = new JSONObject();
	
		for (int i = 0; i < rowCount; i++) {
          Row rowValue = sh.getRow(0);
			for (int j = 0; j < rowValue.getLastCellNum(); j++) {		
				jsonObject1.put(rowValue.getCell(j).getStringCellValue(), sh.getRow(i+1).getCell(j).getStringCellValue());
					file = new FileWriter(prop.getProperty("Jasonoutput"));
					file.write(jsonObject1.toJSONString());
					file.close();
	     }
		}
		System.out.println("JSON file created: " + jsonObject1);
	  }
     }
