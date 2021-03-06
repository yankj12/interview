package com.yan.interview.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yan.access.service.facade.UserAccessService;
import com.yan.access.vo.ResponseVo;
import com.yan.access.vo.UserMsgInfo;
import com.yan.interview.dao.InterviewMongoDaoUtil;
import com.yan.interview.model.Interview;
import com.yan.interview.service.facade.SendInterviewEmailService;
import com.yan.interview.vo.GroupCountAggregateVo;

public class InterviewAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(InterviewAction.class);
	
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

	private UserMsgInfo userMsgInfo;
	
	private InterviewMongoDaoUtil interviewMongoDaoUtil;
	
	private UserAccessService userAccessService;
	
	/** 
	 * 发送面试邮件
	 */
	private SendInterviewEmailService sendInterviewEmailService;
	
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

	public UserMsgInfo getUserMsgInfo() {
		return userMsgInfo;
	}

	public void setUserMsgInfo(UserMsgInfo userMsgInfo) {
		this.userMsgInfo = userMsgInfo;
	}

	public InterviewMongoDaoUtil getInterviewMongoDaoUtil() {
		return interviewMongoDaoUtil;
	}

	public void setInterviewMongoDaoUtil(InterviewMongoDaoUtil interviewMongoDaoUtil) {
		this.interviewMongoDaoUtil = interviewMongoDaoUtil;
	}

	public SendInterviewEmailService getSendInterviewEmailService() {
		return sendInterviewEmailService;
	}

	public void setSendInterviewEmailService(SendInterviewEmailService sendInterviewEmailService) {
		this.sendInterviewEmailService = sendInterviewEmailService;
	}

	public UserAccessService getUserAccessService() {
		return userAccessService;
	}

	public void setUserAccessService(UserAccessService userAccessService) {
		this.userAccessService = userAccessService;
	}

	public String interview(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String sessID = request.getSession().getId();
		// find tickets from cookies
		String ticket = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
            for (Cookie cookie : cookies) {
            	String name = cookie.getName();
            	if("ticket".equals(name)) {
            		ticket = cookie.getValue();
            		break;
            	}
            }
        }
		if(ticket == null || "".equals(ticket)) {
			ticket = sessID;
		}
		
		// 先检查下session中是否存在tickets
		ResponseVo responseVo;
		try {
			responseVo = userAccessService.getSession(ticket);
			if(responseVo != null && responseVo.isSuccess()){
				userMsgInfo = responseVo.getUserMsgInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
			userMsgInfo = new UserMsgInfo();
		}
		
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
		
		//电话约面试起始日期
		String firstPhoneCallTimeStart = request.getParameter("firstPhoneCallTimeStart");
		map.put("firstPhoneCallTimeStart", firstPhoneCallTimeStart);
		
		//电话约面试结束日期
		String firstPhoneCallTimeEnd = request.getParameter("firstPhoneCallTimeEnd");
		map.put("firstPhoneCallTimeEnd", firstPhoneCallTimeEnd);
		
		String firstPhoneCallOfficer = request.getParameter("firstPhoneCallOfficer");
		map.put("firstPhoneCallOfficer", firstPhoneCallOfficer);
		
		String firstInterviewTimePeriod = request.getParameter("firstInterviewTimePeriod");
		map.put("firstInterviewTimePeriod", firstInterviewTimePeriod);
		
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
		
		String firstInterviewAddress = request.getParameter("firstInterviewAddress");
		map.put("firstInterviewAddress", firstInterviewAddress);	
		
		//毕业院校
		String university = request.getParameter("university");
		map.put("university", university);	
		
		
		//专业
		String major = request.getParameter("major");
		map.put("major", major);	
				
		//一面评价
		String firstIntervirewRemark = request.getParameter("firstIntervirewRemark");
		map.put("firstIntervirewRemark", firstIntervirewRemark);
		
		// 一面邀请邮件是否发送
		String firstInterviewEmailSendFlag = request.getParameter("firstInterviewEmailSendFlag");
		map.put("firstInterviewEmailSendFlag", firstInterviewEmailSendFlag);
		
		//面试阶段
		String interviewPhase = request.getParameter("interviewPhase");
		map.put("interviewPhase", interviewPhase);
		
		String interviewEndFlag = request.getParameter("interviewEndFlag");
		map.put("interviewEndFlag", interviewEndFlag);
		
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
    	
    	Interview interview = null;
    	if(id != null && !"".equals(id.trim())) {
    		interview = interviewMongoDaoUtil.findInterviewById(id);
    	}
    	object = interview;
    	
    	return "json";
    }
    
    public String saveInterview() {
		
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
		
		String expectedSalary = request.getParameter("expectedSalary");
		
		String interviewPhase = request.getParameter("interviewPhase");
		
		String firstPhoneCallTime = request.getParameter("firstPhoneCallTime");
		
		String firstPhoneCallRemark = request.getParameter("firstPhoneCallRemark");
		
		String firstPhoneCallOfficer = request.getParameter("firstPhoneCallOfficer");
		
		String firstInterviewEmailSendFlag = request.getParameter("firstInterviewEmailSendFlag");
		
		String firstInterviewTime = request.getParameter("firstInterviewTime");
		
		String firstInterviewTimePeriod = request.getParameter("firstInterviewTimePeriod");
		
		String firstInterviewOfficer = request.getParameter("firstInterviewOfficer");
		
		String firstInterviewAddress = request.getParameter("firstInterviewAddress");
		
		String firstIntervirewRemark = request.getParameter("firstIntervirewRemark");
		
		String secondInterviewTime = request.getParameter("secondInterviewTime");
		
		String validStatus = request.getParameter("validStatus");
		
		String interviewEndFlag = request.getParameter("interviewEndFlag");
		
		String interviewMailText = request.getParameter("interviewMailText");
		
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
		interview.setExpectedSalary(expectedSalary);
		interview.setFirstPhoneCallTime(firstPhoneCallTime);
		interview.setFirstPhoneCallRemark(firstPhoneCallRemark);
		interview.setFirstPhoneCallOfficer(firstPhoneCallOfficer);
		interview.setFirstInterviewEmailSendFlag(firstInterviewEmailSendFlag);
		interview.setFirstInterviewTime(firstInterviewTime);
		interview.setFirstInterviewTimePeriod(firstInterviewTimePeriod);
		interview.setFirstInterviewOfficer(firstInterviewOfficer);
		interview.setFirstInterviewAddress(firstInterviewAddress);
		
		interview.setFirstIntervirewRemark(firstIntervirewRemark);
		interview.setSecondInterviewTime(secondInterviewTime);
		interview.setInterviewEndFlag(interviewEndFlag);
		interview.setInterviewMailText(interviewMailText);
		
		// 避免短时间内因为网络相应慢，多次点击进而多次保存的问题
		if(editType != null && "new".equals(editType.trim())) {
			Map<String, Object> condition = new HashMap<String, Object>();
			// 因为现在是软删除，所以判断重复的时候，只查询有效的数据
			condition.put("validStatus", "1");
			condition.put("phone", phone);
			condition.put("userName", userName);
			condition.put("email", email);
			// insertTime
			// 保存时，名称、电话、邮箱重复，10分钟内有新增加的记录时，则不新增记录，进行更新操作
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, -10);
			Date insertTime = calendar.getTime();
			condition.put("insertTimeStart", insertTime);
			List<Interview> interviews = interviewMongoDaoUtil.findInterviewDocumentsByCondition(condition);
			if(interviews != null && interviews.size() > 0){
				id = interviews.get(0).getId();
				interview.setId(id);
				editType = "edit";
			}
		}
		
		if(editType != null && "new".equals(editType.trim())) {
			interview.setValidStatus("1");
			
			interview.setInsertTime(new Date());
			interview.setUpdateTime(new Date());
    		
			id = interviewMongoDaoUtil.insertInterview(interview);
			interview.setId(id);
			object = interview;
		}else if (editType != null && "edit".equals(editType.trim())) {
			if(validStatus != null && !"".equals(validStatus.trim())){
				interview.setValidStatus(validStatus);
			}else{
				interview.setValidStatus("1");
			}
			
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
    
    public String sendInterviewEmail() {
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String ids = request.getParameter("ids");
		logger.debug("Action sendInterviewEmail,ids:" + ids);
		
    	success = true;
    	errorMsg = "";
    	
    	if(ids != null && !"".equals(ids.trim())){
    		
    		String[] idAry = ids.split(",");
    		
    		if(idAry != null && idAry.length > 0) {
    			for(int i=0;i<idAry.length;i++) {
    				String id = idAry[i];
    				// 根据id查询出面试人员信息
    				Interview interviewToSendEmail = interviewMongoDaoUtil.findInterviewById(id);
    				
    				// 调用发送邮件的方法
    				boolean result = sendInterviewEmailService.sendSingleInterviewEmail(interviewToSendEmail);
    				
    				if(result) {
    					// 更新面试人员信息中的 是否发送一面邮件字段  firstInterviewEmailSendFlag 0还未发送，1发送失败，2发送成功
    					interviewMongoDaoUtil.updateInterviewFirstInterviewEmailSendFlag(id, "2");
    					
    					success = true;
    		    		errorMsg += "[" + interviewToSendEmail.getUserName() + "]的面试邀请邮件发送成功！\n";
    				}else {
    					
    					// 更新面试人员信息中的 是否发送一面邮件字段  firstInterviewEmailSendFlag 0还未发送，1发送失败，2发送成功
    					interviewMongoDaoUtil.updateInterviewFirstInterviewEmailSendFlag(id, "1");
    					
    					success = false;
    		    		errorMsg += "[" + interviewToSendEmail.getUserName() + "]的面试邀请邮件发送失败！\n";
    				}
    			}
    		}
    	}else{
    		success = false;
    		errorMsg = "缺少参数或请求数据不全！";
    	}
    	
    	logger.debug("Action sendInterviewEmail end");
    	return "json";
    }
    
    public String breakInterviewAppointment() {
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String ids = request.getParameter("ids");
//		String field = request.getParameter("field");
//		String fieldValue = request.getParameter("fieldValue");
		
    	success = true;
    	errorMsg = "";
    	
    	if(ids != null && !"".equals(ids.trim())){
    		
    		String[] idAry = ids.split(",");
    		
    		if(idAry != null && idAry.length > 0) {
    			
    			Map<String, Object> map = new HashMap<>();
    			map.put("interviewPhase", "爽约");
    			map.put("interviewEndFlag", "1");
    			
    			for(int i=0;i<idAry.length;i++) {
    				String id = idAry[i];
    				interviewMongoDaoUtil.updateInterviewMultiFieldsById(id, map);
    			}
    			
    			success = true;
    			errorMsg = "更新成功！";
    		}
    	}else{
    		success = false;
    		errorMsg = "缺少参数或请求数据不全！";
    	}
    	
    	return "json";
    }
    
    public String endInterviews() {
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String ids = request.getParameter("ids");
//		String field = request.getParameter("field");
//		String fieldValue = request.getParameter("fieldValue");
		
    	success = true;
    	errorMsg = "";
    	
    	if(ids != null && !"".equals(ids.trim())){
    		
    		String[] idAry = ids.split(",");
    		
    		if(idAry != null && idAry.length > 0) {
    			for(int i=0;i<idAry.length;i++) {
    				String id = idAry[i];
    				interviewMongoDaoUtil.updateInterviewSingleFieldById(id, "interviewEndFlag", "1");
    			}
    			
    			success = true;
    			errorMsg = "更新成功！";
    		}
    	}else{
    		success = false;
    		errorMsg = "缺少参数或请求数据不全！";
    	}
    	
    	return "json";
    }
    
    public String interviewsGroupByfirstInterviewTimePeriod(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String sessID = request.getSession().getId();
		// find tickets from cookies
		String ticket = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
            for (Cookie cookie : cookies) {
            	String name = cookie.getName();
            	if("ticket".equals(name)) {
            		ticket = cookie.getValue();
            		break;
            	}
            }
        }
		if(ticket == null || "".equals(ticket)) {
			ticket = sessID;
		}
		
		// 先检查下session中是否存在tickets
		ResponseVo responseVo;
		try {
			responseVo = userAccessService.getSession(ticket);
			if(responseVo != null && responseVo.isSuccess()){
				userMsgInfo = responseVo.getUserMsgInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
			userMsgInfo = new UserMsgInfo();
		}
		
		return "success";
	}
    
    public String showInterviewsGroupByfirstInterviewTimePeriod() {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
		
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		
		//有效状态
		String validStatus = request.getParameter("validStatus");
		map.put("validStatus", validStatus);
		
		//一面起始日期
		String firstInterviewTimeStart = request.getParameter("firstInterviewTimeStart");
		map.put("firstInterviewTimeStart", firstInterviewTimeStart);
		
		//一面结束日期
		String firstInterviewTimeEnd = request.getParameter("firstInterviewTimeEnd");
		map.put("firstInterviewTimeEnd", firstInterviewTimeEnd);
		
		//电话约面试起始日期
		String firstPhoneCallTimeStart = request.getParameter("firstPhoneCallTimeStart");
		map.put("firstPhoneCallTimeStart", firstPhoneCallTimeStart);
		
		//电话约面试结束日期
		String firstPhoneCallTimeEnd = request.getParameter("firstPhoneCallTimeEnd");
		map.put("firstPhoneCallTimeEnd", firstPhoneCallTimeEnd);
		
		String firstPhoneCallOfficer = request.getParameter("firstPhoneCallOfficer");
		map.put("firstPhoneCallOfficer", firstPhoneCallOfficer);
		
		String firstInterviewTimePeriod = request.getParameter("firstInterviewTimePeriod");
		map.put("firstInterviewTimePeriod", firstInterviewTimePeriod);
		
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
		
		String firstInterviewAddress = request.getParameter("firstInterviewAddress");
		map.put("firstInterviewAddress", firstInterviewAddress);	
		
		//毕业院校
		String university = request.getParameter("university");
		map.put("university", university);	
		
		//专业
		String major = request.getParameter("major");
		map.put("major", major);	
		
		//一面评价
		String firstIntervirewRemark = request.getParameter("firstIntervirewRemark");
		map.put("firstIntervirewRemark", firstIntervirewRemark);
		
		// 一面邀请邮件是否发送
		String firstInterviewEmailSendFlag = request.getParameter("firstInterviewEmailSendFlag");
		map.put("firstInterviewEmailSendFlag", firstInterviewEmailSendFlag);
		
		//面试阶段
		String interviewPhase = request.getParameter("interviewPhase");
		map.put("interviewPhase", interviewPhase);
		
		String interviewEndFlag = request.getParameter("interviewEndFlag");
		map.put("interviewEndFlag", interviewEndFlag);
		
    	//查询结果
    	List<GroupCountAggregateVo> groups = interviewMongoDaoUtil.groupCountInterviewsByFirstInterviewTimePeriod(map);
		//返回给前台的rows不能是null，否则前台js会报rows is null异常
		
		rows = groups;
    	total = groups.size();
		
    	//下面这两个变量在这个方法中并不是必须的
		success = true;
		errorMsg = null;
		return "json";
    }
    
    public String checkInterviewUnique(){
    	
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
    	
    	// id
    	// 用于校验唯一性的时候排除本身
    	String id = request.getParameter("id");
    	
    	//姓名
		String userName = request.getParameter("userName");
		
		//手机
		String phone = request.getParameter("phone");
		
		//邮箱
		String email = request.getParameter("email");
    	
		// 电话、邮箱、姓名不存在重复用户，success为true，否则为false
		success = true;
    	errorMsg = null;
    	
    	Interview interview = null;
    	// 判断名称是否唯一
    	if(userName != null && !"".equals(userName.trim())){
    		interview = interviewMongoDaoUtil.findInterviewByUserName(userName);
    		if(interview != null){
    			if(id != null && !"".equals(id.trim())){
    				// 传了id并且id和查出来的面试信息的id相同，这种情况不算已存在。校验唯一性要排除本身
    				// 严谨的做法是在查询语句中排除id所在的记录
    				if(interview.getId() != null && interview.getId().trim().equals(id.trim())){
    					//是本身
    					object = null;
        				success = true;
        				errorMsg = null;
    				}else{
    					// 不是本身，说明存在
        				object = interview;
        				success = false;
        				errorMsg = "名称已存在";
    				}
    			}else{
    				// 没有传id
    				object = interview;
    				success = false;
    				errorMsg = "名称已存在";
    			}
    		}
    	}
    	// 判断电话是否唯一
    	if(phone != null && !"".equals(phone.trim())){
    		interview = interviewMongoDaoUtil.findInterviewByPhone(phone);
    		if(interview != null){
    			if(id != null && !"".equals(id.trim())){
    				// 传了id并且id和查出来的面试信息的id相同，这种情况不算已存在。校验唯一性要排除本身
    				// 严谨的做法是在查询语句中排除id所在的记录
    				if(interview.getId() != null && interview.getId().trim().equals(id.trim())){
    					//是本身
    					object = null;
        				success = true;
        				errorMsg = null;
    				}else{
    					// 不是本身，说明存在
        				object = interview;
        				success = false;
        				errorMsg = "电话已存在";
    				}
    			}else{
    				// 没有传id
    				object = interview;
    				success = false;
    				errorMsg = "电话已存在";
    			}
	    	}
    	}
    	// 判断邮箱是否唯一
    	if(email != null && !"".equals(email.trim())){
    		interview = interviewMongoDaoUtil.findInterviewByEmail(email);
    		if(interview != null){
    			if(id != null && !"".equals(id.trim())){
    				// 传了id并且id和查出来的面试信息的id相同，这种情况不算已存在。校验唯一性要排除本身
    				// 严谨的做法是在查询语句中排除id所在的记录
    				if(interview.getId() != null && interview.getId().trim().equals(id.trim())){
    					//是本身
    					object = null;
        				success = true;
        				errorMsg = null;
    				}else{
    					// 不是本身，说明存在
        				object = interview;
        				success = false;
        				errorMsg = "邮箱已存在";
    				}
    			}else{
    				// 没有传id
    				object = interview;
    				success = false;
    				errorMsg = "邮箱已存在";
    			}
    		}
    	}
    	
    	return "json";
    }
}