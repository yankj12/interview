package com.yan.interview.model;

import java.io.Serializable;

public class College implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	/**
	 * 学校名称
	 */
	private String name;
	
	/**
	 * 公立 public
	 * 私立、民办 private
	 */
	private String collegeType;
	
	/**
	 * 学信网 xuexinwang
	 * 民教网 minjiaowang
	 */
	private String diplomaCertification;
	
	/**
	 * 统招本科
	 */
	private String isBachelorDegree;

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

	public String getCollegeType() {
		return collegeType;
	}

	public void setCollegeType(String collegeType) {
		this.collegeType = collegeType;
	}

	public String getDiplomaCertification() {
		return diplomaCertification;
	}

	public void setDiplomaCertification(String diplomaCertification) {
		this.diplomaCertification = diplomaCertification;
	}

	public String getIsBachelorDegree() {
		return isBachelorDegree;
	}

	public void setIsBachelorDegree(String isBachelorDegree) {
		this.isBachelorDegree = isBachelorDegree;
	}
	
}
