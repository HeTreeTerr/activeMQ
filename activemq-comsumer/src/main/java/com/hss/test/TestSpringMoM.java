package com.hss.test;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public class TestSpringMoM {

	public static void main(String[] args) {
		ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");
		System.out.println("spring容器启动。。。");

	}

}
