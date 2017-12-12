package com.yan.interview.vo;

import java.io.Serializable;
import java.util.Date;

public class InterviewVo implements Serializable{

	private String id;
	
	private String userName;
	
	private String userEName;
	
	/**
	 * F	女
	 * M	男
	 */
	private String genderCode;
	
	private String genderName;
	
	private String phone;
	
	private String email;
	
	/**
	 * 出生年月
	 */
	private String birth;
	
	/**
	 * 毕业院校
	 */
	private String university;
	
	/**
	 * 专业
	 */
	private String major;
	
	/**
	 * 学历
	 */
	private String educationBackground;
	
	/**
	 * 毕业时间
	 * 年/月
	 */
	private String graduateMonth;
	
	/**
	 * 工作年限
	 */
	private Integer jobExperienceYear;
	
	/**
	 * 首次工作
	 */
	private String firstJob;
	
	/**
	 * 首次工作时间
	 */
	private String firstJobBeginMonth;
	
	/**
	 * 阶段
	 * 选完简历、电话约一面，一面，一面不通过，电话约复试，复试不通过，复试通过
	 */
	private String interviewPhase;
	
	/**
	 * 电话约面试时间
	 */
	private String firstPhoneCallTime;
	
	/**
	 * 电话约面试备注
	 */
	private String firstPhoneCallRemark;
	
	/**
	 * 一面时间
	 */
	private String firstInterviewTime;
	
	/**
	 * 一面面试官
	 */
	private String firstInterviewOfficer;
	
	/**
	 * 一面评价
	 */
	private String firstIntervirewRemark;
	
	/**
	 * 复试时间
	 */
	private String secondInterviewTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEName() {
		return userEName;
	}

	public void setUserEName(String userEName) {
		this.userEName = userEName;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getEducationBackground() {
		return educationBackground;
	}

	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}

	public String getGraduateMonth() {
		return graduateMonth;
	}

	public void setGraduateMonth(String graduateMonth) {
		this.graduateMonth = graduateMonth;
	}

	public Integer getJobExperienceYear() {
		return jobExperienceYear;
	}

	public void setJobExperienceYear(Integer jobExperienceYear) {
		this.jobExperienceYear = jobExperienceYear;
	}

	public String getFirstJob() {
		return firstJob;
	}

	public void setFirstJob(String firstJob) {
		this.firstJob = firstJob;
	}

	public String getFirstJobBeginMonth() {
		return firstJobBeginMonth;
	}

	public void setFirstJobBeginMonth(String firstJobBeginMonth) {
		this.firstJobBeginMonth = firstJobBeginMonth;
	}

	public String getInterviewPhase() {
		return interviewPhase;
	}

	public void setInterviewPhase(String interviewPhase) {
		this.interviewPhase = interviewPhase;
	}

	public String getFirstPhoneCallTime() {
		return firstPhoneCallTime;
	}

	public void setFirstPhoneCallTime(String firstPhoneCallTime) {
		this.firstPhoneCallTime = firstPhoneCallTime;
	}

	public String getFirstPhoneCallRemark() {
		return firstPhoneCallRemark;
	}

	public void setFirstPhoneCallRemark(String firstPhoneCallRemark) {
		this.firstPhoneCallRemark = firstPhoneCallRemark;
	}

	public String getFirstInterviewTime() {
		return firstInterviewTime;
	}

	public void setFirstInterviewTime(String firstInterviewTime) {
		this.firstInterviewTime = firstInterviewTime;
	}

	public String getFirstInterviewOfficer() {
		return firstInterviewOfficer;
	}

	public void setFirstInterviewOfficer(String firstInterviewOfficer) {
		this.firstInterviewOfficer = firstInterviewOfficer;
	}

	public String getFirstIntervirewRemark() {
		return firstIntervirewRemark;
	}

	public void setFirstIntervirewRemark(String firstIntervirewRemark) {
		this.firstIntervirewRemark = firstIntervirewRemark;
	}

	public String getSecondInterviewTime() {
		return secondInterviewTime;
	}

	public void setSecondInterviewTime(String secondInterviewTime) {
		this.secondInterviewTime = secondInterviewTime;
	}
	
}
