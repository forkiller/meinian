package com.atguigu;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author steve
 * @since 2021-07-03 9:20
 */

@SuppressWarnings("all")
public class POITest {

    @Test
    public void testRead() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("d:\\1.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);

        Object value = null;
        for (Row row : sheet) {

            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case 0:
                        value = cell.getNumericCellValue();
                        break;
                    case 1:
                        value = cell.getStringCellValue();
                        break;
                    case 2:
                        value = cell.getCellFormula();
                        break;
                    case 3:
                        value = cell.getStringCellValue();
                        break;
                    case 4:
                        value = cell.getBooleanCellValue();
                        break;
                    case 5:
                        value = cell.getErrorCellValue();
                }

                System.out.println("value = " + value);
            }
        }

        workbook.close();
    }


    @Test
    public void testWrite() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("salary");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("工资");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("001");
        row1.createCell(1).setCellValue("诸军波");
        row1.createCell(2).setCellValue("18000");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("002");
        row2.createCell(1).setCellValue("田文鑫");
        row2.createCell(2).setCellValue("15000");

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\2.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
        workbook.close();

    }

    @Test
    public void testReadLargeFile() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook("d:\\Users\\pro13\\Documents\\minPowerQuery爬取205页股票信息耗时35.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(1);
        Object value = null;
        for (Row row : sheet) {

            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case 0:
                        value = cell.getNumericCellValue();
                        break;
                    case 1:
                        value = cell.getStringCellValue();
                        break;
                    case 2:
                        value = cell.getCellFormula();
                        break;
                    case 3:
                        value = cell.getStringCellValue();
                        break;
                    case 4:
                        value = cell.getBooleanCellValue();
                        break;
                    case 5:
                        value = cell.getErrorCellValue();
                }
                System.out.println("value = " + value);
            }

        }

    }

    // 导出excel，获取最后一行
    @Test
    public void exportExcel_lastRow() throws Exception {
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\1.xlsx");
        // 获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheet = workbook.getSheetAt(0);
        // 获取当前工作表最后一行的行号，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            // 根据行号获取行对象
            XSSFRow row = sheet.getRow(i);
            // 再获取单元格对象
            short lastCellNum = row.getLastCellNum();

            Object value = null;
            for (short j = 0; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                // 获取单元格对象的值
                switch (cell.getCellType()) {
                    case 0:
                        value = cell.getNumericCellValue();
                        break;
                    case 1:
                        value = cell.getStringCellValue();
                        break;
                    case 2:
                        value = cell.getCellFormula();
                        break;
                    case 3:
                        value = cell.getStringCellValue();
                        break;
                    case 4:
                        value = cell.getBooleanCellValue();
                        break;
                    case 5:
                        value = cell.getErrorCellValue();
                }

                System.out.println("value = " + value);
            }
        }
        workbook.close();
    }

    @Test
    public void exportExcel_lastRow2() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\hello.xlsx");
        //获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取当前工作表最后一行的行号，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<=lastRowNum;i++){
            //根据行号获取行对象
            XSSFRow row = sheet.getRow(i);
            // 再获取单元格对象
            short lastCellNum = row.getLastCellNum();
            for(short j=0;j<lastCellNum;j++){
                // 获取单元格对象的值
                String value = row.getCell(j).getStringCellValue();
                System.out.print(value + "\t");
            }
        }
        workbook.close();
    }


}
