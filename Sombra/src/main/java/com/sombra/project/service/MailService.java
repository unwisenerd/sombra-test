package com.sombra.project.service;

public interface MailService {

	void sendMail(String subject, String text, String recipient);

	void sendFile(String subject, String text, String recipient, String file);

}
