package com.hss.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 发送一个字符串文本消息到ActiveMQ中
 * @author lenovo
 *
 */
public class TopicProducer {
	
	/**
	 * 发送消息到ActiveMq中，具体消息内容为参数信息
	 * 开发JMS相关代码过程中，使用的接口类型都是javax.jms包下的类型
	 * @param datas - 消息内容
	 */
	public void sendTextMessage(String datas) {
		//连接工厂
		ConnectionFactory factory = null;
		//连接
		Connection connection = null;
		//目的地
		Destination destination = null;
		//会话
		Session session = null;
		//消息发送者
		MessageProducer producer = null;
		//消息对象
		Message message = null;
		
		try {
			//创建连接工厂，连接ActiceMQ服务的连接工厂。
			//创建工厂，构造方法有三个参数，分别是用户名、密码、连接地址
			factory = new ActiveMQConnectionFactory("guest", "guest", 
					"tcp://10.48.56.214:61616");
			
			//通过工厂，创建连接对象。
			//创建连接的方法有重载，其中createConnection(String username,String);
			//可以在创建连接工厂时，只传递连接地址，不传递用户信息。
			connection = factory.createConnection();
			//建议启动连接，消息的发送者不是必须启动；连接。消息的消费者必须启动连接。
			connection.start();
			
			//通过连接对象，创建会话对象。必须绑定目的地
			/*
			 * 创建会话的时候，必须传递两个参数，分别代表是否支持事务 和如何确认消息处理。
			 * transacted - 是否支持事务，数据类型是boolean. true-支持，false-不支持
			 *  true - 支持事务，第二个参数默认无效。建议传递的数据是Session.SESSION_TRANSACTED
			 *  false - 不知处事务，常用参数。第二个参数必须传递，且必须有效。
			 * 
			 * acknowledgeMode - 如何确认消息的处理。使用确认机制实现的。
			 * 	AUTO_ACKNOWLEDGE - 自动确认消息。消息的消费者处理消息后，自动确认。常用（商业开发不推荐）
			 * 	CLIENT_ACKNOWLEDGE - 客户端手动确认。消息的消费者处理后，必须手工确认。
			 * 	DUPS_OK_ACKNOWLEDGE - 有副本的客户端手动确认。
			 * 		一个消息可以多次处理
			 * 		可以降低Session的消耗，在可以容忍重复消息时使用。（不推荐使用）
			 */
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			//创建主题目的地。topic
			destination = session.createTopic("test-topic");
			
			//通过会话对象，创建消息的发送者producer
			//创建的消息发送者，发送的消息一定到指定的目的地中。
			producer = session.createProducer(destination);
			
			//创建文本消息对象，作为具体数据内容的载体。
			message = session.createTextMessage(datas);
			
			//使用producer,发送消息到ActiveMQ中的目的地。
			producer.send(message);
			
			System.out.println("消息已发送");
			
		}catch(Exception e) {
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
		TopicProducer topicProducer = new TopicProducer();
		topicProducer.sendTextMessage("测试ActiveMQ-4");

	}

}
