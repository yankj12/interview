<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/main/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>login</title>
    
    <style type="text/css">
        #center{ 
                position:absolute; 
                top:50%; left:50%; 
                margin-top:-150px; margin-left:-250px;      
                width:500px;   height:300px;       
        }
    </style>
    <script type="text/javascript" src="${ctx }/main/js/md5.js"></script>
    <script type="text/javascript" src="${ctx }/jquery/jquery-cookie/1.4.1/jquery.cookie.js"></script>
    
</head>
<body>
	<!-- 这个id为center的div是为了将登陆输入框居中显示 -->
	<div id="center">
	    <div class="easyui-panel" title='登陆' style="width:400px;max-width:400px;padding:30px 60px;" >
	        <form name="form" id="ff" class="easyui-form" method="post" action="login.do" enctype="multipart/form-data">
	            <div style="margin-bottom:20px">
	                <input class="easyui-textbox" name="userCode" id="userCode" style="width:100%" data-options="label:'账号',required:true">
	            </div>
	            <div style="margin-bottom:20px">
	            	<input id="password" class="easyui-passwordbox" prompt="请输入密码" iconWidth="28" style="width:100%;padding:10px" data-options="label:'密码',required:true">
					<input id="password_hidden" type="hidden" name="password" value="">
	            </div>
	            <div style="margin-bottom:20px">
	                <label id="errorMsg">${errorMsg }</label>
	            </div>
	        </form>
	        <div style="text-align:center;padding:5px 0">
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">登陆</a>
	            <!-- <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">重置</a>-->
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="registe()" style="width:80px">注册</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="modifyPassword()" style="width:80px">忘记密码</a>
	        </div>
	    </div>
	</div>

	<!-- 下面dlg是为了有注册用户界面 -->
	<div id="dlg" class="easyui-dialog" style="width:550px;height:auto;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
		<table cellpadding="5">
			<tr>
				<td><label>账号</label></td>
				<td>
					<input id="user_userCode_edit" name="user.userCode" class="easyui-textbox"/>
				</td>
				<td><label>中文名</label></td>
				<td>
					<input id="user_userName_edit" name="user.userName" class="easyui-textbox"/>
				</td>
			</tr>
			
			<tr>
				<td><label>邮箱</label></td>
				<td colspan="3">
					<input id="user_email_edit" name="user.email" class="easyui-textbox" style="width:100%"/>
				</td>
			</tr>
			
			<tr>
				<td><label>请输入密码</label></td>
				<td colspan="3">
					<input type="hidden" id="user_pwd_edit" name="user.pswd" />
					<input id="user_pwd_first" class="easyui-passwordbox" prompt="请输入密码" iconWidth="28" style="width:100%;padding:10px" data-options="required:true">
				</td>
			</tr>
			
			<tr>
				<td><label>请重复密码</label></td>
				<td colspan="3">
					<input id="user_pwd_repeat" class="easyui-passwordbox" prompt="请重复密码" iconWidth="28" style="width:100%;padding:10px" data-options="required:true">
				</td>
			</tr>
			
			<tr>
				<td><label>个人备注信息</label></td>
				<td colspan="3">
					<input id="user_remark_edit" name="user.remark" class="easyui-textbox" style="width:100%"/>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<!-- 下面dlg-buttons是为了让新增用户页面有保存和取消按钮 -->
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="submitRegiste()" style="width:90px">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
	</div>

	<!-- 下面dlg1是修改密码界面 -->
	<div id="dlg1" class="easyui-dialog" style="width:300px;height:auto;padding:10px 20px"
			closed="true" buttons="#dlg-buttons1">
		<form id="fm1" method="post" novalidate>
		<table cellpadding="5">
			<tr>
				<td><label>账号</label></td>
				<td colspan="3">
					<input id="user_userCode_edit1" name="user.userCode" class="easyui-textbox" style="width:100%"/>
				</td>
			</tr>
			
			<tr>
				<td><label>邮箱</label></td>
				<td colspan="3">
					<input id="user_email_edit1" name="user.email" class="easyui-textbox" style="width:100%"/>
				</td>
			</tr>
			
			<tr>
				<td><label>请输入密码</label></td>
				<td colspan="3">
					<input type="hidden" id="user_pwd_edit" name="user.pswd" />
					<input id="user_pwd1" class="easyui-passwordbox" prompt="请输入密码" iconWidth="28" style="width:100%;padding:10px" data-options="required:true">
				</td>
			</tr>
			
		</table>
		</form>
	</div>
	<!-- 下面dlg-buttons是为了让修改密码页面有保存和取消按钮 -->
	<div id="dlg-buttons1">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="submitModifyPassword()" style="width:90px">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')" style="width:90px">取消</a>
	</div>
	
    <script>
    	/**
    	* 生成随机字符串
    	* len 长度
    	* radix 基数
    	*/
		function uuid(len, radix) {
			var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
			var uuid = [], i;
			radix = radix || chars.length;
			
			if (len) {
				// Compact form
				for (i = 0; i < len; i++) 
					uuid[i] = chars[0 | Math.random()*radix];    
			} else {
				// rfc4122, version 4 form      
				var r;       
				// rfc4122 requires these characters      
				uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';      
				uuid[14] = '4';       
				// Fill in random data.  At i==19 set the high bits of clock sequence as      
				// per rfc4122, sec. 4.1.5      
				for (i = 0; i < 36; i++) {        
					if (!uuid[i]) {          
						r = 0 | Math.random()*16;          
						uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];        
					}      
				}    
			}     
			return uuid.join('');
		}


        function submitForm(){
            var userCode = $('#userCode').textbox('getValue');
       		var pwd = $('#password').textbox('getValue');
       		
       		if(userCode == null || userCode == ''){
       			$.messager.alert('提示', '账号不能为空!');
       			return false;
       		}
       		
       		if(pwd == null || pwd == ''){
       			$.messager.alert('提示', '密码不能为空!');
       			return false;
       		}
       		
       		
       		if(pwd != null && pwd != ''){
       			$('#password_hidden').val(hex_md5(pwd));
       		}else{
       			$('#password_hidden').val('');
       		}
       		//$('#ff').submit();    //这个提交方式是异步提交的方式，不会刷新页面
       		
       		var form = document.forms['form'];    //这个提交方式是同步提交的方式，会刷新页面
       		form.submit();
        }
        
        // 重置登陆框内容
        function clearForm(){
            $('#ff').form('clear');
            $('#errorMsg').text('');
        }
        
        // 弹出注册用户的窗口
        function registe(){
         	// 先清空注册用户表单中的旧数据
         	$('#fm').form('clear');
         	// 弹出注册用户窗口
        	$('#dlg').dialog('open').dialog('setTitle','注册用户');
        }
        
        // 提交注册用户的信息
        function submitRegiste(){
        	//先判断登录名不能为空
			var userCode = $('#user_userCode_edit').textbox('getValue');
        	
        	if(userCode == null || userCode == ''){
        		$.messager.alert('提示', '账号不能为空!');
       			return false;
       		}
        	
        	//判断用户名不能为空
			var userName = $('#user_userName_edit').textbox('getValue');
        	
        	if(userName == null || userName == ''){
        		$.messager.alert('提示', '中文名不能为空!');
       			return false;
       		}
        	
        	//判断邮箱不能为空
        	var email = $('#user_email_edit').textbox('getValue');
        	
        	if(email == null || email == ''){
        		$.messager.alert('提示', '邮箱不能为空!');
       			return false;
       		}
        	
        	//校验密码和重复出入的密码是否为空
        	var password = $('#user_pwd_first').textbox('getValue');
        	var passwordRepeat = $('#user_pwd_repeat').textbox('getValue');
        	
        	if(password == null || password == ''){
        		$.messager.alert('提示', '密码不能为空!');
       			return false;
       		}
        	
        	if(passwordRepeat == null || passwordRepeat == ''){
        		$.messager.alert('提示', '请重复密码!');
       			return false;
       		}
        	
        	//校验密码和重复输入的密码是否一致
        	if(password != passwordRepeat){
        		$.messager.alert('提示', '密码不一致!');
       			return false;
       		}else{
       			$('#user_pwd_edit').val(hex_md5(password));
       		}
        	
        	//校验用户编码是否存在
        	//校验邮箱是否存在
        	
        	$.ajax({
		        type:"GET", 
		        url: contextRootPath + "/login/checkUserUnique.do?user.userCode=" + userCode + '&user.email=' + email,
		        dataType:"json", 
		        contentType: "text/html;charset=UTF-8", 
		        success:function(result){
		        	if (result.success){
		        		
		        		//提交
		        		$('#fm').form('submit',{
							url: contextRootPath + '/login/registeUser.do',
							onSubmit: function(){
								return $(this).form('validate');
							},
							success: function(result){
								var result = eval('('+result+')');
								if (result.success){
									//提示信息
									$.messager.alert('提示',result.errorMsg);
									
									//关闭窗口
									$('#dlg').dialog('close');		// close the dialog
									
								} else {
									$.messager.show({
										title: 'Error',
										msg: result.errorMsg
									});
								}
							}
						});
		        		
		        	}else{
		        		//用户编码或者邮箱已经存在
		        		$.messager.alert('提示',result.errorMsg);
		        	}
		        },
		       	failure:function (result) {  
		       		//(提示框标题，提示信息)
		    		$.messager.alert('提示',result.errorMsg);
		       	}
			});
        	
        }
        
        // 弹出修改密码的窗口
        function modifyPassword(){
        	
        	// 弹出修改密码的窗口
        	// 先清空注册用户表单中的旧数据
         	$('#fm1').form('clear');
         	// 弹出注册用户窗口
        	$('#dlg1').dialog('open').dialog('setTitle','修改密码');
        	
        }
        
        // 提交修改密码的请求
        function submitModifyPassword(){
        	//先判断登录名不能为空
			var userCode = $('#user_userCode_edit1').textbox('getValue');
        	
        	if(userCode == null || userCode == ''){
        		$.messager.alert('提示', '账号不能为空!');
       			return false;
       		}
        	
        	//判断邮箱不能为空
        	var email = $('#user_email_edit1').textbox('getValue');
        	
        	if(email == null || email == ''){
        		$.messager.alert('提示', '邮箱不能为空!');
       			return false;
       		}
        	
        	//校验密码和重复出入的密码是否为空
        	var password = $('#user_pwd1').textbox('getValue');
        	
        	if(password == null || password == ''){
        		$.messager.alert('提示', '密码不能为空!');
       			return false;
       		}
       		password = hex_md5(password);
        	
        	// 判断cookie
        	// 提交时
        	// 读取cookie，key是_pdc值是时间戳。 如果cookie不存在则创建，存在不创建新的cookie
        	var pdc = $.cookie("_pdc");
        	if(pdc == null || pdc == ''){
        		// 获取时间戳
	        	var date = new Date();
	       		var y = date.getFullYear();
				var m = date.getMonth()+1;
				var d = date.getDate();
				var h = date.getHours();
				var mi = date.getMinutes();
				var s = date.getSeconds();
				var datetime =  '' + y + (m<10?('0'+m):m) + (d<10?('0'+d):d) + (h<10?('0'+h):h) + (mi<10?('0'+mi):mi) + (s<10?('0'+s):s);
        		// 生成随机字符串
        		var randomstr = uuid(16, 16);
        		pdc = datetime + randomstr;
        		// 写入cookie
        		$.cookie("_pdc", pdc ,{path:"/"});
        	}
        	
        	$.ajax({
		        type:"GET", 
		        url: contextRootPath + "/login/modifyPassword.do?user.userCode=" + userCode + '&user.email=' + email + '&user.pswd=' + password,
		        dataType:"json", 
		        contentType: "text/html;charset=UTF-8", 
		        success:function(result){
		        	if (result.success){
		        		$.messager.alert('提示',result.errorMsg);
		        		$('#dlg1').dialog('close');
		        		// 清空注册用户表单中的旧数据
         				$('#fm1').form('clear');
		        	}else{
		        		$.messager.alert('提示',result.errorMsg);
		        	}
		        },
		       	failure:function (result) {  
		       		//(提示框标题，提示信息)
		    		$.messager.alert('提示',result.errorMsg);
		       	}
			});
        }
        
    </script>
</body>
</html>