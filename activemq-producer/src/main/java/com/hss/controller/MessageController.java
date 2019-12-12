package com.hss.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hss.service.MessageService;

@Controller
public class MessageController {
	
	private final static Logger logger = Logger.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 
	 * @param from 发送人
	 * @param to 接收人
	 * @param subject 主题
	 * @param content 内容
	 * @return
	 */
	@RequestMapping(value="/sendMessage")
	public String sendMessage(@RequestParam(value="from",required = false)String from,
			@RequestParam(value="to")String to,
			@RequestParam(value="subject")String subject,
			@RequestParam(value="content")String content) {
		//from = "hss321hss";
		logger.info("from="+from+",to="+to+",subject="+subject+",content="+content);
		this.messageService.sendMessage(from,to,subject,content);
		
		return "ok";
	}
	
	
}
