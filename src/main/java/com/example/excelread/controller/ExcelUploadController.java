package com.example.excelread.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
public class ExcelUploadController {

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA, produces = APPLICATION_JSON_VALUE)
    public <T> T uploadExcelFile(@RequestParam("file") MultipartFile multipartFile){
        return (T) new ArrayList<>();
    }
}
