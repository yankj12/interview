<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/main/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Access-Control-Allow-Origin" content="*">

<script src="${ctx }/main/js/mycommon.js" type="text/javascript"></script>
<script src="${ctx }/pages/emailConfig/emailConfig.js" type="text/javascript"></script>

	<!-- 下拉列表可能需要的样式 -->
	<style type="text/css">
		.item-img{
			display:inline-block;
			vertical-align:middle;
			width:16px;
			height:16px;
		}
		.item-text{
			display:inline-block;
			vertical-align:middle;
			padding:3px 0 3px 3px;
		}
	</style>
<title>邮箱配置</title>
</head>
<body>
<div class="easyui-panel" title="查询条件" style="width:100%;height:auto;">
    <form id="conditionForm" action="" method="post" enctype="multipart/form-data">
        <table>
            <tr>
				<td>模板名称</td>
				<td colspan="3">
					<input name="name" class="easyui-textbox" style="width:100%">
				</td>
				<td>邮件服务器名</td>
				<td>
					<input name="smtpHostName" class="easyui-textbox">
				</td>
				<td>有效状态</td>
				<td>
					<input name="validStatus" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '有效',
								value: '1',
								'selected':true
							},{
								label: '无效',
								value: '0'
							},{
								label: '--',
								value: ''
							}]"/>
				</td>
			</tr>
			
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td colspan="2"><!-- 这里不能使用submit，因为submit会进行页面跳转，新跳转的页面会只显示json数据，应该用异步方式提交表单 -->
					<input type="button" value="查询" onClick="exeQuery()"></input>
					&nbsp;&nbsp;
					<input type="button" value="重置" onClick="resetConditionForm()"></input>
				</td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>
<table id="dg" title="查询结果" class="easyui-datagrid" style="width:100%;height:auto;"
		url="${ctx }/emailConfig/findEmailConfigs.do?validStatus=1"
		toolbar="#toolbar"
		rownumbers="true" pagination="true" fitColumns="true">
		<!-- table增加了pagination="true"属性，就增加了底部的分页工具栏 -->
	<thead>
		<tr>
			<!-- field必须不能重复，否则页面展示上比例调整起来很没有规律 -->
			<th data-options="field:'id',hidden:true">主键</th>
			<th field="name" width="30">模板标题</th>
			<th field="smtpHostName" width="30">邮件服务器名称</th>
			<th field="smtpPort" width="10">邮件服务器端口</th>
			<th field="sslOnConnect" width="10">是否使用ssl</th>
			<th field="mailUserName" width="10">发件人名称</th>
			<th field="validStatus" width="20" formatter="formatValidStatus">有效状态</th>
			<th field="userCode" width="30">归属人员</th>
			
		</tr>
	</thead>
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRecord('编写邮箱配置')">编写邮箱配置</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRecord('修改邮箱配置')">修改邮箱配置</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyRecord()">删除邮箱配置</a>
</div>
	<div id="dlg" class="easyui-dialog" style="width:800px;height:auto;padding:0px 0px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
		<table cellpadding="5" style="width:100%;">
			<tr>
				<td><label>模板标题</label></td>
				<td>
					<input type="hidden" id="loginUserCode_edit" name="loginUserCode" value="${userMsgInfo.userCode }"/>
					<input type="hidden" id="loginUserName_edit" name="loginUserName" value="${userMsgInfo.userCName }"/>
					
					<input type="hidden" id="editType_edit" name="editType" value="new"/>
					<input type="hidden" id="id_edit" name="id" value=""/>
					<input type="hidden" id="validStatus_edit" name="validStatus" value=""/>
					<input id="name_edit" name="name" class="easyui-textbox" value="" style="width:100%"/>
				</td>
				<td><label>归属人员代码</label></td>
				<td>
					<input id="userCode_edit" name="userCode" class="easyui-textbox" value="" style="width:100%"/>
				</td>
				
			</tr>

			<tr>
				<td><label>邮件服务器名称</label></td>
				<td>
					<input id="smtpHostName_edit" name="smtpHostName" class="easyui-textbox" value="" style="width:100%"/>
				</td>
				<td><label>邮件服务器端口</label></td>
				<td>
					<input id="smtpPort_edit" name="smtpPort" class="easyui-textbox" value="" style="width:100%"/>
				</td>
			</tr>

			<tr>
				<td><label>是否使用ssl</label></td>
				<td>
					<input id="sslOnConnect_edit" name="sslOnConnect" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '是',
								value: 'true',
								'selected':true
							},{
								label: '否',
								value: 'false'
							}]"/>
				</td>
				<td><label></label></td>
				<td>
				</td>
			</tr>
			
			<tr>
				<td><label>发件邮箱用户名</label></td>
				<td>
					<input id="mailUserName_edit" name="mailUserName" class="easyui-textbox" value="" style="width:100%"/>
				</td>
				<td><label>发件邮箱密码</label></td>
				<td>
					<input id="mailUserPwd_edit" name="mailUserPwd" class="easyui-textbox" value="" style="width:100%">
				</td>
			</tr>
			
			<tr>
				<td><label>备注</label></td>
				<td colspan="3">
					<input id="remark_edit" name="remark" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:60px">
				</td>
			</tr>
			
		</table>	
		</form>
	</div>
	<!-- 下面dlg-buttons是为了让新增用户页面有保存和取消按钮 -->
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveRecord()" style="width:90px">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
	</div>
	
	<script type="text/javascript">

	
	</script>
	<style type="text/css">
		#fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
		.fitem input{
			width:160px;
		}
	</style>
</body>
</html>