package com.yan.interview.model;

import java.util.Date;

public class InterviewConfig {

	private String id;
	
	// 这个面试邮件模板归属于那个人员
	private String userCode;
	
	// 模板的名称
	private String name;
	
	// 面试邀请邮件抄送给谁
	private String copyTo;
	
	// 面试邀请邮件的主题
	private String subject;
	
	// 面试地点
	private String interviewAddress;
	
	// 面试官称呼
	private String interviewOfficerTitle;
	
	// 面试官联系电话
	private String interviewOfficerPhone;
	
	// 邮件正文后缀，比如发件人的联系方式及公司信息等
	private String emailSuffix;

	// 有效状态
	private String validStatus;
	
	private Date insertTime;
	
	private Date updateTime;
	
	public String getInterviewAddress() {
		return interviewAddress;
	}

	public void setInterviewAddress(String interviewAddress) {
		this.interviewAddress = interviewAddress;
	}

	public String getInterviewOfficerTitle() {
		return interviewOfficerTitle;
	}

	public void setInterviewOfficerTitle(String interviewOfficerTitle) {
		this.interviewOfficerTitle = interviewOfficerTitle;
	}

	public String getInterviewOfficerPhone() {
		return interviewOfficerPhone;
	}

	public void setInterviewOfficerPhone(String interviewOfficerPhone) {
		this.interviewOfficerPhone = interviewOfficerPhone;
	}

	public String getEmailSuffix() {
		return emailSuffix;
	}

	public void setEmailSuffix(String emailSuffix) {
		this.emailSuffix = emailSuffix;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
