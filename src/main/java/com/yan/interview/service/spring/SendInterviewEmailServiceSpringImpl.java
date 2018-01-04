package com.yan.interview.service.spring;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yan.common.util.DateUtil;
import com.yan.interview.model.Interview;
import com.yan.interview.model.InterviewConfig;
import com.yan.interview.service.facade.SendInterviewEmailService;
import com.yan.mail.service.SendEmailService;

public class SendInterviewEmailServiceSpringImpl implements SendInterviewEmailService{

	private static final Logger logger = LogManager.getLogger("sendEmail");

	private InterviewConfig interviewConfig;
	
	private SendEmailService sendEmailService;

	public SendEmailService getSendEmailService() {
		return sendEmailService;
	}

	public void setSendEmailService(SendEmailService sendEmailService) {
		this.sendEmailService = sendEmailService;
	}
	
	public InterviewConfig getInterviewConfig() {
		return interviewConfig;
	}

	public void setInterviewConfig(InterviewConfig interviewConfig) {
		this.interviewConfig = interviewConfig;
	}

	/**
	 * 发送一个人员的面试邮件
	 * @param interview
	 * @return true表示发送成功，false表示发送失败
	 */
	public boolean sendSingleInterviewEmail(Interview interview) {
		boolean result = false;
		try {
			String name = interview.getUserName();
			
			String firstInterviewTime = interview.getFirstInterviewTime();
			// 通过正则表达式将
			Date interviewTime = DateUtil.formatDateStr(firstInterviewTime);
			logger.debug("Service, format first interview time success");
			
			String toMail = interview.getEmail();
			
			String address = interviewConfig.getInterviewAddress();
			String interviewOfficerTitle = interviewConfig.getInterviewOfficerTitle();
			String interviewOfficerPhone = interviewConfig.getInterviewOfficerPhone();
			
			// 邮件正文后缀，比如发件人的联系方式及公司信息等
			String emailSuffix = interviewConfig.getEmailSuffix();
			
			String emailMsg = this.formatEmailMsg(name, interviewTime, address, interviewOfficerTitle, interviewOfficerPhone, emailSuffix);
			logger.debug("Service, formatEmailMsg success");
			
			sendEmailService.sendEmail(emailMsg, toMail);
			
			logger.debug("Service, send email success");
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		
		return result;
	}
	
	/**
	 * 生成面试邀请邮件内容
	 * @param name
	 * @param interviewTime
	 * @param address
	 * @param interviewOfficerTitle 避免直呼其名，面试官名称的简写、职位或者尊称
	 * @param interviewOfficerPhone
	 * @param emailSuffix
	 * @return
	 */
	private String formatEmailMsg(String name, Date interviewTime, String address, String interviewOfficerTitle, String interviewOfficerPhone, String emailSuffix) {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		String interviewTimeFormatStr = simpleDateFormat.format(interviewTime);
		
		Calendar calendar = Calendar.getInstance();
		
		// 处理当前时间
		int thisWeekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
				
		// 处理面试时间
		calendar.setTime(interviewTime);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		int interviewWeekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		
		Map<Integer, String> dayOfweekCNameMap = new HashMap<>();
		dayOfweekCNameMap.put(Calendar.SUNDAY, "周日");
		dayOfweekCNameMap.put(Calendar.MONDAY, "周一");
		dayOfweekCNameMap.put(Calendar.TUESDAY, "周二");
		dayOfweekCNameMap.put(Calendar.WEDNESDAY, "周三");
		dayOfweekCNameMap.put(Calendar.THURSDAY, "周四");
		dayOfweekCNameMap.put(Calendar.FRIDAY, "周五");
		dayOfweekCNameMap.put(Calendar.SATURDAY, "周六");
		
		
		String weekPrefixOfCName = "";
		if(interviewWeekOfMonth == thisWeekOfMonth) {
			weekPrefixOfCName = "";
		}else if( (interviewWeekOfMonth - thisWeekOfMonth) == 1) {
			weekPrefixOfCName = "下";
		}else if( (interviewWeekOfMonth - thisWeekOfMonth) == -1) {
			weekPrefixOfCName = "上";
		}
		
		String dayOfweekCName = weekPrefixOfCName + dayOfweekCNameMap.get(dayOfWeek);
		
		
		StringBuilder msg = new StringBuilder();
		msg.append(name).append("，你好！\n");
		msg.append("    我们已收到您投递的应聘职位JAVA软件工程师的简历，经过我司HR的初步筛选，认为你与我们的职位要求很匹配，现诚挚邀请你来参加我司的技术笔试及面试。请你准时出席，如时间上有变化也请尽快与我们联系。  \n");
		msg.append("\n");
		msg.append("    请携带一份纸质简历及一支笔\n");
		msg.append("    面试职位：JAVA软件开发岗\n");
		msg.append("    面试时间：").append(interviewTimeFormatStr).append(" (").append(dayOfweekCName).append(")\n");
		msg.append("    面试地点：").append(address).append("\n");
		msg.append("    联系人：").append(interviewOfficerTitle).append("\n");
		msg.append("    联系方式：").append(interviewOfficerPhone).append("\n");
		
		msg.append("\n");
		
		msg.append(emailSuffix);
		
		return msg.toString();
	}
}
