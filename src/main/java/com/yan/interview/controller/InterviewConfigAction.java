package com.yan.interview.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yan.access.vo.UserMsgInfo;
import com.yan.interview.dao.InterviewConfigMongoDaoUtil;
import com.yan.interview.model.InterviewConfig;

public class InterviewConfigAction extends ActionSupport{

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

	private UserMsgInfo userMsgInfo;
	
	private InterviewConfigMongoDaoUtil interviewConfigMongoDaoUtil;
	
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

	public InterviewConfigMongoDaoUtil getInterviewConfigMongoDaoUtil() {
		return interviewConfigMongoDaoUtil;
	}

	public void setInterviewConfigMongoDaoUtil(InterviewConfigMongoDaoUtil interviewConfigMongoDaoUtil) {
		this.interviewConfigMongoDaoUtil = interviewConfigMongoDaoUtil;
	}

	public String interviewConfig(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession httpSession = request.getSession();
		String sessID = request.getSession().getId();
		//从session中获取userCode
		if(httpSession != null){
			if(httpSession.getAttribute(sessID) != null){
				userMsgInfo = (UserMsgInfo)httpSession.getAttribute(sessID);
			}
		}
		
		return "success";
	}
	
	public String findInterviewConfigs() {
    	
		String userCode = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession httpSession = request.getSession();
		String sessID = request.getSession().getId();
		//从session中获取userCode
		if(httpSession != null){
			if(httpSession.getAttribute(sessID) != null){
				userMsgInfo = (UserMsgInfo)httpSession.getAttribute(sessID);
				userCode = userMsgInfo.getUserCode();
			}
		}
		
    	Map<String, Object> map = new HashMap<String, Object>();
		
    	//HttpServletRequest request = ServletActionContext.getRequest();
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
		
		// 这个面试邮件模板归属于那个人员
		map.put("userCode", userCode);
		
		// 模板的名称
		String name = request.getParameter("name");
		map.put("name", name);
		
		// 面试邀请邮件抄送给谁
		String copyTo = request.getParameter("copyTo");
		map.put("copyTo", copyTo);
		
		// 面试邀请邮件的主题
		String subject = request.getParameter("subject");
		map.put("subject", subject);
		
		// 面试地点
		String interviewAddress = request.getParameter("interviewAddress");
		map.put("interviewAddress", interviewAddress);
		
		// 面试官称呼
		String interviewOfficerTitle = request.getParameter("interviewOfficerTitle");
		map.put("interviewOfficerTitle", interviewOfficerTitle);		
		
		// 面试官联系电话
		String interviewOfficerPhone = request.getParameter("interviewOfficerPhone");
		map.put("interviewOfficerPhone", interviewOfficerPhone);	
		
		String remark = request.getParameter("remark");
		map.put("remark", remark);	
		
    	//根据条件查询总条数
    	total = 0;
    	//查询结果
		List<InterviewConfig> interviewConfigs = interviewConfigMongoDaoUtil.findInterviewConfigDocumentsByCondition(map);
		//返回给前台的rows不能是null，否则前台js会报rows is null异常
//		List<InterviewConfigVo> interviewConfigVos = new ArrayList<InterviewConfigVo>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		rows = interviewConfigs;
    	total = interviewConfigMongoDaoUtil.countInterviewConfigDocumentsByCondition(map);
		
    	//下面这两个变量在这个方法中并不是必须的
		success = true;
		errorMsg = null;
		return "json";
    }
    
    public String findUniqueInterviewConfig() {
    	
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String id = request.getParameter("id");
    	
    	success = true;
    	errorMsg = null;
    	
    	InterviewConfig interviewConfig = null;
    	if(id != null && !"".equals(id.trim())) {
    		interviewConfig = interviewConfigMongoDaoUtil.findInterviewConfigById(id);
    	}
    	object = interviewConfig;
    	
    	return "json";
    }
    
    public String saveInterviewConfig() {
    	String userCode = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession httpSession = request.getSession();
		String sessID = request.getSession().getId();
		//从session中获取userCode
		if(httpSession != null){
			if(httpSession.getAttribute(sessID) != null){
				userMsgInfo = (UserMsgInfo)httpSession.getAttribute(sessID);
				userCode = userMsgInfo.getUserCode();
			}
		}
		
    	//HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
    	
    	String id = request.getParameter("id");
    	
		String editType = request.getParameter("editType");
		
		String name = request.getParameter("name");
		
		String copyTo = request.getParameter("copyTo");
		
		String subject = request.getParameter("subject");
		
		String interviewAddress = request.getParameter("interviewAddress");
		
		String interviewOfficerTitle = request.getParameter("interviewOfficerTitle");
		
		String interviewOfficerPhone = request.getParameter("interviewOfficerPhone");
		
		String emailSuffix = request.getParameter("emailSuffix");
    	
		String validStatus = request.getParameter("validStatus");
		
		String remark = request.getParameter("remark");
		
		InterviewConfig interviewConfig = new InterviewConfig();
		 
		interviewConfig.setId(id);
		interviewConfig.setUserCode(userCode);
		interviewConfig.setName(name);
		interviewConfig.setCopyTo(copyTo);
		interviewConfig.setSubject(subject);
		interviewConfig.setInterviewAddress(interviewAddress);
		interviewConfig.setInterviewOfficerTitle(interviewOfficerTitle);
		interviewConfig.setInterviewOfficerPhone(interviewOfficerPhone);
		interviewConfig.setEmailSuffix(emailSuffix);
		interviewConfig.setRemark(remark);
		
		if(editType != null && "new".equals(editType.trim())) {
			interviewConfig.setValidStatus("1");
			
			interviewConfig.setInsertTime(new Date());
			interviewConfig.setUpdateTime(new Date());
    		
			id = interviewConfigMongoDaoUtil.insertInterviewConfig(interviewConfig);
			interviewConfig.setId(id);
			object = interviewConfig;
		}else if (editType != null && "edit".equals(editType.trim())) {
			if(validStatus != null && !"".equals(validStatus.trim())){
				interviewConfig.setValidStatus(validStatus);
			}else{
				interviewConfig.setValidStatus("1");
			}
			
			interviewConfig.setUpdateTime(new Date());
    		
			interviewConfigMongoDaoUtil.updateInterviewConfig(interviewConfig);
			object = interviewConfig;
		}
		
		success = true;
		errorMsg = null;
    	
    	return "json";
    }
    
    public String deleteInterviewConfig() {
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String id = request.getParameter("id");
    	
    	success = true;
    	errorMsg = null;
    	
    	if(id != null && !"".equals(id.trim())){
    		
			//目前采用修改有效无效标志位的方式来标志删除
			interviewConfigMongoDaoUtil.updateValidStatus(id, "0");
    	}else{
    		success = false;
    		errorMsg = "缺少参数或请求数据不全！";
    	}
    	
    	return "json";
    }
    
}