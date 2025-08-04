package utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {
    public static List<List<String>> readExcelData(String filePath, String sheetName) {
        List<List<String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet " + sheetName + " not found");

            // Tìm số cột tối đa để đảm bảo tất cả row có cùng số cột
            int maxColumns = 0;
            for (Row row : sheet) {
                if (row.getLastCellNum() > maxColumns) {
                    maxColumns = row.getLastCellNum();
                }
            }

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<String> rowData = new ArrayList<>();

                // Đọc tất cả cột, bao gồm cả ô trống
                for (int i = 0; i < maxColumns; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        rowData.add(""); // Thêm chuỗi rỗng cho ô null
                    } else {
                        cell.setCellType(CellType.STRING);
                        rowData.add(cell.getStringCellValue());
                    }
                }

                data.add(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public static List<String> getHeader(String filePath, String sheetName) {
        List<List<String>> data = readExcelData(filePath, sheetName);
        return data.isEmpty() ? new ArrayList<>() : data.get(0);
    }

//    public static List<List<String>> getDataWithoutHeader(String filePath, String sheetName) {
//        List<List<String>> data = readExcelData(filePath, sheetName);
//        return data.size() <= 1 ? new ArrayList<>() : data.subList(1, data.size());
//    }
public static List<List<String>> getDataWithoutHeader(String filePath, String sheetName) {
    List<List<String>> data = readExcelData(filePath, sheetName);
    if (data.size() <= 1) return new ArrayList<>();

    // Bỏ qua header + lọc các dòng trống hoàn toàn
    return data.subList(1, data.size()).stream()
            .filter(row -> row.stream().anyMatch(cell -> !cell.trim().isEmpty()))
            .toList();
}
}