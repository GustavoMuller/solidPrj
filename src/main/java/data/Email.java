package data;

import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Email {
    private String fromEmail = "EMAIL";
    private String password = "PASSWORD";

    public void email(String ToEmail, String fileName) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.user", fromEmail);
        prop.put("mail.smtp.password", password);
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(ToEmail));
            message.setSubject("Flight Report");
            Multipart content = new MimeMultipart();
            MimeBodyPart body = new MimeBodyPart();
            body.setText("Good morning, here is the flight report.");
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile("src/main/resources/" + fileName + ".xlsx");
            content.addBodyPart(body);
            content.addBodyPart(attachment);
            message.setContent(content);
            Transport.send(message);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
