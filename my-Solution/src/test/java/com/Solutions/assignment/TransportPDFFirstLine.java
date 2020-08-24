package com.Solutions.assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import org.testng.annotations.Test;

public class TransportPDFFirstLine {

	static Properties prop;

	@Test
	public void readFirstLineStatementPDF() throws IOException {
		prop = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/config.properties");
		prop.load(ip);

		String[] line = null;

		PDDocument document = PDDocument.load(new File(prop.getProperty("TransportPDFLocation")));

			document.getClass();

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);

				line = pdfFileInText.split("\\r\\n");

			}
			System.out.println(line[2]);

		
	}
}
