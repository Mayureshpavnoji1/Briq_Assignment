package com.generic.pdfreaderMethod;

import java.io.File;
import java.io.FileInputStream;
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


public class PDFReaderMethod {
static  Properties prop ;
public void readPDF() throws IOException{

    XSSFWorkbook workbook = new XSSFWorkbook();
    
   
     
    
    XSSFSheet sheet = workbook.createSheet("Statement1 Data");
    
    
		prop = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/crm/qa/config/config.properties");
		prop.load(ip);
	

    ArrayList<String> al = new ArrayList<String>(); 
    PDDocument document = PDDocument.load(new File(prop.getProperty("PDFPathStatement")));
    	
    	

        document.getClass();

        if (!document.isEncrypted()) {

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);
            //System.out.println("Text:" + st);
            int cellnum = 0;
			int rownum = 0;
            String lines[] = pdfFileInText.split("\\r\\n");
           
            for (String line : lines) {
                System.out.println(line);
                Row row = sheet.createRow(rownum++); 
             
                al.add(line);
                
                Iterator<String> it = al.iterator();
                
                while (it.hasNext()){
					Object fr = it.next();
					{
                  
						   Cell cell = row.createCell(cellnum++);
						
                           if(fr instanceof String)
                                cell.setCellValue((String)fr);
                            else if(fr instanceof Integer)
                                cell.setCellValue((Integer)fr);
                        
                        }
                  
                   
                   cellnum = 0;
                    
               
                
                
            }

        }

    }
        
    FileOutputStream out = new FileOutputStream(prop.getProperty("ExcelCreationPathStatement"));
    workbook.write(out);
    out.close();
}
}
