package com.yan.interview.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yan.interview.dao.InterviewMongoDaoUtil;
import com.yan.interview.model.Interview;
import com.yan.interview.util.SchameCopyUtil;
import com.yan.interview.vo.InterviewVo;
import com.yan.interview.vo.ResultVo;

@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,  
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,  
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")  
@RestController
public class InterviewController {

    @RequestMapping(value="/findInterviews", method=RequestMethod.POST)
    public ResultVo findInterviews(@RequestParam Map<String, Object> map) {
    	ResultVo queryResultVo = new ResultVo();
    	
    	//根据条件查询总条数
    	long total = 0;
    	//查询结果
    	InterviewMongoDaoUtil interviewMongoDaoUtil = new InterviewMongoDaoUtil();
		List<Interview> interviews = interviewMongoDaoUtil.findInterviewDocumentsByCondition(map);
		//返回给前台的rows不能是null，否则前台js会报rows is null异常
		List<InterviewVo> interviewVos = new ArrayList<InterviewVo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(interviews != null && interviews.size() > 0) {
			//interviewVos = new ArrayList<InterviewVo>();
			for(Interview interview:interviews){
				InterviewVo interviewVo = (InterviewVo)SchameCopyUtil.simpleCopy(interview, InterviewVo.class);
				
				interviewVos.add(interviewVo);
			}
		}
    	total = interviewMongoDaoUtil.countInterviewDocumentsByCondition(map);
		
    	queryResultVo.setTotal(total);
    	//返回给前台的rows不能是null，否则前台js会报rows is null异常
    	queryResultVo.setRows(interviewVos);
        return queryResultVo;
    }
    
    @RequestMapping("/findUniqueInterview")
    public ResultVo findUniqueInterview(@RequestParam(value="id") String id) {
    	boolean success = true;
    	String errorMsg = null;
    	ResultVo resultVo = new ResultVo();
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	InterviewVo interviewVo = null;
    	if(id != null && !"".equals(id.trim())) {
    		InterviewMongoDaoUtil interviewMongoDaoUtil = new InterviewMongoDaoUtil();
    		Interview interview = interviewMongoDaoUtil.findInterviewById(id);
    		if(interview != null) {
    			interviewVo = (InterviewVo)SchameCopyUtil.simpleCopy(interview, InterviewVo.class);
    		}
    	}
    	
    	resultVo.setObject(interviewVo);
    	resultVo.setErrorMsg(errorMsg);
    	resultVo.setSuccess(success);
    	return resultVo;
    }
    
    @RequestMapping("/saveInterview")
    public ResultVo saveInterview(@RequestParam Map<String, Object> map) {

    	boolean success = true;
    	String errorMsg = null;
    	ResultVo resultVo = new ResultVo();
    	
    	if(map != null){
    		Interview interview = new Interview();
    		interview.setId((String)map.get("id"));
//    		interview.setDay((String)map.get("day"));
//    		interview.setTitle((String)map.get("title"));
//    		interview.setType((String)map.get("type"));
//    		interview.setProjectName((String)map.get("projectName"));
//    		interview.setProjectCode((String)map.get("projectCode"));
//    		interview.setWriterName((String)map.get("writerName"));
//    		interview.setWorkText((String)map.get("workText"));
//			
//    		interview.setValidStatus("1");
    		
    		String editType = (String)map.get("editType");
    		
			InterviewMongoDaoUtil interviewMongoDaoUtil = new InterviewMongoDaoUtil();
    		if(editType != null && "new".equals(editType.trim())) {
    			interview.setInsertTime(new Date());
    			interview.setUpdateTime(new Date());
        		
    			String id = interviewMongoDaoUtil.insertInterview(interview);
    			interview.setId(id);
				resultVo.setObject(interview);
    		}else if (editType != null && "edit".equals(editType.trim())) {
    			interview.setUpdateTime(new Date());
        		
    			interviewMongoDaoUtil.updateInterview(interview);
				resultVo.setObject(interview);
			}
    	}else{
    		success = false;
    		errorMsg = "缺少参数或请求数据不全！";
    	}
    	
    	resultVo.setErrorMsg(errorMsg);
    	resultVo.setSuccess(success);
    	return resultVo;
    }
    
//    @RequestMapping("/deleteInterview")
//    public ResultVo deleteInterview(@RequestParam(value="id") String id) {
//    	boolean success = true;
//    	String errorMsg = null;
//    	ResultVo resultVo = new ResultVo();
//    	
//    	if(id != null && !"".equals(id.trim())){
//    		
//			InterviewMongoDaoUtil interviewMongoDaoUtil = new InterviewMongoDaoUtil();
//			//目前采用修改有效无效标志位的方式来标志删除
////			interviewMongoDaoUtil.updateInterviewValidStatus(id, "0");
//    	}else{
//    		success = false;
//    		errorMsg = "缺少参数或请求数据不全！";
//    	}
//    	
//    	resultVo.setErrorMsg(errorMsg);
//    	resultVo.setSuccess(success);
//    	return resultVo;
//    }
}