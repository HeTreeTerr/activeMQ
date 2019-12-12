package com.hss.listener;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ObjectProduce {
	
	public void sendMessage(Object obj) {
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer producer = null;
		Message message = null;
		
		try {
			factory = new ActiveMQConnectionFactory("admin", "admin", 
					"tcp://10.48.56.214:61616");
			
			connection = factory.createConnection();
			
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			
			destination = session.createQueue("test-listener");
			
			producer = session.createProducer(destination);
			
			connection.start();
			
			Random random = new Random();
			for(int i = 0;i < 100;i++) {
				//Integer data = random.nextInt(100);
				Integer data = i;
				//创建对象消息，消息中的数据载体是一个可序列化的对象
				message = session.createObjectMessage(data);
				producer.send(message);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			//回收资源
			if(producer != null) {//回收消息发送者
				try {
					producer.close();
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
	}

	public static void main(String[] args) {
		ObjectProduce produce = new ObjectProduce();
		produce.sendMessage(null);

	}

}
