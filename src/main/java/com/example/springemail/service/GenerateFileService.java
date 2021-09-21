package com.example.springemail.service;

import com.example.springemail.constant.FileData;
import com.example.springemail.util.FileUtil;
import com.example.springemail.util.ThymeleafUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class GenerateFileService {
    public byte[] createPdfFile(Object request, Map<FileData, String> fileData) throws IOException {
        FileUtil.createHtmlFileTemplate(fileData.get(FileData.HTML_TEMPLATE), fileData.get(FileData.DIRECTORY)
                .concat(fileData.get(FileData.HTML_FILE_NAME)));
        String templateData = ThymeleafUtil.processHtmlToString(fileData.get(FileData.DIRECTORY),
                fileData.get(FileData.HTML_FILE_NAME), request);
        return FileUtil.createPdfFile(fileData.get(FileData.DIRECTORY), fileData.get(FileData.PDF_FILE_NAME)
                , templateData);
    }

    public byte[] createPdfFile(Map<String, Object> request, Map<FileData, String> fileData) throws IOException {
        FileUtil.createHtmlFileTemplate(fileData.get(FileData.HTML_TEMPLATE), fileData.get(FileData.DIRECTORY)
                .concat(fileData.get(FileData.HTML_FILE_NAME)));
        String templateData = ThymeleafUtil.processHtmlToString(fileData.get(FileData.DIRECTORY),
                fileData.get(FileData.HTML_FILE_NAME), request);
        return FileUtil.createPdfFile(fileData.get(FileData.DIRECTORY), fileData.get(FileData.PDF_FILE_NAME)
                , templateData);
    }

    public byte[] createPdfFile(Context request, Map<FileData, String> fileData) throws IOException {
        FileUtil.createHtmlFileTemplate(fileData.get(FileData.HTML_TEMPLATE), fileData.get(FileData.DIRECTORY)
                .concat(fileData.get(FileData.HTML_FILE_NAME)));
        String templateData = ThymeleafUtil.processHtmlToString(fileData.get(FileData.DIRECTORY),
                fileData.get(FileData.HTML_FILE_NAME), request);
        return FileUtil.createPdfFile(fileData.get(FileData.DIRECTORY), fileData.get(FileData.PDF_FILE_NAME)
                , templateData);
    }
}
