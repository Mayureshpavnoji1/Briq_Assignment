package com.Solutions.assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HashmapReturn  {
	
	static 	XSSFWorkbook wb;
	static XSSFSheet sheet;
	static Row row;
	static Cell cell;
	static int RowLastAppend;
	static 	Cell cell2;
	
	public HashmapReturn(HashMap<String, ArrayList<String>> locations)  throws IOException {
		
		FileInputStream fis = new FileInputStream("./src/main/java/com/outputFiles/repository/LocationDetails.xlsx");
		wb = new XSSFWorkbook(fis);
		int RowLastAppend = 0;
		for (String tableHeader : locations.keySet()) {
			
			/* Taking the Latest Row Number from the Excel */
			row = sheet.createRow(++RowLastAppend);
			int cellvalue = 0;
			cell = row.createCell(cellvalue);
			cell.setCellValue(tableHeader);		
			ArrayList<String> list = (ArrayList<String>) locations.get(tableHeader);		
			for (String navigator : list) {
				cell2 = row.createCell(++cellvalue);
				cell2.setCellValue(navigator);
			}	
			tableHeader = tableHeader+1;
	       }		
	      }
	
		public void outputValues () throws IOException {		
			FileOutputStream out = new FileOutputStream(new File("./src/main/java/com/outputFiles/repository/LocationDetails.xlsx"));
			wb.write(out);
			out.close();
		}
	   }


		
	


	
