package com.Solutions.assignment;
import java.io.IOException;


import org.testng.annotations.Test;

import com.generic.pdfreaderMethod.PDFReaderMethod;



public class PDFReaderTest extends PDFReaderMethod {
	
	
	
	@Test()
	
	public void pdfReadRunner() throws IOException {	
		readPDF();		
	}
	

 
}