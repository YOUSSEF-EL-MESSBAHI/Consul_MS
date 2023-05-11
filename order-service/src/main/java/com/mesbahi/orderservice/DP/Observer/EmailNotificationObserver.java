package com.mesbahi.orderservice.DP.Observer;

import com.mesbahi.orderservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements OrderObserver{
    private String emailAddress="Youssef";
    private JavaMailSender mailSender;

    public EmailNotificationObserver(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void update(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("elmessbahiyoussef@gmail.com");
        message.setTo("youssefelmesbahi30@gmail.com");
        message.setText("Observer Design Pattern with Email when status is changed for State Design Pattern");
        message.setSubject("Observer");
        mailSender.send(message);
        System.out.println("Mail Send...");
        System.out.println("Sending email notification to " + emailAddress);
    }
}
