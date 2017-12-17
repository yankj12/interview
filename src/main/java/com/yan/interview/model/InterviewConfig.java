package com.yan.interview.model;

public class InterviewConfig {

	private String interviewAddress;
	
	private String interviewOfficerTitle;
	
	private String interviewOfficerPhone;
	
	// 邮件正文后缀，比如发件人的联系方式及公司信息等
	private String emailSuffix;

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

}
