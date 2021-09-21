package com.example.springemail.service;

import com.example.springemail.constant.FileData;
import com.example.springemail.model.MailRequest;
import com.example.springemail.model.User;
import com.example.springemail.util.ThymeleafUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private static final MailRequest mailRequest = MailRequest
            .builder()
            .accountNumber("14045")
            .accountOwnerName("subakir")
            .amountWords("Seratus Juta Rupiah")
            .aroType("ARO")
            .balance("Rp. 100.000.000,00")
            .createDate("21 Juni 2021")
            .currency("IDR")
            .interestRate("6%")
            .timePeriod("3 Bulan")
            .build();


    private String html_template = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
            "<head>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>BTN</h1>\n" +
            "<h3>Pembukaan Deposito</h3>\n" +
            "<p th:text=\"${accountOwnerName}\"></p>\n" +
            "<p th:text=\"${accountNumber}\"></p>\n" +
            "<p th:text=\"${currency}\"></p>\n" +
            "<p th:text=\"${interestRate}\"></p>\n" +
            "<p th:text=\"${aroType}\"></p>\n" +
            "<p th:text=\"${timePeriod}\"></p>\n" +
            "<p th:text=\"${createDate}\"></p>\n" +
            "<p th:text=\"${maturityDate}\"></p>\n" +
            "<p th:text=\"${balance}\"></p>\n" +
            "<p th:text=\"${amountWords}\"></p>\n" +
            "</body>\n" +
            "</html>";

    public void sendEmail(User user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmailAddress());
        mail.setSubject(user.getSubject());
        mail.setText(user.getBody());
        javaMailSender.send(mail);
    }

    public void sendEmailWithAttachment(User user) throws MailException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(user.getEmailAddress());
        helper.setSubject(user.getSubject());
        helper.setText(user.getBody());

        ClassPathResource resource = new ClassPathResource("static/Attachment.pdf");
        helper.addAttachment(resource.getFilename(), resource);
        javaMailSender.send(mimeMessage);
    }

    public ResponseEntity<ByteArrayResource> downloadPdf(MailRequest request) throws IOException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=advice.pdf");

            ByteArrayResource resource = new ByteArrayResource(constructPdfByteArray(request));
            return new ResponseEntity<ByteArrayResource>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private byte[] constructPdfByteArray(MailRequest request) throws IOException {
        Map<String, Object> mapRequest = ThymeleafUtil.convertObjectToMap(request);
        Map<FileData, String> fileDataStringMap = new HashMap<>();
        fileDataStringMap.put(FileData.DIRECTORY, "file-handler/");
        fileDataStringMap.put(FileData.HTML_FILE_NAME, "advice.html");
        fileDataStringMap.put(FileData.HTML_TEMPLATE, html_template);
        fileDataStringMap.put(FileData.PDF_FILE_NAME, "advice.pdf");

        GenerateFileService service = new GenerateFileService();
        return service.createPdfFile(mapRequest, fileDataStringMap);
    }

    public void sendMailWithByteArray(User user) throws MessagingException, IOException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            InputStreamSource fileStream = new ByteArrayResource(constructPdfByteArray(mailRequest));
            helper.setTo(user.getEmailAddress());
            helper.setSubject(user.getSubject());
            helper.setText(user.getBody());
            helper.addAttachment("advice.pdf", fileStream);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
