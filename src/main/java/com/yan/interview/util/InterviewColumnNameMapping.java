package com.yan.interview.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 目前将列的中文名称和英文名称对应按照这样的方式，后续可以改造成使用注解的方式
 * 用于excel中列和vo中字段对应
 * @author Yan
 *
 */
public class InterviewColumnNameMapping {
	
	private static Map<String, String> cnameToENameMap = new HashMap<String, String>();
	private static Map<String, String> enameToCNameMap = new HashMap<String, String>();

	static{
		cnameToENameMap.put("电话沟通备注", "firstPhoneCallRemark");
		cnameToENameMap.put("姓名", "userName");
		cnameToENameMap.put("拼音", "userEName");
		cnameToENameMap.put("性别", "genderName");
		//F	女
		//M	男
		cnameToENameMap.put("手机", "phone");
		cnameToENameMap.put("邮箱", "email");
		cnameToENameMap.put("出生年月", "birth");
		cnameToENameMap.put("学校", "university");
		cnameToENameMap.put("专业", "major");
		cnameToENameMap.put("学历", "educationBackground");
		cnameToENameMap.put("毕业时间", "graduateMonth");
		cnameToENameMap.put("工作年限", "jobExperienceYear");
		//选完简历、电话约一面，一面，一面不通过，电话约复试，复试不通过，复试通过
		cnameToENameMap.put("阶段", "interviewPhase");
		cnameToENameMap.put("电话约面试时间", "firstPhoneCallTime");
		cnameToENameMap.put("预约一面时间", "firstInterviewTime");
		cnameToENameMap.put("一面面试人", "firstInterviewOfficer");
		cnameToENameMap.put("一面评价", "firstIntervirewRemark");
		cnameToENameMap.put("预约复试时间", "secondInterviewTime");
		
		//将英文名对应为中文名
		for (Iterator<Entry<String, String>> it=cnameToENameMap.entrySet().iterator();it.hasNext();) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			
			enameToCNameMap.put(value, key);
		}
	}
	
	public static String colCNameToEName(String cname){
		return cnameToENameMap.get(cname);
	}
	
	public static String colENameToCName(String ename){
		return enameToCNameMap.get(ename);
	}
}
