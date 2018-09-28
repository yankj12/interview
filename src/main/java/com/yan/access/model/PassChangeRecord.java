package com.yan.access.model;

import java.io.Serializable;
import java.util.Date;

public class PassChangeRecord implements Serializable{

	// 记录修改密码时的pdc
	private String pdc;
	
	// 记录修改面时的类型
	// 忘记密码forgetPass
	// 修改密码modifyPass
	private String type;
	
	// 记录修改密码时的userCode
	private String userCode;
	
	// 记录吸怪密码时的email
	private String email;
	
	private String remark;
	
    private Date insertTime;

    private Date updateTime;

	public String getPdc() {
		return pdc;
	}

	public void setPdc(String pdc) {
		this.pdc = pdc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
