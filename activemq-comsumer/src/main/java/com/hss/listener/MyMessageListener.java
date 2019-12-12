package com.hss.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hss.bean.MailContent;
import com.hss.service.MailService;

@Component(value="myListener")
public class MyMessageListener implements MessageListener{

	@Autowired
	private MailService mailService;
	
	/**
	 * 监听方法
	 */
	public void onMessage(Message message) {
		System.out.println("监听到消息");
		try {
			if(message instanceof ObjectMessage) {
				ObjectMessage om = (ObjectMessage)message;
				Object data = om.getObject();
				if(data instanceof MailContent) {
					MailContent mailContent = (MailContent)data;
					this.mailService.sendMail(mailContent);
				}
			}else {
				System.out.println(message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
