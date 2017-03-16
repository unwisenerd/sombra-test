package com.sombra.project.serviceimpl;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sombra.project.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private final String USERNAME = "wcrlf1@gmail.com";
	private final String PASSWORD = "wowcs123";

	@Async
	public void sendMail(String subject, String text, String recipient) {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(USERNAME));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(text, "text/html");
			Transport.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Async
	public void sendFile(String subject, String text, String recipient, String file) {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(USERNAME));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			mimeMessage.setSubject(subject);
			Multipart multiPart = new MimeMultipart();
			BodyPart bodyText = new MimeBodyPart();
			bodyText.setContent(text, "text/html");
			multiPart.addBodyPart(bodyText);
			BodyPart bodyFile = new MimeBodyPart();
			DataSource dataSource = new FileDataSource(file);
			bodyFile.setDataHandler(new DataHandler(dataSource));
			bodyFile.setFileName(dataSource.getName());
			multiPart.addBodyPart(bodyFile);
			mimeMessage.setContent(multiPart);
			Transport.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
