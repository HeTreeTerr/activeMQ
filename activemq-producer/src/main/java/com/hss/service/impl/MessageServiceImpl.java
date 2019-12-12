package com.hss.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.hss.bean.MailContent;
import com.hss.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	
	private final static Logger logger = Logger.getLogger(MessageServiceImpl.class);
	
	@Autowired
	private JmsTemplate template;

	/**
	 * 发送消息到ActiveMQ中。
	 * 1.需要连接（Connection）对象，会话（Session）对象，消息发送器（Producer）对象。
	 * 2.发送消息
	 * 使用Spring提供的JmsTemplate对象实现访问
	 * 使用spring提供JmsTemplate对象实现访问
	 * JmsTemplate - 是spring封装的一个，专门访问MOM容器的模板对象。
	 * 其中定义若干方法，实现消息的发送和接收
	 */
	public void sendMessage(String from, String to, String subject, String content) {
		//设置目的地名称--this.template.setDefaultDestinationName("");
		if(to.indexOf(";")!= -1) {
			String[] tos = to.split(";");
			for(String t : tos) {
				logger.info("发送人="+t);
				if(null == t || "".equals(to.trim())) {
					continue;
				}
				final MailContent mail = this.transfer2Mail(from,t,subject,content);
				
				this.sendMessage(mail);
			}
		}else {
			final MailContent mail = this.transfer2Mail(from,to,subject,content);
			
			this.sendMessage(mail);
		}
		
	}
	
	private MailContent transfer2Mail(String from, String to, String subject, String content) {
		MailContent mail = new MailContent();
		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setContent(content);
		
		return mail;
	}
	
	private void sendMessage(final MailContent mail) {
		template.send(new MessageCreator() {
			/**
			 * 创建一个要发送的消息对象
			 * 并返回这个消息对象
			 * template自动将消息对象发送到MOM容器中
			 */
			public Message createMessage(Session session) throws JMSException {

				Message message = session.createObjectMessage(mail);
				return message;
			}
		});
	}

}
