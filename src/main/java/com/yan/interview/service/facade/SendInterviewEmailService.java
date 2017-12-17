package com.yan.interview.service.facade;

import com.yan.interview.model.Interview;

public interface SendInterviewEmailService {

	/**
	 * 发送一个人员的面试邮件
	 * @param interview
	 * @return true表示发送成功，false表示发送失败
	 */
	public boolean sendSingleInterviewEmail(Interview interview);
}
