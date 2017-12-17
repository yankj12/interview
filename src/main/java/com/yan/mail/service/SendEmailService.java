package com.yan.mail.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.yan.mail.common.EmailConfig;

public class SendEmailService {

	private EmailConfig emailConfig;
	
	public EmailConfig getEmailConfig() {
		return emailConfig;
	}

	public void setEmailConfig(EmailConfig emailConfig) {
		this.emailConfig = emailConfig;
	}

	public boolean sendEmail(String emailMsg, String toMail) {
		boolean result = false;
		try {
			Email email = new SimpleEmail();
			email.setHostName(emailConfig.getSmtpHostName());
			email.setSmtpPort(emailConfig.getSmtpPort());
			// 在java代码中发送邮件，有些邮箱提供商（比如163邮箱），在使用smtp发送邮件的时候，密码使用的是授权码，而非真的密码，需要注意
			email.setAuthenticator(new DefaultAuthenticator(emailConfig.getMailUserName(), emailConfig.getMailUserPwd()));
			email.setSSLOnConnect(true);
			email.setFrom(emailConfig.getFromMail());
			email.setSubject(emailConfig.getSubject());
			email.setMsg(emailMsg);
			email.addTo(toMail);
			
			// 抄送
			String copyTo = emailConfig.getCopyTo();
			if(copyTo != null &&!"".equals(copyTo.trim())) {
				String[] ccAry = copyTo.split(",");
				if(ccAry != null && ccAry.length > 0) {
					for(int i=0;i<ccAry.length;i++) {
						if(ccAry[i] != null && !"".equals(ccAry[i].trim()) && ccAry[i].contains("@")) {
							email.addCc(ccAry[i]);
						}
					}
				}
			}
			
			email.send();
			
			result = true;
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}

