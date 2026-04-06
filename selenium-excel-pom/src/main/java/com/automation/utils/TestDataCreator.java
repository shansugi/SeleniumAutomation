package com.automation.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Run this once (right-click → Run As → Java Application in Eclipse)
 * to generate src/test/resources/testdata/testdata.xlsx
 */
public class TestDataCreator {

    public static void main(String[] args) throws IOException {
        String path = "src/test/resources/testdata/testdata.xlsx";
        Files.createDirectories(Paths.get("src/test/resources/testdata"));

        try (Workbook wb = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(path)) {

            Sheet sheet = wb.createSheet("Login");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("username");
            header.createCell(1).setCellValue("password");
            header.createCell(2).setCellValue("expectedError");

            // Row 1 — valid login
            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("standard_user");
            row1.createCell(1).setCellValue("secret_sauce");
            row1.createCell(2).setCellValue("");

            // Row 2 — wrong password
            Row row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("standard_user");
            row2.createCell(1).setCellValue("wrong_password");
            row2.createCell(2).setCellValue(
                    "Epic sadface: Username and password do not match any user in this service");

            // Row 3 — locked out user
            Row row3 = sheet.createRow(3);
            row3.createCell(0).setCellValue("locked_out_user");
            row3.createCell(1).setCellValue("secret_sauce");
            row3.createCell(2).setCellValue(
                    "Epic sadface: Sorry, this user has been locked out.");

            // Row 4 — invalid user
            Row row4 = sheet.createRow(4);
            row4.createCell(0).setCellValue("invalid_user");
            row4.createCell(1).setCellValue("secret_sauce");
            row4.createCell(2).setCellValue(
                    "Epic sadface: Username and password do not match any user in this service");

            // Auto-size columns
            for (int i = 0; i < 3; i++) sheet.autoSizeColumn(i);

            wb.write(fos);
        }

        System.out.println("testdata.xlsx created at: " + path);
    }
}
