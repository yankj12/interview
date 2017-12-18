<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/main/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Access-Control-Allow-Origin" content="*">

<script src="${ctx }/main/js/mycommon.js" type="text/javascript"></script>
<script src="${ctx }/pages/interviewConfig/interviewConfig.js" type="text/javascript"></script>

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
<title>面试邀请邮件</title>
</head>
<body>
<div class="easyui-panel" title="查询条件" style="width:100%;height:auto;">
    <form id="conditionForm" action="" method="post" enctype="multipart/form-data">
        <table>
            <tr>
				<td>模板名称</td>
				<td>
					<input name="name" class="easyui-textbox">
				</td>
				<td>邮件抄送给</td>
				<td>
					<input name="copyTo" class="easyui-textbox">
				</td>
				<td>邮件主题</td>
				<td>
					<input name="subject" class="easyui-textbox">
				</td>
				<td>面试地点</td>
				<td>
					<input name="interviewAddress" class="easyui-textbox">
				</td>
				
			</tr>
			
			<tr>
				<td>面试官称谓</td>
				<td>
					<input name="interviewOfficerTitle" class="easyui-textbox">
				</td>
				<td>面试官电话</td>
				<td>
					<input name="interviewOfficerPhone" class="easyui-textbox">
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
				<td></td>
				<td></td>
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
		url="${ctx }/interviewConfig/findInterviewConfigs.do?validStatus=1"
		toolbar="#toolbar"
		rownumbers="true" pagination="true" fitColumns="true">
		<!-- table增加了pagination="true"属性，就增加了底部的分页工具栏 -->
	<thead>
		<tr>
			<!-- field必须不能重复，否则页面展示上比例调整起来很没有规律 -->
			<th data-options="field:'id',hidden:true">主键</th>
			<th field="name" width="30">模板标题</th>
			<th field="copyTo" width="30">邮件抄送给</th>
			<th field="subject" width="30">邮件主题</th>
			<th field="interviewAddress" width="30">面试地点</th>
			<th field="interviewOfficerTitle" width="10">面试官称谓</th>
			<th field="interviewOfficerPhone" width="20">面试官联系电话</th>
			<th field="validStatus" width="20" formatter="formatValidStatus">有效状态</th>
			<th field="userCode" width="30">归属人员</th>
			
		</tr>
	</thead>
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRecord('编写面试邀请邮件模板')">编写面试邀请邮件模板</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRecord('修改面试邀请邮件模板')">修改面试邀请邮件模板</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyRecord()">删除面试邀请邮件模板</a>
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
				<td><label>邮件主题</label></td>
				<td>
					<input id="subject_edit" name="subject" class="easyui-textbox" value="" style="width:100%"/>
				</td>
				<td><label>邮件抄送给</label></td>
				<td>
					<input id="copyTo_edit" name="copyTo" class="easyui-textbox" value="" style="width:100%"/>
				</td>
			</tr>

			<tr>
				<td><label>面试地点</label></td>
				<td colspan="3">
					<input id="interviewAddress_edit" name="interviewAddress" class="easyui-textbox" value="" style="width:100%"/>
				</td>
			</tr>
			
			<tr>
				<td><label>面试官称谓</label></td>
				<td>
					<input id="interviewOfficerTitle_edit" name="interviewOfficerTitle" class="easyui-textbox" value="" style="width:100%"/>
				</td>
				<td><label>面试官电话</label></td>
				<td>
					<input id="interviewOfficerPhone_edit" name="interviewOfficerPhone" class="easyui-textbox" value="" style="width:100%"/>
				</td>
			</tr>
			
			<tr>
				<td><label>邮件正文后缀</label></td>
				<td colspan="3">
					<input id="emailSuffix_edit" name="emailSuffix" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:100px"/>
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