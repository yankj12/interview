var url;

/**
 * 页面初始化后做些东西
 */
$(document).ready(function(){
	console.log('init');
	
});


//通过让数据表格url为调用js方法，这样可以让不管是分页插件还是查询条件点击下一页，都可以将查询条件和分页信息同时传到后台action
function exeQuery(){
	var data = $('#conditionForm').serializeArray();//先进行序列化数组操作
    var postData = {};  //创建一个对象  
    $.each(data, function(n,v) {
    	//postData[data[n].name]=data[n].value;  //循环数组，把数组的每一项都添加到对象中
    	//上面这种方式，在遇到多个同名input的时候，会进行覆盖操作，导致后台只能获取到最后一个元素
    	
        //如果有多个input的name重名，那么postData[data[n].name]存储的应该是一个数组
        if(postData[data[n].name]){
        	//已经存在的话
        	//有可能其中存储的是单个变量，也有可能存储的是个数组
        	var value = postData[data[n].name];
        	//$.isArray(value);  //jquery的方式判断是否为数组
        	if(value instanceof Array){
        		value.push(data[n].value);
        	}else{
        		var perviewElement = postData[data[n].name];
        		var ary = new Array();
        		//先应该将上一个元素添加到数组中
        		ary.push(perviewElement);
        		ary.push(data[n].value);
        		postData[data[n].name]=ary;
        	}
        }else{
        	//没有存在，那么我们当做单个变量来处理
        	postData[data[n].name]=data[n].value;  //循环数组，把数组的每一项都添加到对象中
        }
    });
	//通过设置datagrid的queryParams，可以将数据表格的参数带到后台
    //此处并不需要专门给url赋值，因为使用的是表单的url
    $('#dg').datagrid({
		//queryParams: {
		//	userNames: '张三'
		//}
		queryParams:postData
	}); 
}

function newRecord(title){
	//打开新的标签，在新的标签中进行添加操作
	//addTab(title,'leave/editLeaveApplication?editType=new');
	
	$('#dlg').dialog('open').dialog('setTitle','编写面试人员信息');
	$('#fm').form('clear');
	//设置修改类型，否则action中保存方法不知道是什么修改类型
	$('#editType_edit').val("new");

	// 默认新增用户是有效状态
	$('#validStatus_edit').val("1");
	
	//给日期赋默认值yyyyMMdd
	var date = new Date();
	
	// 性别默认选择男
	$('#genderCode_edit').combobox('setValue', 'M');
	//$("#genderName_edit").val('男');    // 已经不需要给genderName赋值了
	
	// 给电话约面试时间赋值
	$('#firstPhoneCallTime_edit').datetimebox('setValue', myDateFormatter(date));
	
	// 学历赋默认值统招本科
	$('#educationBackground_edit').combobox('setValue', '统招本科');
	
	// 面试阶段赋默认值，选完简历
	$('#interviewPhase_edit').combobox('setValue', '选完简历');
	
	// 工作年限赋默认值2
	$('#jobExperienceYear_edit').textbox('setValue', 2);
	
	// 一面面试官赋默认值 当前登录用户
	var loginUserName = $("#loginUserName_edit").val();
	$('#firstInterviewOfficer_edit').textbox('setValue', loginUserName);
	// 电话邀约人赋默认值 当前登录用户
	$('#firstPhoneCallOfficer_edit').textbox('setValue', loginUserName);
	
	// 给“一面邀请邮件是否发送”字段赋默认值“还未发送”
	$('#firstInterviewEmailSendFlag_edit').combobox('setValue', '0');
	
	$('#interviewEndFlag_edit').combobox('setValue', '0');
	
	// 每次新增的时候，先把姓名、电话、邮箱后面的图标清空，否则默认显示的是重复的图标
	$('#label_after_userName').html('');
	$('#label_after_phone').html('');
	$('#label_after_email').html('');
	
	// 每次新增的时候，将简历邮件内容初始化，否则会展示上一次编辑的其他人的内容
	$('#interviewMailText_edit').textbox('setValue', '');
}


function editRecord(title){
	
	var row = $('#dg').datagrid('getSelected');    //这一步可以改造为从后台异步获取数据
	
	if(row != null){
		var id = row.id;
		
		//异步从action中加载数据
		$.ajax({
	        type:"GET", 
	        url:contextRootPath + "/interview/findUniqueInterview.do?id=" + id,
	        //url:"leave/saveLeaveApplication?editType=新增",
	        dataType:"json", 
	        //data:postData,
	        contentType: "text/html;charset=UTF-8", 
	        success:function(result){
	        	if (result.success){
					
					//先打开界面
					$('#dlg').dialog('open').dialog('setTitle','编写面试人员信息');
					$('#fm').form('clear');

					//为一些属性赋默认值
	        		var interview = result.object;
					
					$("#id_edit").val(interview.id);
					
	        		//设置修改类型，否则action中保存方法不知道是什么修改类型
	        		$('#editType_edit').val("edit");
	        		
	        		//
	        		$('#userName_edit').textbox('setValue', interview.userName);
	        		
	        		$('#userEName_edit').textbox('setValue', interview.userEName);
	        		
	        		$('#genderCode_edit').combobox('setValue', interview.genderCode);
	        		//$("#genderName_edit").val(interview.genderName);
	        		
	        		
	        		$('#birth_edit').textbox('setValue', interview.birth);
	        		
	        		$('#phone_edit').textbox('setValue', interview.phone);
	        		
	        		$('#email_edit').textbox('setValue', interview.email);
	        		
	        		$('#university_edit').textbox('setValue', interview.university);
	        		
	        		$('#major_edit').textbox('setValue', interview.major);
	        		
	        		$('#educationBackground_edit').combobox('setValue', interview.educationBackground);
	        		
	        		$('#graduateMonth_edit').textbox('setValue', interview.graduateMonth);
	        		
	        		$('#jobExperienceYear_edit').textbox('setValue', interview.jobExperienceYear);
	        		
	        		$('#expectedSalary_edit').textbox('setValue', interview.expectedSalary);
	        		
	        		$('#interviewPhase_edit').combobox('setValue', interview.interviewPhase);
	        		
	        		$('#firstPhoneCallTime_edit').datetimebox('setValue', formatDateTimeString(interview.firstPhoneCallTime));
	        		
	        		$('#firstPhoneCallRemark_edit').textbox('setValue', interview.firstPhoneCallRemark);
	        		
	        		$('#firstPhoneCallOfficer_edit').textbox('setValue', interview.firstPhoneCallOfficer);
	        		
	        		$('#firstInterviewEmailSendFlag_edit').combobox('setValue', interview.firstInterviewEmailSendFlag);
	        		
	        		$('#firstInterviewTime_edit').datetimebox('setValue', formatDateTimeString(interview.firstInterviewTime));
	        		
	        		$('#firstInterviewOfficer_edit').textbox('setValue', interview.firstInterviewOfficer);
	        		
	        		$('#firstInterviewAddress_edit').textbox('setValue', interview.firstInterviewAddress);
	        		
	        		$('#firstIntervirewRemark_edit').textbox('setValue', interview.firstIntervirewRemark);
	        		
	        		$('#secondInterviewTime_edit').datetimebox('setValue', formatDateTimeString(interview.secondInterviewTime));
	        		
	        		$('#interviewEndFlag_edit').combobox('setValue', interview.interviewEndFlag);
	        		
	        		$('#interviewMailText_edit').textbox('setValue', interview.interviewMailText);
	        		
	        	}else{
	        		$.messager.alert('提示',result.errorMsg);
	        	}
	        },
	       	failure:function (result) {  
	       		//(提示框标题，提示信息)
	    		$.messager.alert('提示','加载失败');
	       	}
		});
	}else{
		//alert("请选择一条记录进行修改");
		//(提示框标题，提示信息)
		$.messager.alert('提示','请选择一条记录进行修改');
	}
}


function destroyRecord(){
	var rows = $('#dg').datagrid('getSelections');
	if (rows != null && rows.length != null && rows.length > 1){
		$.messager.alert('提示','不允许选择多条记录进行修改');
		return false;
	}
	
	var row = $('#dg').datagrid('getSelected');
	if (row){
		$.messager.confirm('Confirm','确定删除这条记录吗？',function(r){
			if (r){
				$.post(contextRootPath + '/interview/deleteInterview.do',{id:row.id},function(result){
					if (result.success){
						$('#dg').datagrid('reload');	// reload the user data
					} else {
						$.messager.show({	// show error message
							title: 'Error',
							msg: result.errorMsg
						});
					}
				},'json');
			}
		});
	}
}


function saveRecord(){
	var editType = $('#editType_edit').val();
	var id = $('#id_edit').val();

	var userName = $('#userName_edit').textbox('getValue');
	var userEName = $('#userEName_edit').textbox('getValue');
	var genderCode = $('#genderCode_edit').combobox('getValue');
	var genderName = $("#genderName_edit").val();
	var birth = $('#birth_edit').textbox('getValue');
	var phone = $('#phone_edit').textbox('getValue');
	var email = $('#email_edit').textbox('getValue');
	var university = $('#university_edit').textbox('getValue');
	var major = $('#major_edit').textbox('getValue');
	var educationBackground = $('#educationBackground_edit').combobox('getValue');
	var graduateMonth = $('#graduateMonth_edit').textbox('getValue');
	var jobExperienceYear = $('#jobExperienceYear_edit').textbox('getValue');
	var expectedSalary = $('#expectedSalary_edit').textbox('getValue');
	var interviewPhase = $('#interviewPhase_edit').combobox('getValue');
	var firstPhoneCallTime = $('#firstPhoneCallTime_edit').datetimebox('getValue');
	var firstPhoneCallRemark = $('#firstPhoneCallRemark_edit').textbox('getValue');
	var firstPhoneCallOfficer = $('#firstPhoneCallOfficer_edit').textbox('getValue');
	var firstInterviewEmailSendFlag = $('#firstInterviewEmailSendFlag_edit').combobox('getValue');
	var firstInterviewTime = $('#firstInterviewTime_edit').datetimebox('getValue');
	var firstInterviewTimePeriod = transDateTimeToPerioid(firstInterviewTime);
	
	var firstInterviewOfficer = $('#firstInterviewOfficer_edit').textbox('getValue');
	var firstInterviewAddress = $('#firstInterviewAddress_edit').textbox('getValue');
	var firstIntervirewRemark = $('#firstIntervirewRemark_edit').textbox('getValue');
	var secondInterviewTime = $('#secondInterviewTime_edit').datetimebox('getValue');
	var interviewEndFlag = $('#interviewEndFlag_edit').combobox('getValue');
	var interviewMailText = $('#interviewMailText_edit').textbox('getValue');
	
	var requestVo = new Object();
	requestVo.editType = editType;
	requestVo.id = id;
	
	requestVo.userName = userName;
	requestVo.userEName = userEName;
	requestVo.genderCode = genderCode;
	if(genderCode == 'M'){
		requestVo.genderName = '男';
	}else if(genderCode == 'F'){
		requestVo.genderName = '女';
	}
	
	requestVo.birth = birth;
	requestVo.phone = phone;
	requestVo.email = email;
	requestVo.university = university;
	requestVo.major = major;
	requestVo.educationBackground = educationBackground;
	requestVo.graduateMonth = graduateMonth;
	requestVo.jobExperienceYear = jobExperienceYear;
	requestVo.interviewPhase = interviewPhase;
	requestVo.expectedSalary = expectedSalary;
	requestVo.firstPhoneCallTime = firstPhoneCallTime;
	requestVo.firstPhoneCallRemark = firstPhoneCallRemark;
	requestVo.firstPhoneCallOfficer = firstPhoneCallOfficer
	requestVo.firstInterviewEmailSendFlag = firstInterviewEmailSendFlag;
	requestVo.firstInterviewTime = firstInterviewTime;
	requestVo.firstInterviewTimePeriod = firstInterviewTimePeriod;
	requestVo.firstInterviewOfficer = firstInterviewOfficer;
	requestVo.firstInterviewAddress = firstInterviewAddress;
	requestVo.firstIntervirewRemark = firstIntervirewRemark;
	requestVo.secondInterviewTime = secondInterviewTime;
	requestVo.interviewEndFlag = interviewEndFlag;
	requestVo.interviewMailText = interviewMailText;
	
	$.post(contextRootPath + '/interview/saveInterview.do', requestVo, function(result){
		if (result.success){
			//$.messager.alert('提示',result.errorMsg);
			
			//此处会写不会写id关系不大了，因为下面会直接进行close
			// var workReport = result.object;
			// var id = workReport.id;
			// $('#id_edit').val(id);
			// $('#editType_edit').val('edit');
			
			$('#dlg').dialog('close');
			$('#dg').datagrid('reload');	// reload the user data
		} else {
			$.messager.show({	// show error message
				title: 'Error',
				msg: result.errorMsg
			});
		}
	},'json');

}


function sendInterviewEmail(){
	var rows = $('#dg').datagrid('getSelections');
	if (rows != null && rows.length != null && rows.length > 0){
		
		$.messager.confirm('请确认！','确认要向这几位面试者发送一面面试邀请邮件吗？',function(r){
		    if (r){
				var ids = "";
				for (var i = 0; i < rows.length; i++) {  
					if (ids == '') {  
						ids = rows[i].id;  
					} else {  
						ids += ',' + rows[i].id;  
					}
				}
				
				$.post(contextRootPath + '/interview/sendInterviewEmail.do',{ids:ids},function(result){
					if (result.success){
						$.messager.alert('提示','面试邀请邮件发送成功！');
						$('#dg').datagrid('reload');	// reload the user data
					} else {
						$.messager.show({	// show error message
							title: 'Error',
							msg: result.errorMsg
						});
					}
				},'json');
		    }
		});
		
	}else{
		//(提示框标题，提示信息)
		$.messager.alert('提示','请至少选择一条记录');
	}
	
}


function endInterviews(){
	var rows = $('#dg').datagrid('getSelections');
	if (rows != null && rows.length != null && rows.length > 0){
		
		var ids = "";
		
		// 未结束的面试数量
		var unEndedInterviewCount = 0;
		
        for (var i = 0; i < rows.length; i++) {  
            if (ids == '') {  
            	ids = rows[i].id;  
            } else {  
            	ids += ',' + rows[i].id;  
            }
            
            if(rows[i].interviewEndFlag == null || rows[i].interviewEndFlag != '1'){
            	unEndedInterviewCount++;
            }
        }

        if(unEndedInterviewCount == 0){
        	$.messager.alert('提示','至少选择一条未结束的面试记录！');
        	return false;
        }
        
        $.messager.confirm('请确认！','确认要将这几条面试信息置为已结束吗？',function(r){
		    if (r){
		    	$.post(contextRootPath + '/interview/endInterviews.do',{ids:ids},function(result){
		    		if (result.success){
		    			$.messager.alert('提示','成功将选中面试置为结束状态！');
		    			$('#dg').datagrid('reload');	// reload the user data
		    		} else {
		    			$.messager.show({	// show error message
		    				title: 'Error',
		    				msg: result.errorMsg
		    			});
		    		}
		    	},'json');
		    }
		});
        
	}else{
		//(提示框标题，提示信息)
		$.messager.alert('提示','请至少选择一条记录');
	}
}

function breakInterviewAppointment(){
	var rows = $('#dg').datagrid('getSelections');
	if (rows != null && rows.length != null && rows.length > 0){
		var ids = "";
		
		// 没有爽约的面试数量
		var unBreakAppointmentCount = 0;
		
        for (var i = 0; i < rows.length; i++) {  
            if (ids == '') {  
            	ids = rows[i].id;  
            } else {  
            	ids += ',' + rows[i].id;  
            }
            
            if(rows[i].interviewPhase == null || rows[i].interviewPhase != '爽约'){
            	unBreakAppointmentCount++;
            }
        }

        if(unBreakAppointmentCount == 0){
        	$.messager.alert('提示','您选择的记录都是爽约状态，不需要再次更改状态！');
        	return false;
        }
        
        $.post(contextRootPath + '/interview/breakInterviewAppointment.do',{ids:ids},function(result){
			if (result.success){
				$.messager.alert('提示','成功更新选中面试记录的状态！');
				$('#dg').datagrid('reload');	// reload the user data
			} else {
				$.messager.show({	// show error message
					title: 'Error',
					msg: result.errorMsg
				});
			}
		},'json');
		return false;
	}else{
		//(提示框标题，提示信息)
		$.messager.alert('提示','请至少选择一条记录');
	}
}

/**
 * 格式化出生年月为 yyyy/MM
 * @returns
 */
function formatBirth(){
	var dateStr = $('#birth_edit').textbox('getValue');
	var newStr = formatYearMonth(dateStr);
	$('#birth_edit').textbox('setValue', newStr);
}

/**
 * 格式化毕业年月为 yyyy/MM
 * @returns
 */
function formatGraduateMonth(){
	var dateStr = $('#graduateMonth_edit').textbox('getValue');
	var newStr = formatYearMonth(dateStr);
	$('#graduateMonth_edit').textbox('setValue', newStr);
}

/**
 * 将日期格式化为yyyy/MM的格式
 * @returns
 */
function formatYearMonth(dateStr){
	
	if(dateStr == null || dateStr== ''){
		return '';
	}
	
	var reg = /(\d+)/g;
	var r = dateStr.match(reg);
	if(r != null){
		// r[0] 表示匹配到的全体
		var y = parseInt(r[0],10);
		var m = parseInt(r[1],10);
		
		var newStr = '' + y + '/' + (m<10?('0'+m):m);
		return newStr;
	}
}


function updateInterviewEndFlag(newValue , oldValue){
	
	if(newValue == '电话筛除' || newValue == '爽约' || newValue == '一面不通过' || newValue == '复试不通过' || newValue == '未入职' || newValue == '已入职'){
		// 面试结束的情况
		$('#interviewEndFlag_edit').combobox('setValue', '1');
	}else{
		// 面试未结束的情况
		$('#interviewEndFlag_edit').combobox('setValue', '0');
	}
}


function checkUserName(){
	var id = $('#id_edit').val();
	var userName = $('#userName_edit').textbox('getValue');
	
	if(userName != null && $.trim(userName) != ''){
		// 进行trim操作
		userName = $.trim(userName);
		$('#userName_edit').textbox('setValue', userName);
		
		$.post(contextRootPath + '/interview/checkInterviewUnique.do',{id:id, userName:userName},function(result){
			if (result.success){
				// 是唯一的
				$('#label_after_userName').html('');
			} else {
				// 已经被使用
				imgUrl = contextRootPath + '/easyui/themes/usericons/warning.png';
				$('#label_after_userName').html('<img src="' + imgUrl + '" width="16" height="16" alt="已存在" title="已存在"/>');
			}
		},'json');
	}else{
		$('#label_after_userName').html('');
	}
}

function checkPhone(){
	var id = $('#id_edit').val();
	var phone = $('#phone_edit').textbox('getValue');
	
	if(phone != null && $.trim(phone) != ''){
		// 进行trim操作
		phone = $.trim(phone);
		$('#phone_edit').textbox('setValue', phone);
		
		$.post(contextRootPath + '/interview/checkInterviewUnique.do',{id:id, phone:phone},function(result){
			if (result.success){
				// 是唯一的
				$('#label_after_phone').html('');
			} else {
				// 已经被使用
				imgUrl = contextRootPath + '/easyui/themes/usericons/warning.png';
				$('#label_after_phone').html('<img src="' + imgUrl + '" width="16" height="16" alt="已存在" title="已存在"/>');
			}
		},'json');
	}else{
		$('#label_after_phone').html('');
	}
}

function checkEmail(){
	var id = $('#id_edit').val();
	var email = $('#email_edit').textbox('getValue');
	
	if(email != null && $.trim(email) != ''){
		// 进行trim操作
		email = $.trim(email);
		$('#email_edit').textbox('setValue', email);
		
		$.post(contextRootPath + '/interview/checkInterviewUnique.do',{id:id, email:email},function(result){
			if (result.success){
				// 是唯一的
				$('#label_after_email').html('');
			} else {
				// 已经被使用
				imgUrl = contextRootPath + '/easyui/themes/usericons/warning.png';
				$('#label_after_email').html('<img src="' + imgUrl + '" width="16" height="16" alt="已存在" title="已存在"/>');
			}
		},'json');
	}else{
		$('#label_after_email').html('');
	}
}

/**
 * 毕业院校字段onchange时触发的方法
 * @returns
 */
function onChangeUniversity(){
	var university = $('#university_edit').textbox('getValue');
	if(university != null && university != ''){
		university = $.trim(university);
		$('#university_edit').textbox('setValue', university);
	}
	
}

/**
 * 专业字段onchange时触发的方法
 * @returns
 */
function onChangeMajor(){
	var major = $('#major_edit').textbox('getValue');
	if(major != null && major != ''){
		major = $.trim(major);
		$('#major_edit').textbox('setValue', major);
	}
}

function setWorkReportTitle(){
	var day = $('#day_edit').textbox('getValue');
	if(day.length >= 8){
		var y = parseInt(day.substring(0,4),10);
		var m = parseInt(day.substring(4,6),10);
		var d = parseInt(day.substring(6,8),10);

		var type = $('#type_edit').textbox('getValue');
		
		//标题前缀，默认是日期yyyy-MM-dd
		var titlePreFix = ''+y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		//标题后缀
		var titleSuffix = '每日工作计划';
		if(type != null && type == 'person'){
			titleSuffix = '工作日志';
		}
		var title =  titlePreFix + titleSuffix;
		$('#title_edit').textbox('setValue', title);
	}
}

function setWorkReportProjectName(){
	var projectCode = $('#projectCode_edit').textbox('getValue');
	if(projectCode != null && projectCode.length > 0  && projectCodeToNameMap != null
		&& projectCodeToNameMap[projectCode] != null ){
		$('#projectName_edit').textbox('setValue', projectCodeToNameMap[projectCode]);
	}
}

/**
 * 去除工作日志中每行行首的空白符
 */
function indentRowText(){
	var workText = $('#workText_edit').textbox('getValue');
	var newWorkText = '';
	var rows = workText.split(/[\r\n]/g);
	for(var i=0;i<rows.length;i++){
		rows[i] = rows[i].replace(/^\s*/, '');
		newWorkText += rows[i];
		
		// 加上换行符
		if(i<rows.length-1){
			newWorkText += '\n';
		}
	}
	//alert(newWorkText);
	$('#workText_edit').textbox('setValue', newWorkText);
}

function resetConditionForm(){
	//JQuery中没有reset方法,需要通过JQuery来重置表单呢
	//或者使用dom的方法来做 document.getElementById
	$("#conditionForm")[0].reset();
}

/**
 * attendanceDay控件选择日期后，当点中某一天的时候会触发onSelect事件
 * @param date
 */
function setStatisticsMonthByDay(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	
	//经过多次尝试，在js中给easyui的textbox赋值，需要向下面这样进行
	//尽量通过id的方式获取，可以选择到easyui的textbox，如果通过其他选择器，那么只能选中textbox组件的某一部分
	$("#statisticsMonth_edit").combobox('setValue',''+y+(m<10?('0'+m):m));
}

/**
 * 下拉列表控件选择option后，会触发onSelect事件
 * @param date
 */
function setUserNameByCode(s){
	var name = s.label;
	//如下方式set值
	$('#userName_edit').combobox('setValue', name);
}

/**
 * 下拉列表控件选择option后，会触发onSelect事件
 * @param date
 */
function selectUserCodeByName(s){
	var name = s.label;
	var list = $('#userCode_edit').combobox('getData');
		
	var userCode = '';
	if(list != null){
		for(var i=0;i<list.length;i++){
			if(list[i].label==name){
				userCode = list[i].value;
				break;
			}
		}
	}
	
	//select方法会选中某一项
	//$('#userCode_edit').combobox('select', userCode);
	$('#userCode_edit').combobox('setValue', userCode);
	
	//easyui 的textbox，但是我觉得combobox类似
	//setText	text	Set the displaying text value.
	//setValue	value	Set the component value.
}




/**
 * jquery easyui 在子tab页中打开新tab页
 * @param title
 * @param url
 */
function addTab(title, url) {
	//jquery easyui 在子tab页中打开新tab页(关于easyUI在子页面增加显示tabs的一个问题)
	//在子页面点个按钮也能触发增加子页面的情况。
	//改正的关键是用top.jQuery这个函数，这个函数具体出外我忘记了，用法看似是取得整个父页面对象，正确是写法：
	//http://blog.csdn.net/zhang527836447/article/details/44676581
	//其他写法尝试了下有问题，就采用了如下的写法	
	var jq = top.jQuery;
	if (jq('#home').tabs('exists', title)) {
		jq('#home').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0" src="'
				+ url + '" style="width:100%;height:100%;"></iframe>';
		jq('#home').tabs('add', {
			title : title,
			content : content,
			closable : true
		});
	}
}

/**
 * 格式化性别代码为中文
 * @param val
 * @param row
 * @returns
 */
function formatGenderCode(val,row){
	if(val == 'M'){
		return '男';
	}else if(val == 'F'){
		return '女';
	}
}

/**
 * 格式化面试结束标志位中文
 * @param val
 * @param row
 * @returns
 */
function formatInterviewEndFlag(val,row){
	if(val == '1'){
		return '已结束';
	}else if(val == '0'){
		return '未结束';
	}
}

/**
 * 格式化是否发送一面面试邀请邮件代码为中文
 * @param val
 * @param row
 * @returns
 */
function formatFirstInterviewEmailSendFlag(val,row){
	if(val == '0'){
		return '还未发送';
	}else if(val == '1'){
		return '发送失败';
	}else if(val == '2'){
		return '发送成功';
	}
}

function formatDateTimeStringOnChange(){
	var timeStr = $(this).textbox('getValue');
	var newTimeStr = formatDateTimeString(timeStr);
	$(this).textbox('setValue', newTimeStr);
}

/**
 * 转换日期格式为 yyyy-MM-dd HH:mm:ss 的统一格式
 * @param timeStr
 * @returns
 */
function formatDateTimeString(timeStr){
	
	if(timeStr == null || timeStr == ''){
		return '';
	}
	
	// 先判断下时间格式
	// yyyy/MM/dd HH:mm
	// yyyy/MM/dd HH:mm:ss
	// yyyy-MM-dd HH:mm
	// yyyy-MM-dd HH:mm:ss
	
	var reg = /(\d+)/g;
	var r = timeStr.match(reg);
	if(r != null && r.length > 0){
		// r[0] 表示匹配到的全体
		
		var y = 1971;
		var m = 1;
		var d = 1;
		var h = 0;
		var mi = 0;
		var s = 0;
		
		if(r.length > 0){
			y = parseInt(r[0],10);
		}
		
		if(r.length > 1){
			m = parseInt(r[1],10);
		}
		
		if(r.length > 2){
			d = parseInt(r[2],10);
		}
		
		if(r.length > 3){
			h = parseInt(r[3],10);
		}
		
		if(r.length > 4){
			mi = parseInt(r[4],10);
		}
		
		if(r.length > 5){
			s = parseInt(r[5],10);
		}
		
		var newStr = '' + y + '-' + (m<10?('0'+m):m) + '-' + (d<10?('0'+d):d) + ' ' + (h<10?('0'+h):h) + ':' + (mi<10?('0'+mi):mi) + ':' + (s<10?('0'+s):s);
		return newStr;
	}else{
		return '';
	}
}

function formatDateTimeToPerioidOnChange(){
	var timeStr = $(this).textbox('getValue');
	var newTimeStr = '';
	
	// 先格式化yyyy-MM-dd
	var reg = /(\d+)/g;
	var r = timeStr.match(reg);
	if(r != null && r.length > 0){
		// r[0] 表示匹配到的全体
		
		var y = 1971;
		var m = 1;
		var d = 1;
		
		if(r.length > 0){
			y = parseInt(r[0],10);
		}
		
		if(r.length > 1){
			m = parseInt(r[1],10);
		}
		
		if(r.length > 2){
			d = parseInt(r[2],10);
		}
		
		newTimeStr = '' + y + '-' + (m<10?('0'+m):m) + '-' + (d<10?('0'+d):d);
	}
	
	//再格式化AM和PM
	// 情况1，直接填写了小时
	if(r != null && r.length > 3){
		var h = parseInt(r[3],10);
		var periodFlag = h<=12?'AM':'PM';
		newTimeStr = newTimeStr + ' ' + periodFlag; 
	}else{
		// 情况2，没有填写小时，填写的是am或者pm
		var reg2 = /(am|pm|AM|PM)/;
		var r2 = timeStr.match(reg2);
		if(r2 != null && r2.length > 1){
			var periodFlag = r2[1].toUpperCase();
			newTimeStr = newTimeStr + ' ' + periodFlag; 
		}
	}
	
	$(this).textbox('setValue', newTimeStr);
}

/**
 * 将日期字符串转换为 yyyy-MM-dd AM  yyyy-MM-dd PM  格式
 * @param s
 * @returns
 */
function transDateTimeToPerioid(s){
	if (!s) return '';
	
	var y = parseInt(s.substring(0,4),10);
	var m = parseInt(s.substring(5,7),10);
	var d = parseInt(s.substring(8,10),10);
	
	var h = parseInt(s.substring(11,13),10);
	
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return '' + y + '-' + (m<10?('0'+m):m) + '-' + (d<10?('0'+d):d) + ' ' + (h<=12?'AM':'PM');
	} else {
		return '';
	}
}

