package com.example.springemail.controller;

import com.example.springemail.model.MailRequest;
import com.example.springemail.model.MailResponse;
import com.example.springemail.model.User;
import com.example.springemail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class RegistrationController {

    @Autowired
    private MailService mailService;

    @Autowired
    private User user;

    @PostMapping("/sendmail")
    public MailResponse send(@RequestBody User user) {
        try {
            mailService.sendEmail(user);
        } catch (MailException e) {
            return MailResponse.builder().isSuccess(false).build();
        }
        return MailResponse.builder().isSuccess(true).build();
    }

    @PostMapping("/sendemailattachment")
    public MailResponse sendWithAttachment(@RequestBody User user) throws MessagingException {
        try {
            mailService.sendEmailWithAttachment(user);
        } catch (MailException e) {
            return MailResponse.builder().isSuccess(false).build();
        }
        return MailResponse.builder().isSuccess(true).build();
    }

    @PostMapping("/downloadpdf")
    public ResponseEntity<ByteArrayResource> downloadPdf(@RequestBody MailRequest request) throws IOException {
        return mailService.downloadPdf(request);
    }

    @PostMapping("/sendadvice")
    public MailResponse sendMailWithByteArray(@RequestBody User user) throws MessagingException {
        try {
            mailService.sendMailWithByteArray(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MailResponse.builder().isSuccess(true).build();
    }
}
