import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	

	public ExcelUtility(String path) {
		
		this.path = path;
		try {
			
			this.fis = new FileInputStream(path);
			this.workbook = new XSSFWorkbook(this.fis);
			
			// always keep the 1st sheet at 0th position with the list of all the test cases
			this.sheet = this.workbook.getSheetAt(0);
			this.fis.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public int getRowCount(String sheetName) {
		
		
		int index = this.workbook.getSheetIndex(sheetName);
		if (index == -1) {
			
			return 0;
		}

		this.sheet = this.workbook.getSheetAt(index);
		int number = this.sheet.getLastRowNum() + 1;
		return number;
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		
		try {
			
			if (rowNum <= 0) {
				
				return "";
			}

			int index = this.workbook.getSheetIndex(sheetName);
			
			int col_Num = -1;
			
			if (index == -1) {
				
				return "";
			}

			this.sheet = this.workbook.getSheetAt(index);

			this.row = this.sheet.getRow(0);
			
			for (int i = 0; i < this.row.getLastCellNum(); ++i) {
				
				if (!(this.row.getCell(i).getStringCellValue().trim().equals(colName.trim())))
					continue;
				col_Num = i;
			}

			if (col_Num == -1) {
				
				return "";
			}

			this.sheet = this.workbook.getSheetAt(index);

			this.row = this.sheet.getRow(rowNum - 1);
			
			if (this.row == null) {
				
				return "";
			}

			this.cell = this.row.getCell(col_Num);
			
			if (this.cell == null) {
				
				return "";
			}
			
//			System.out.println(this.cell.getCellType());
//			System.out.println(this.cell.getCellType().toString());

			if (this.cell.getCellType().toString().equalsIgnoreCase("STRING")) {
				
				return this.cell.getStringCellValue();
			}
			
			
			if (this.cell.getCellType().toString().equalsIgnoreCase("NUMERIC")) {
				
				int intCellText = (int) this.cell.getNumericCellValue();
				String cellText = Integer.toString(intCellText);
				
				return cellText;
				
			}
			
			if (this.cell.getCellType().toString().equalsIgnoreCase("BLANK")) {
				
				return "";
			}

			
			return String.valueOf(this.cell.getBooleanCellValue());
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "row " + rowNum + " or column " + colName + " does not exist in xls";
		
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		
		try {
			
			if (rowNum <= 0) {
				
				return "";
			}

			int index = this.workbook.getSheetIndex(sheetName);
			
			if (index == -1) {
				
				return "";
			}

			this.sheet = this.workbook.getSheetAt(index);
			this.row = this.sheet.getRow(rowNum - 1);
			
			if (this.row == null) {
				
				return "";
			}

			this.cell = this.row.getCell(colNum);
			
			if (this.cell == null) {
				
				return "";
			}
			
//			System.out.println(this.cell.getCellType());
//			System.out.println(this.cell.getCellType().toString());

			if (this.cell.getCellType().toString().equalsIgnoreCase("STRING")) {
				
				return this.cell.getStringCellValue();
			}
			
			
			if (this.cell.getCellType().toString().equalsIgnoreCase("NUMERIC")) {
				
				int intCellText = (int) this.cell.getNumericCellValue();
				String cellText = Integer.toString(intCellText);
				
				return cellText;
				
			}
			
			if (this.cell.getCellType().toString().equalsIgnoreCase("BLANK")) {
				
				return "";
			}

			
			return String.valueOf(this.cell.getBooleanCellValue());

			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "row " + rowNum + " or column " + colNum + " does not exist in xls";
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		
		try {
			
			this.fis = new FileInputStream(this.path);
			this.workbook = new XSSFWorkbook(this.fis);

			if (rowNum <= 0) {
				
				return false;
			}

			int index = this.workbook.getSheetIndex(sheetName);
			int colNum = -1;

			if (index == -1) {
				
				return false;
			}

			this.sheet = this.workbook.getSheetAt(index);
			this.row = this.sheet.getRow(0);
			
			for (int i = 0; i < this.row.getLastCellNum(); ++i) {
				
				if (!(this.row.getCell(i).getStringCellValue().trim().equals(colName)))
					continue;
				colNum = i;
			}

			if (colNum == -1) {
				
				return false;
			}

			this.sheet.autoSizeColumn(colNum);
			this.row = this.sheet.getRow(rowNum - 1);

			if (this.row == null) {
				
				this.row = this.sheet.createRow(rowNum - 1);
			}

			this.cell = this.row.getCell(colNum);

			if (this.cell == null) {
				
				this.cell = this.row.createCell(colNum);
			}

			this.cell.setCellValue(data);
			this.fileOut = new FileOutputStream(this.path);
			this.fileOut.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean isSheetExist(String sheetName) {
		
		int index = this.workbook.getSheetIndex(sheetName);
		
		if (index == -1) {
			
			index = this.workbook.getSheetIndex(sheetName.toUpperCase());

			return (index != -1);
		}

		return true;
	}

	public int getColumnCount(String sheetName) {
		
		if (!(isSheetExist(sheetName))) {
			
			return -1;
		}

		this.sheet = this.workbook.getSheet(sheetName);
		this.row = this.sheet.getRow(0);

		if (this.row == null) {
			return -1;
		}

		return this.row.getLastCellNum();
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		
		for (int i = 2; i <= getRowCount(sheetName); ++i) {
			
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				
				return i;
			}
		}

		return -1;
	}
	
	
}