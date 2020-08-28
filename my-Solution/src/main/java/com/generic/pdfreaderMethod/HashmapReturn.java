package com.generic.pdfreaderMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class HashmapReturn  {
	static 	XSSFWorkbook wb;
	static XSSFSheet sheet;
	
	static Row row;
	static Cell cell;
	static int RowLastAppend;
	static 	Cell cell2;
	
	
	@Test
	public static void outputValues(HashMap<String, ArrayList<String>> locations) {
	
	

	
		
		for (String tableHeader : locations.keySet()) {
			Row row = sheet.createRow(++RowLastAppend);
			int cellvalue = 0;
			cell = row.createCell(cellvalue);
			cell.setCellValue(tableHeader);
			ArrayList<String> list = (ArrayList<String>) locations.get(tableHeader);
			for (String navigator : list) {
				cell2 = row.createCell(++cellvalue);
				cell2.setCellValue(navigator);
			}

		}
	}

}

	
