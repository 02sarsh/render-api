package com.deploy.api;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class sendemail {
//
//	public void sendmail() {
//	System.out.println( "preparing to send msg");
//    String message="this is gmail Api";
//    String subject="creating Api";
//    String to="02sarveshb@gmail.com";
//    String from="coolsampatpatil@gmail.com";
//    
//    sendEmail(message,subject,to,from);
//}

public void sendEmail(String message, String subject, String to, String from) {
	
	String host="smtp.gmail.com";
	
	Properties properties=System.getProperties();
	System.out.println(properties);
	
	properties.put("mail.smtp.host", host);
	properties.put("mail.smtp.port", "465");
	properties.put("mail.smtp.ssl.enable","true");
	properties.put("mail.smtp.auth","true");
	
	Session session=Session.getInstance(properties,new Authenticator() {
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		
		return new PasswordAuthentication("coolsampatpatil@gmail.com","uwvg brsw lgkz ndzk");
	}
	
	
	
	});
	
	
	MimeMessage m=new MimeMessage(session);
	
	try {
		
		m.setFrom(from);
		
		m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		
		m.setSubject(subject);
		
		m.setText(message);
		
		Transport.send(m);
		
		System.out.println("success");
		
	} catch (Exception e) {
		e.printStackTrace();		}
	
	}

public sendemail() {
	super();
	// TODO Auto-generated constructor stub
}
	
}

	

