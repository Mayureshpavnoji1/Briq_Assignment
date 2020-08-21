package com.Solutions.assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ActiveLicenseTest {

	public static Properties prop;

	@Test
	public static void readActiveLicensePDF() throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		XSSFSheet sheet = workbook.createSheet("Active License PDF Data");
		ArrayList<String> al = new ArrayList<String>();
		try (PDDocument document = PDDocument.load(new File(prop.getProperty("ActiveLicenseDOC")))) {

			document.getClass();

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);
				// System.out.println("Text:" + st);
				int cellnum = 0;
				int rownum = 0;
				String lines[] = pdfFileInText.split("\\r\\n");

				for (String line : lines) {
					System.out.println(line);
					Row row = sheet.createRow(rownum++);
					Cell cell = row.createCell(cellnum++);
					al.add(line);

					Iterator<String> it = al.iterator();

					while (it.hasNext()) {
						Object fr = it.next();
						{

							if (fr instanceof String)
								cell.setCellValue((String) fr);
							else if (fr instanceof Integer)
								cell.setCellValue((Integer) fr);

						}

						cellnum = 0;

					}

				}

			}

			FileOutputStream out = new FileOutputStream(new File(prop.getProperty("ExcelCreationPathActiveLicense")));
			workbook.write(out);
			out.close();

		}

	}

}
