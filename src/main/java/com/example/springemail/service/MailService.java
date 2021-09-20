package com.example.springemail.service;

import com.example.springemail.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class MailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

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

        ClassPathResource resource = new ClassPathResource("Attachment.pdf");
        helper.addAttachment(resource.getFilename(), resource);
        javaMailSender.send(mimeMessage);
    }
}
