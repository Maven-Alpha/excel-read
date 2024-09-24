package com.example.excelread.controller;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
public class ExcelUploadController {

    private static final Logger log = LoggerFactory.getLogger(ExcelUploadController.class);
    public static String EXCEL_FORMAT = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA, produces = APPLICATION_JSON_VALUE)
    public <T> T uploadExcelFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if(!Objects.equals(multipartFile.getContentType(), EXCEL_FORMAT)){
            throw new RuntimeException("File must be an excel file with xlsx extension!");
        }

        return parseExcel(multipartFile.getInputStream());
    }

    private <T> T parseExcel(InputStream inputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet("file");

        if(Objects.isNull(sheet)){
            throw new RuntimeException("Sheet is not found!");
        }

        List<String> list = new ArrayList<>();

        for(int i = 0; i < sheet.getPhysicalNumberOfRows(); i++){
            XSSFRow row = sheet.getRow(i);
            for(int j = 0; j < row.getPhysicalNumberOfCells(); j++){
                XSSFCell cell = row.getCell(j);
                list.add(cell.getStringCellValue());
                log.info("This is the value of cell: " + cell.getRawValue());
            }
        }

        return (T) list;
    }
}
