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
import com.yan.interview.dao.EmailConfigMongoDaoUtil;
import com.yan.interview.model.EmailConfig;

public class EmailConfigAction extends ActionSupport{

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
	
	private EmailConfigMongoDaoUtil emailConfigMongoDaoUtil;
	
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

	public EmailConfigMongoDaoUtil getEmailConfigMongoDaoUtil() {
		return emailConfigMongoDaoUtil;
	}

	public void setEmailConfigMongoDaoUtil(EmailConfigMongoDaoUtil emailConfigMongoDaoUtil) {
		this.emailConfigMongoDaoUtil = emailConfigMongoDaoUtil;
	}

	public String emailConfig(){
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
	
	public String findEmailConfigs() {
    	
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
		
		map.put("userCode", userCode);
		
		String name = request.getParameter("name");
		map.put("name", name);
		
		String smtpHostName = request.getParameter("smtpHostName");
		map.put("smtpHostName", smtpHostName);
		
		String remark = request.getParameter("remark");
		map.put("remark", remark);	
		
    	//根据条件查询总条数
    	total = 0;
    	//查询结果
		List<EmailConfig> emailConfigs = emailConfigMongoDaoUtil.findEmailConfigDocumentsByCondition(map);
		//返回给前台的rows不能是null，否则前台js会报rows is null异常
//		List<EmailConfigVo> emailConfigVos = new ArrayList<EmailConfigVo>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		rows = emailConfigs;
    	total = emailConfigMongoDaoUtil.countEmailConfigDocumentsByCondition(map);
		
    	//下面这两个变量在这个方法中并不是必须的
		success = true;
		errorMsg = null;
		return "json";
    }
    
    public String findUniqueEmailConfig() {
    	
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String id = request.getParameter("id");
    	
    	success = true;
    	errorMsg = null;
    	
    	EmailConfig emailConfig = null;
    	if(id != null && !"".equals(id.trim())) {
    		emailConfig = emailConfigMongoDaoUtil.findEmailConfigById(id);
    	}
    	object = emailConfig;
    	
    	return "json";
    }
    
    public String saveEmailConfig() {
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
		
		String smtpHostName = request.getParameter("smtpHostName");
		
		String smtpPort = request.getParameter("smtpPort");
		
		String sslOnConnect = request.getParameter("sslOnConnect");
		
		String mailUserName = request.getParameter("mailUserName");
		
		String mailUserPwd = request.getParameter("mailUserPwd");
		
		String validStatus = request.getParameter("validStatus");
		
		String remark = request.getParameter("remark");
		
		EmailConfig emailConfig = new EmailConfig();
		 
		emailConfig.setId(id);
		emailConfig.setUserCode(userCode);
		emailConfig.setName(name);
		emailConfig.setSmtpHostName(smtpHostName);
		emailConfig.setRemark(remark);
		
		if(smtpPort != null && !"".equals(smtpPort.trim())) {
			emailConfig.setSmtpPort(Integer.parseInt(smtpPort));
		}
		
		if(sslOnConnect != null && !"".equals(sslOnConnect.trim())) {
			emailConfig.setSslOnConnect(Boolean.parseBoolean(sslOnConnect));
		}
		
		emailConfig.setMailUserName(mailUserName);
		emailConfig.setMailUserPwd(mailUserPwd);
		
		
		if(editType != null && "new".equals(editType.trim())) {
			emailConfig.setValidStatus("1");
			
			emailConfig.setInsertTime(new Date());
			emailConfig.setUpdateTime(new Date());
    		
			id = emailConfigMongoDaoUtil.insertEmailConfig(emailConfig);
			emailConfig.setId(id);
			object = emailConfig;
		}else if (editType != null && "edit".equals(editType.trim())) {
			if(validStatus != null && !"".equals(validStatus.trim())){
				emailConfig.setValidStatus(validStatus);
			}else{
				emailConfig.setValidStatus("1");
			}
			
			emailConfig.setUpdateTime(new Date());
    		
			emailConfigMongoDaoUtil.updateEmailConfig(emailConfig);
			object = emailConfig;
		}
		
		success = true;
		errorMsg = null;
    	
    	return "json";
    }
    
    public String deleteEmailConfig() {
    	HttpServletRequest request = ServletActionContext.getRequest();
		//当前台传过来的变量userNames是一个数组的时候，通过request.getParameterValues("userNames[]");这种方式才能获取到这个数组
		//String[] userNames = request.getParameterValues("userNames[]");
		String id = request.getParameter("id");
    	
    	success = true;
    	errorMsg = null;
    	
    	if(id != null && !"".equals(id.trim())){
    		
			//目前采用修改有效无效标志位的方式来标志删除
			emailConfigMongoDaoUtil.updateValidStatus(id, "0");
    	}else{
    		success = false;
    		errorMsg = "缺少参数或请求数据不全！";
    	}
    	
    	return "json";
    }
    
}