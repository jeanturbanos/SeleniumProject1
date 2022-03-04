package utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenericMethod {

	
	 public static String[][] getData(String fileName,String sheetName) throws IOException{
		File file = new File(fileName);
		FileInputStream ips = new FileInputStream(file);
		Workbook wb = new XSSFWorkbook(ips);
		Sheet sh = wb.getSheet(sheetName);
		int rowNumber = sh.getLastRowNum()+1;
		int colNumber = sh.getRow(0).getLastCellNum();
		String[][] data = new String[rowNumber][colNumber];

		for(int i = 0;i<rowNumber;i++) {
			Row row = sh.getRow(i);
			for(int j=0;j<colNumber;j++) {
				Cell cell = row.getCell(j);
				String value = new DataFormatter().formatCellValue(cell);
				data[i][j] = value;

			}
		}
		wb.close();
		return data;
	}

	public void writeToExcel(String filePath, String sheetName,int rowNumber, int coloumnNumber, String value) throws EncryptedDocumentException, IOException {
		FileInputStream inputStream = new FileInputStream(new File(filePath));
		Workbook workbook = WorkbookFactory.create(inputStream);

		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.createCell(coloumnNumber);
		cell.setCellValue(value);
		if(value.equals("PASS")) {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
			cell.setCellStyle(style);  
		}
		if(value.equals("FAIL")) {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
			cell.setCellStyle(style);  
		}
		else if(value.equals("NA")) {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
			cell.setCellStyle(style);  
		}
		inputStream.close();
		FileOutputStream outputStream = new FileOutputStream(filePath);
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
