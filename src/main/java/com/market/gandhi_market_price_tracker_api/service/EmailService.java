package com.market.gandhi_market_price_tracker_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${admin.email}")  // store in application.properties
    private String adminEmail;

    public void remainderMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("Reminder: Update Today's Crop Prices");

        String body = "Hi Team,\n\n" +
                "Good evening!\n\n" +
                "This is a gentle reminder to update today’s crop prices in the system.\n\n" +
                "Update Link: https://gandhi-market-price-tracker-ui.vercel.app\n\n" +
                "Keeping the data up to date ensures accurate reporting and smooth operations. " +
                "Please make sure all prices are entered at your earliest convenience.\n\n" +
                "Thank you,\n" +
                "System Notification - Gandhi Market Price Tracker\n\n" +
                "--- \n" +
                "Note: This is an automated message. Please do not reply to this email.";

        message.setText(body);
        mailSender.send(message);
    }

    public void sendMissingPriceAlert(List<String> missingCrops) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("⚠️ Gandhi Market — Prices Not Updated for Today");

        StringBuilder body = new StringBuilder();
        body.append("Hi Team,\n\n");
        body.append("Good evening!\n\n");
        body.append("The following crops do not have prices entered for today(");
        body.append(LocalDate.now()).append("):\n\n");

        missingCrops.forEach(crop -> body.append(" • ").append(crop).append("\n"));

        body.append("\nPlease update the prices at your earliest convenience.\n");
        body.append("Update Link: https://gandhi-market-price-tracker-ui.vercel.app\n\n");
        body.append("Thank you,\n");
        body.append("System Notification - Gandhi Market Price Tracker\n\n");
        body.append("--- \n");
        body.append("Note: This is an automated message. Please do not reply to this email.");

        message.setText(body.toString());
        mailSender.send(message);

    }
}

