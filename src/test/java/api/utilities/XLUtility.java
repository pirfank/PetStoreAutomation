package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {

    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;

    String path;

    // ✅ Constructor (accepts file path)
    public XLUtility(String path) {
        this.path = path;
    }

    // ✅ Returns row count of a sheet
    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        int rowcount = sheet.getLastRowNum(); // Zero-based

        workbook.close();
        fi.close();
        return rowcount;
    }

    // ✅ Returns column count in a row
    public int getCellCount(String sheetName, int rowNum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(rowNum);
        int cellcount = row.getLastCellNum();

        workbook.close();
        fi.close();
        return cellcount;
    }

    // ✅ Returns data from a specific cell
    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        String data;

        try {
            data = cell.toString();
        } catch (Exception e) {
            data = "";
        }

        workbook.close();
        fi.close();
        return data;
    }

    // ✅ Write data into a cell
    public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
        File file = new File(path);

        // If file does not exist, create a blank workbook
        if (!file.exists()) {
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
            fo.close();
        }

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }

        row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }

        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        workbook.write(fo);

        workbook.close();
        fi.close();
        fo.close();
    }

    // ✅ Fill cell with green color (Pass)
    public void fillGreenColor(String sheetName, int rowNum, int colNum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor((short) 17); // Light green
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        cell.setCellStyle(style);

        fo = new FileOutputStream(path);
        workbook.write(fo);

        workbook.close();
        fi.close();
        fo.close();
    }

    // ✅ Fill cell with red color (Fail)
    public void fillRedColor(String sheetName, int rowNum, int colNum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor((short) 10); // Red
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        cell.setCellStyle(style);

        fo = new FileOutputStream(path);
        workbook.write(fo);

        workbook.close();
        fi.close();
        fo.close();
    }
}

