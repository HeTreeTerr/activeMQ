package com.hss.bean;

import java.io.Serializable;

/**
 * 邮件实体类型
 * @author lenovo
 *
 */
public class MailContent implements Serializable{

	private static final long serialVersionUID = -1558498654408050326L;
	
	/** 发件人 */
	private String from;
	/** 发件人 */
	private String to;
	/** 主题*/
	private String subject;
	/** 内容*/
	private String content;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "MailContent [from=" + from + ", to=" + to + ", subject=" + subject + ", content=" + content + "]";
	}
	
	

}
