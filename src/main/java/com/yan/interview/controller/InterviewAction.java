package com.yan.interview.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yan.interview.dao.InterviewMongoDaoUtil;
import com.yan.interview.model.Interview;
import com.yan.interview.vo.InterviewVo;

public class InterviewAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	/**
	 * 查询出来的总条数
	 * 分页插件需要
	 */
	private long total;
	
	/**
	 * 返回给页面的分页查询记录
	 * 分页插件需要
	 */
	private List rows;
	
	/**
	 * 成功失败标志位
	 * 前台和后台之间异步交互
	 */
	private boolean success;
	
	/**
	 * 错误信息
	 * 前台和后台之间异步交互
	 */
	private String errorMsg;

	
	private Object object;

	private InterviewMongoDaoUtil interviewMongoDaoUtil;
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public InterviewMongoDaoUtil getInterviewMongoDaoUtil() {
		return interviewMongoDaoUtil;
	}

	public void setInterviewMongoDaoUtil(InterviewMongoDaoUtil interviewMongoDaoUtil) {
		this.interviewMongoDaoUtil = interviewMongoDaoUtil;
	}

	public String interview(){
		
		return "success";
	}
	
	public String findInterviews() {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
		
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String page = request.getParameter("page");
		String r = request.getParameter("rows");
		
		if(page == null || "".equals(page.trim())){
			page = "1";
		}
		map.put("page", page);
		if(r == null || "".equals(r.trim())){
			r = "10";
		}
		map.put("rows", r);
		
		//有效状态
		String validStatus = request.getParameter("validStatus");
		map.put("validStatus", validStatus);
		
		//一面起始日期
		String firstInterviewTimeStart = request.getParameter("firstInterviewTimeStart");
		map.put("firstInterviewTimeStart", firstInterviewTimeStart);
		
		//一面结束日期
		String firstInterviewTimeEnd = request.getParameter("firstInterviewTimeEnd");
		map.put("firstInterviewTimeEnd", firstInterviewTimeEnd);
		
		//姓名
		String userName = request.getParameter("userName");
		map.put("userName", userName);
		
		//手机
		String phone = request.getParameter("phone");
		map.put("phone", phone);
		
		//邮箱
		String email = request.getParameter("email");
		map.put("email", email);
		
		//性别
		String genderCode = request.getParameter("genderCode");
		map.put("genderCode", genderCode);		
		
		//工作年数
		String jobExperienceYear = request.getParameter("jobExperienceYear");
		map.put("jobExperienceYear", jobExperienceYear);		

		//一面面试官
		String firstInterviewOfficer = request.getParameter("firstInterviewOfficer");
		map.put("firstInterviewOfficer", firstInterviewOfficer);	
		
		//毕业院校
		String university = request.getParameter("university");
		map.put("university", university);	
		
		
		//专业
		String major = request.getParameter("major");
		map.put("major", major);	
				
		//一面评价
		String firstIntervirewRemark = request.getParameter("firstIntervirewRemark");
		map.put("firstIntervirewRemark", firstIntervirewRemark);
		
		//面试阶段
		String interviewPhase = request.getParameter("interviewPhase");
		map.put("interviewPhase", interviewPhase);
		
    	//根据条件查询总条数
    	total = 0;
    	//查询结果
		List<Interview> interviews = interviewMongoDaoUtil.findInterviewDocumentsByCondition(map);
		//返回给前台的rows不能是null，否则前台js会报rows is null异常
//		List<InterviewVo> interviewVos = new ArrayList<InterviewVo>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		rows = interviews;
    	total = interviewMongoDaoUtil.countInterviewDocumentsByCondition(map);
		
    	//下面这两个变量在这个方法中并不是必须的
		success = true;
		errorMsg = null;
		return "json";
    }
    
    public String findUniqueInterview() {
    	
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String id = request.getParameter("id");
    	
    	success = true;
    	errorMsg = null;
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Interview interview = null;
    	if(id != null && !"".equals(id.trim())) {
    		interview = interviewMongoDaoUtil.findInterviewById(id);
    	}
    	object = interview;
    	
    	return "json";
    }
    
    public String saveInterview() {
    	Map<String, Object> map = new HashMap<String, Object>();
		
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
    	
    	String id = request.getParameter("id");
    	
		String editType = request.getParameter("editType");
		
		String userName = request.getParameter("userName");
		
		String userEName = request.getParameter("userEName");
		
		String genderCode = request.getParameter("genderCode");
		String genderName = request.getParameter("genderName");
		
		String phone = request.getParameter("phone");
		
		String email = request.getParameter("email");
		
		String birth = request.getParameter("birth");
		
		String university = request.getParameter("university");
    	
		String major = request.getParameter("major");
		
		String educationBackground = request.getParameter("educationBackground");
		
		String graduateMonth = request.getParameter("graduateMonth");
		
		String jobExperienceYear = request.getParameter("jobExperienceYear");
		
		String firstJob = request.getParameter("firstJob");
		
		String firstJobBeginMonth = request.getParameter("firstJobBeginMonth");
		
		String interviewPhase = request.getParameter("interviewPhase");
		
		String firstPhoneCallTime = request.getParameter("firstPhoneCallTime");
		
		String firstPhoneCallRemark = request.getParameter("firstPhoneCallRemark");
		
		String firstInterviewTime = request.getParameter("firstInterviewTime");
		
		String firstInterviewOfficer = request.getParameter("firstInterviewOfficer");
		
		String firstIntervirewRemark = request.getParameter("firstIntervirewRemark");
		
		String secondInterviewTime = request.getParameter("secondInterviewTime");
		
		Interview interview = new Interview();
		 
		interview.setId(id);
		interview.setUserName(userName);
		interview.setUserEName(userEName);
		interview.setGenderCode(genderCode);
		interview.setGenderName(genderName);
		interview.setPhone(phone);
		interview.setEmail(email);
		interview.setBirth(birth);
		interview.setUniversity(university);
		interview.setMajor(major);
		interview.setEducationBackground(educationBackground);
		interview.setGraduateMonth(graduateMonth);
		if(jobExperienceYear != null && !"".equals(jobExperienceYear.trim())) {
			interview.setJobExperienceYear(Integer.parseInt(jobExperienceYear));
		}
		interview.setFirstJob(firstJob);
		interview.setFirstJobBeginMonth(firstJobBeginMonth);
		interview.setInterviewPhase(interviewPhase);
		interview.setFirstPhoneCallTime(firstPhoneCallTime);
		interview.setFirstPhoneCallRemark(firstPhoneCallRemark);
		interview.setFirstInterviewTime(firstInterviewTime);
		interview.setFirstInterviewOfficer(firstInterviewOfficer);
		interview.setFirstIntervirewRemark(firstIntervirewRemark);
		interview.setSecondInterviewTime(secondInterviewTime);
		
		if(editType != null && "new".equals(editType.trim())) {
			interview.setInsertTime(new Date());
			interview.setUpdateTime(new Date());
    		
			id = interviewMongoDaoUtil.insertInterview(interview);
			interview.setId(id);
			object = interview;
		}else if (editType != null && "edit".equals(editType.trim())) {
			interview.setUpdateTime(new Date());
    		
			interviewMongoDaoUtil.updateInterview(interview);
			object = interview;
		}
		
		success = true;
		errorMsg = null;
    	
    	return "json";
    }
    
    public String deleteInterview() {
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String id = request.getParameter("id");
    	
    	success = true;
    	errorMsg = null;
    	
    	if(id != null && !"".equals(id.trim())){
    		
			//目前采用修改有效无效标志位的方式来标志删除
			interviewMongoDaoUtil.updateInterviewValidStatus(id, "0");
    	}else{
    		success = false;
    		errorMsg = "缺少参数或请求数据不全！";
    	}
    	
    	return "json";
    }
}