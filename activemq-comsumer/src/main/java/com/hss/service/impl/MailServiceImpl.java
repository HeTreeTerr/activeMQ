package com.hss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.hss.bean.MailContent;
import com.hss.service.MailService;

@Service
public class MailServiceImpl implements MailService{

	@Autowired
    private JavaMailSender sender;
	
	@Autowired
	private SimpleMailMessage mailMessage;
	
	@Autowired
	private ThreadPoolTaskExecutor pool;
	
	public void sendMail(final MailContent mail) {
		this.pool.execute(new Runnable() {
			
			public void run() {
				mailMessage.setFrom("hss321hss@163.com");
				mailMessage.setTo(mail.getTo());
				mailMessage.setSubject(mail.getSubject());
				mailMessage.setText(mail.getContent());
				sender.send(mailMessage);
			}
		});
		
	}

}
