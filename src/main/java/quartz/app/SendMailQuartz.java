/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.app;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

/**
 *
 * @author alexandru.gojgar
 */
public class SendMailQuartz {
    private String host = "";
    private int port = 0;
    private String username = "";
    private String password = "";
    private String catre="";
    private String subject = "";
    private String mesajMail = "";


    public SendMailQuartz(String host, int port, String username, String password, String catre, String subject, String mesajMail) {

        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.catre = catre;
        this.subject = subject;
        this.mesajMail = mesajMail;

        sendMail();
    }

    private void sendMail() {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
                Multipart multipart = new MimeMultipart();
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("vanzari.inovo@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(catre));
                message.setSubject(subject);

                String msg = mesajMail;

                BodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(msg + "<br/><br/> Multumesc,<br/> <b>Inovo Mobili</b> <br/><img src=\"cid:image\">", "text/html");
                multipart.addBodyPart(mimeBodyPart);
                mimeBodyPart = new MimeBodyPart();
                DataSource fds = new FileDataSource(
                    new File("/opt/files/logo.png"));

                 mimeBodyPart.setDataHandler(new DataHandler(fds));
                 mimeBodyPart.setHeader("Content-ID", "<image>");
//                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//                attachmentBodyPart.attachFile(new File("pom.xml"));

                
                multipart.addBodyPart(mimeBodyPart);
                //multipart.addBodyPart(attachmentBodyPart);

                message.setContent(multipart);

                Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
