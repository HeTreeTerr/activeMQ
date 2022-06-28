package com.hss.exception;

import javax.jms.JMSException;

@SuppressWarnings("serial")
public class InitJMSException extends Exception{
	
	public InitJMSException() {
		super();
	}

	public InitJMSException(JMSException e) {
		super();
	}

}
