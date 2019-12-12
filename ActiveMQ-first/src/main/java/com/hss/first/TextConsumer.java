package com.hss.first;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TextConsumer {
	
	public String receiveTextMessage() {
		
		String resultCode = "";
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		//消息的消费者，用于接收消息的接收
		MessageConsumer consumer = null;
		Message message = null;
		
		try {
			factory = new ActiveMQConnectionFactory("admin", "admin", 
					"tcp://10.48.56.214:61616");
			connection = factory.createConnection();
			//消息的消费者必须启动连接，否则无法处理消息
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			destination = session.createQueue("first-mq");
			//创建消息消费者对象。在指定的目的地中获取消息
			consumer = session.createConsumer(destination);
			//获取消息队列中的消息。receive方法是一个主动获取消息的方法。执行一次，拉取一个消息，开发少用
			message = consumer.receive();
			
			//确认消息，发送处理消息确认信息。通知MQ删除对应的消息。
			message.acknowledge();
			
			//处理文本消息
			resultCode = ((TextMessage)message).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//回收资源
			if(consumer != null) {//回收消息消费者
				try {
					consumer.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if(session != null) {//回收会话对象
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {//回收连接对象
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
		return resultCode;
	}

	public static void main(String[] args) {
		TextConsumer consumer = new TextConsumer();
		String message = consumer.receiveTextMessage();
		System.out.println("message:"+message);

	}

}
