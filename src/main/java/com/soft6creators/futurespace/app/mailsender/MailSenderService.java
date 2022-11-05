package com.soft6creators.futurespace.app.mailsender;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail, String subject, String body) throws MessagingException, UnsupportedEncodingException {
		ClassPathResource image = new ClassPathResource("public/images/logocopy.png");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("cryptospaceassets@gmail.com", "CRYPTOSPACE SUPPORT");
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(body, true);
		helper.addInline("image", image);
		
		mailSender.send(message);
		System.out.println("Mail sent successfully");
	}
}
