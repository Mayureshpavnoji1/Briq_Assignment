package com.Solutions.assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ActiveLicenseTest {

	public static Properties prop;
	public static Row row;
	public static Cell cell;

	@Test
	public static void readActiveLicensePDF() throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		prop = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/config.properties");
		prop.load(ip);
		XSSFSheet sheet = workbook.createSheet("Active License PDF Data");
		PDDocument document = PDDocument.load(new File(prop.getProperty("ActiveLicenseDOC")));
	    document.getClass();

			if (!document.isEncrypted()) {
				PDFTextStripper tStripper = new PDFTextStripper();
                String pdfFileInText = tStripper.getText(document);
				int cellnum = 0;
				int rownum = 0;
				String lines[] = pdfFileInText.split("\\n");		
				for (String words : lines) {
					String a[] = words.split(" ");
					row = sheet.createRow(rownum++);
					for (String b : a) {

						cell = row.createCell(cellnum++);
						cell.setCellValue(b);

					}

					cellnum = 0;
				}

			}

			FileOutputStream out = new FileOutputStream(prop.getProperty("ExcelCreationPathActiveLicense"));
			workbook.write(out);
			out.close();

		}
}

	
