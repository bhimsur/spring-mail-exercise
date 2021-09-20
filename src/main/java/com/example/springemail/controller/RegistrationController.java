package com.example.springemail.controller;

import com.example.springemail.model.User;
import com.example.springemail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
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
    public String send(@RequestBody User user) {
        try {
            mailService.sendEmail(user);
        } catch (MailException e) {
            System.out.println(e);
        }
        return "Mail send to " + user.getEmailAddress();
    }

    @PostMapping("/sendemailattachment")
    public String sendWithAttachment(@RequestBody User user) throws MessagingException {
        try {
            mailService.sendEmailWithAttachment(user);
        } catch (MailException e) {
            System.out.println(e);
        }
        return "Mail send to " + user.getEmailAddress();
    }
}
