package com.hss.send;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.hss.exception.InitJMSException;

public class Producer4Send {
	
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;
	private Message message = null;
	
	/**
	 * 直接发送消息
	 * @param obj
	 */
	public void sendMessage(Serializable obj) {
		try {
			this.init("test-send");
			message = session.createObjectMessage(obj);
			producer.send(message);
			
			System.out.println("sendMessage method run");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 指定目的地发送消息
	 * @param obj 消息内容
	 * @param destinationName 目的地
	 */
	public void sendMessage4Destination(Serializable obj,String destinationName) {
		try {
			this.init();
			
			message = session.createObjectMessage(obj);
			//创建临时目的地
			Destination destination = session.createQueue(destinationName);
			producer.send(destination,message);
			
			System.out.println("sendMessage4Destination method run");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 多参数发送消息
	 * @param obj
	 * @param deliveryMode - 持久化模型
	 * 		DeliverMode.PERSISENT - 持久化，消息会持久化到数据库（kahadb,JDBC等）
	 * 		DeliverMode.NON_PERSISTENT - 不持久化，消息只保存到内存中。
	 * @param priority - 优先级，0-9取值范围，取值越大优先级越高。不能保证绝对顺序
	 * 		必须在activemq.xml配置文件的broker标签中增加配置
	 * @param timeToLive - 消息有效期。单位毫秒。有效期超时，消息自动放弃。
	 */
	public void sendMessageWithParamters(Serializable obj,int deliveryMode,int priority,long timeToLive) {
		try {
			this.init("test-send-params");
			
			message = session.createObjectMessage(obj);
			System.out.println(timeToLive);
			producer.send(message, deliveryMode, priority, timeToLive);
			System.out.println("sendMessageWithParamters method run");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Producer4Send() throws InitJMSException{
		//init();
	}



	private void init() throws InitJMSException {
		this.init(null);
	}
	
	private void init(String destinationName) throws InitJMSException{
		this.init("admin","admin","tcp://10.50.228.168:61616",destinationName);
	}
	
	private void init(String userName,String password,String brokerURL,String destinationName) throws InitJMSException{
		try {
			factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			if(null != destinationName) {
				destination = session.createQueue(destinationName);
				producer = session.createProducer(destination);
			}else {
				producer = session.createProducer(null);
			}
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
			throw new InitJMSException(e);
		}
	}
	
	public void destory() {
		this.release();
	}
	
	private void release() {
		
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

	public static void main(String[] args) {
		Producer4Send producer = null;
		
		try {
			producer = new Producer4Send();
			
//			producer.sendMessage("send message");
			
//			producer.sendMessage4Destination("send message for destination", "test-send-des");
			
			
			producer.sendMessageWithParamters("send message for paramters", DeliveryMode.PERSISTENT, 0, 1000*10);
			
			producer.destory();
		} catch (InitJMSException e) {
			e.printStackTrace();
		}

	}

}
