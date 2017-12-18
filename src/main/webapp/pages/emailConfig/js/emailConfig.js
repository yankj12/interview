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
	
	// 归属人员代码赋默认值 当前登录用户
	var loginUserCode = $("#loginUserCode_edit").val();
	$('#userCode_editt').textbox('setValue', loginUserCode);
	
}


function editRecord(title){
	
	var row = $('#dg').datagrid('getSelected');    //这一步可以改造为从后台异步获取数据
	
	if(row != null){
		var id = row.id;
		
		//异步从action中加载数据
		$.ajax({
	        type:"GET", 
	        url:contextRootPath + "/emailConfig/findUniqueEmailConfig.do?id=" + id,
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
	        		var emailConfig = result.object;
					
					$("#id_edit").val(interview.id);
					
	        		//设置修改类型，否则action中保存方法不知道是什么修改类型
	        		$('#editType_edit').val("edit");
	        		
	        		$('#validStatus_edit').val(emailConfig.validStatus);
	        		//
	        		$('#name_edit').textbox('setValue', emailConfig.name);
	        		
	        		$('#userCode_edit').textbox('setValue', emailConfig.userCode);
	        		
	        		$('#smtpHostName_edit').textbox('setValue', emailConfig.smtpHostName);
	        		
	        		$("#smtpPort_edit").textbox('setValue', emailConfig.smtpPort);
	        		
	        		$('#sslOnConnect_edit').combobox('setValue', emailConfig.sslOnConnect);
	        		
	        		$('#mailUserName_edit').textbox('setValue', emailConfig.mailUserName);
	        		
	        		$('#mailUserPwd_edit').textbox('setValue', emailConfig.mailUserPwd);
	        		
	        		$('#remark_edit').textbox('setValue', emailConfig.remark);
	        		
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
				$.post(contextRootPath + '/emailConfig/deleteEmailConfig.do',{id:row.id},function(result){
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

	var validStatus = $('#validStatus_edit').val();
	var name = $('#name_edit').textbox('getValue');
	var userCode = $('#userCode_edit').textbox('getValue');
	var smtpHostName = $('#smtpHostName_edit').textbox('getValue');
	var smtpPort = $("#smtpPort_edit").textbox('getValue');
	var sslOnConnect = $('#sslOnConnect_edit').combobox('getValue');
	var mailUserName = $('#mailUserName_edit').textbox('getValue');
	var mailUserPwd = $('#mailUserPwd_edit').textbox('getValue');
	var remark = $('#remark_edit').textbox('getValue');
	
	var requestVo = new Object();
	requestVo.editType = editType;
	requestVo.id = id;
	
	requestVo.name = name;
	requestVo.userCode = userCode;
	requestVo.smtpHostName = smtpHostName;
	requestVo.smtpPort = smtpPort;
	requestVo.sslOnConnect = sslOnConnect;
	requestVo.mailUserName = mailUserName;
	requestVo.mailUserPwd = mailUserPwd;
	requestVo.remark = remark;
	requestVo.validStatus = validStatus;

	$.post(contextRootPath + '/emailConfig/saveEmailConfig.do', requestVo, function(result){
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

function resetConditionForm(){
	//JQuery中没有reset方法,需要通过JQuery来重置表单呢
	//或者使用dom的方法来做 document.getElementById
	$("#conditionForm")[0].reset();
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
	
	// yyyy/MM/dd HH:mm
	var reg = /^(\d{4})\/(\d{1,2})\/(\d{1,2})\s(\d{1,2}):(\d{1,2})$/;
	var r = timeStr.match(reg);
	if(r != null){
		// r[0] 表示匹配到的全体
		var y = parseInt(r[1],10);
		var m = parseInt(r[2],10);
		var d = parseInt(r[3],10);
		
		var h = parseInt(r[4],10);
		var mi = parseInt(r[5],10);
		var s = 0;
		
		var newStr = '' + y + '-' + (m<10?('0'+m):m) + '-' + (d<10?('0'+d):d) + ' ' + (h<10?('0'+h):h) + ':' + (mi<10?('0'+mi):mi) + ':' + (s<10?('0'+s):s);
		return newStr;
	}
	
	// yyyy/MM/dd HH:mm:ss
	reg = /^(\d{4})\/(\d{1,2})\/(\d{1,2})\s(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	r = timeStr.match(reg);
	if(r != null){
		// r[0] 表示匹配到的全体
		var y = parseInt(r[1],10);
		var m = parseInt(r[2],10);
		var d = parseInt(r[3],10);
		
		var h = parseInt(r[4],10);
		var mi = parseInt(r[5],10);
		var s = parseInt(r[6],10);
		
		var newStr = '' + y + '-' + (m<10?('0'+m):m) + '-' + (d<10?('0'+d):d) + ' ' + (h<10?('0'+h):h) + ':' + (mi<10?('0'+mi):mi) + ':' + (s<10?('0'+s):s);
		return newStr;
	}
	
	// yyyy-MM-dd HH:mm
	reg = /^(\d{4})-(\d{1,2})-(\d{1,2})\s(\d{1,2}):(\d{1,2})$/;
	r = timeStr.match(reg);
	if(r != null){
		// r[0] 表示匹配到的全体
		var y = parseInt(r[1],10);
		var m = parseInt(r[2],10);
		var d = parseInt(r[3],10);
		
		var h = parseInt(r[4],10);
		var mi = parseInt(r[5],10);
		var s = 0;
		
		var newStr = '' + y + '-' + (m<10?('0'+m):m) + '-' + (d<10?('0'+d):d) + ' ' + (h<10?('0'+h):h) + ':' + (mi<10?('0'+mi):mi) + ':' + (s<10?('0'+s):s);
		return newStr;
	}
	
	// yyyy-MM-dd HH:mm:ss
	reg = /^(\d{4})-(\d{1,2})-(\d{1,2})\s(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	r = timeStr.match(reg);
	if(r != null){
		// r[0] 表示匹配到的全体
		var y = parseInt(r[1],10);
		var m = parseInt(r[2],10);
		var d = parseInt(r[3],10);
		
		var h = parseInt(r[4],10);
		var mi = parseInt(r[5],10);
		var s = parseInt(r[6],10);
		
		var newStr = '' + y + '-' + (m<10?('0'+m):m) + '-' + (d<10?('0'+d):d) + ' ' + (h<10?('0'+h):h) + ':' + (mi<10?('0'+mi):mi) + ':' + (s<10?('0'+s):s);
		return newStr;
	}
	
}
