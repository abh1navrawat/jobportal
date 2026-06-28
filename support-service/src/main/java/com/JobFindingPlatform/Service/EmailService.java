package com.JobFindingPlatform.Service;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.JobFindingPlatform.DTO.EmailRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

//	@Autowired
//	private JavaMailSender mailSender;
//	
//	
//	public void sendEmail(EmailRequestDTO dto) {
//		
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(dto.getTo());
//		message.setSubject(dto.getSubject());
//		message.setText(dto.getBody());
//		
//		mailSender.send(message);
//		
//	}
	@org.springframework.beans.factory.annotation.Value("${spring.mail.username}")
	private String fromEmail;
	
	@org.springframework.beans.factory.annotation.Value("${spring.mail.password}")
	private String password;
	
	public void sendEmail(EmailRequestDTO dto) {
		
		
		
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smpt.starttls.enable", "true");
		
		
		
		Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(fromEmail,password);
			}
			
		});
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(dto.getTo()) );
			message.setSubject(dto.getSubject());
			message.setText(dto.getBody());
			
			Transport.send(message);
			System.out.println("Email sent to"+dto.getTo());
			
			
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send Email" , e);
			
		}
	}
}

