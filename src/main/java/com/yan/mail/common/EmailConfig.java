package com.yan.mail.common;

public class EmailConfig {

	// smtp服务器名称，比如：smtp.163.com
	private String smtpHostName;
	
	// smtp服务器端口
	private int smtpPort;
	
	// 是否使用ssl
	private boolean sslOnConnect;

	// 发件人用户名，用于邮箱认证
	private String mailUserName;
	
	// 发件人密码，用于邮箱认证
	private String mailUserPwd;
	
	private String fromMail;
	
	private String copyTo;
	
	// 邮件主题
	private String subject;

	public String getSmtpHostName() {
		return smtpHostName;
	}

	public void setSmtpHostName(String smtpHostName) {
		this.smtpHostName = smtpHostName;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public boolean isSslOnConnect() {
		return sslOnConnect;
	}

	public void setSslOnConnect(boolean sslOnConnect) {
		this.sslOnConnect = sslOnConnect;
	}

	public String getMailUserName() {
		return mailUserName;
	}

	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	public String getMailUserPwd() {
		return mailUserPwd;
	}

	public void setMailUserPwd(String mailUserPwd) {
		this.mailUserPwd = mailUserPwd;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getCopyTo() {
		return copyTo;
	}

	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
