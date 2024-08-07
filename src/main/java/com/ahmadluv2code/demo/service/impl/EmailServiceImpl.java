package com.ahmadluv2code.demo.service.impl;

import com.ahmadluv2code.demo.dto.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient());
             mailMessage.setText(emailDetails.getMessageBody());
             mailMessage.setSubject(emailDetails.getSubject());

             javaMailSender.send(mailMessage);
            System.out.println("Mail sent successfully");

        } catch (MailException e){
            throw new RuntimeException(e);
        }
    }
}
