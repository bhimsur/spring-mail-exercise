package com.example.springemail.util;

import com.lowagie.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@Slf4j
public class FileUtil {

    public static void createHtmlFile(String htmlTemplate, String directory) {
        PrintWriter writer = null;
        try {
            String fileName = new ClassPathResource(directory.concat("/mutation.html")).getPath();
            File file = new File(fileName);
            file.getParentFile().mkdir();
            writer = new PrintWriter(file);
            writer.write(htmlTemplate);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }

    /**
     * Create html file template
     * @param htmlTemplate html to be write
     * @param fileLocation full path + file name
     * @throws IOException
     */
    public static void createHtmlFileTemplate(String htmlTemplate, String fileLocation) throws IOException {
        if (fileLocation == null) {
            throw new IOException("Location empty");
        }

        PrintWriter writer = null;
        try {
            String fileName = new ClassPathResource(fileLocation).getPath();
            File file = new File(fileName);
            file.getParentFile().mkdir();
            writer = new PrintWriter(file);
            if (htmlTemplate != null) {
                writer.write(htmlTemplate);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }

    /**
     * create pdf file from html template
     * @param fileLocation folder pdf will generate
     * @param fileName file name pdf
     * @param pdfData data to be written to pdf
     * @return byte[] pdf data
     */
    public static byte[] createPdfFile(String fileLocation, String fileName, String pdfData) {
        OutputStream outputStream = null;
        CustomFileInputStream fileInputStream = null;

        try {
            String location = fileLocation.concat(fileName);
            File file = new File(location);
            outputStream = new FileOutputStream(file);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(pdfData);
            renderer.layout();
            renderer.createPDF(outputStream);

            fileInputStream = new CustomFileInputStream(file);
            return fileInputStream.readAllBytes();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                outputStream.close();
                fileInputStream.close();
            } catch (Exception e) {}
        }
        return null;
    }
}
