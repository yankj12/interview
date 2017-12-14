<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/main/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Access-Control-Allow-Origin" content="*">

<script src="${ctx }/main/js/mycommon.js" type="text/javascript"></script>
<script src="${ctx }/interview.js" type="text/javascript"></script>

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
<title>面试信息管理</title>
</head>
<body>
<div class="easyui-panel" title="查询条件" style="width:100%;height:auto;">
    <form id="conditionForm" action="" method="post" enctype="multipart/form-data">
        <table>
            <tr>
				<td>一面起始日期:</td>
				<td>
					<input name="firstInterviewTimeStart" class="easyui-textbox">
				</td>
				<td>一面结束日期:</td>
				<td>
					<input name="firstInterviewTimeEnd" class="easyui-textbox">
				</td>
				<td>姓名:</td>
				<td>
					<input name="userName" class="easyui-textbox">
				</td>
				<td>手机:</td>
				<td>
					<input name="phone" class="easyui-textbox">
				</td>
				<td>邮箱:</td>
				<td>
					<input name="email" class="easyui-textbox">
				</td>
				<td>性别:</td>
				<td>
					<input name="genderCode" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '男',
								value: 'M'
							},{
								label: '女',
								value: 'F'
							},{
								label: '--',
								value: '',
								'selected':true
							}]"/>
				</td>
			</tr>
			
			<tr>
				<td>工作年数:</td>
				<td>
					<input name="jobExperienceYear" class="easyui-textbox">
				</td>
				<td>一面面试官:</td>
				<td>
					<input name="firstInterviewOfficer" class="easyui-textbox">
				</td>
				<td>毕业院校:</td>
				<td>
					<input name="university" class="easyui-textbox">
				</td>
				<td>专业:</td>
				<td>
					<input name="major" class="easyui-textbox">
				</td>
				<td>一面评价:</td>
				<td>
					<input name="firstIntervirewRemark" class="easyui-textbox">
				</td>
				<td>面试阶段:</td>
				<td>
					<input name="interviewPhase" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '选完简历',
								value: '选完简历'
							},{
								label: '电话约一面',
								value: '电话约一面'
							},{
								label: '电话筛除',
								value: '电话筛除'
							},{
								label: '爽约',
								value: '爽约'
							},{
								label: '一面不通过',
								value: '一面不通过'
							},{
								label: '约复试',
								value: '约复试'
							},{
								label: '复试不通过',
								value: '复试不通过'
							},{
								label: '复试通过',
								value: '复试通过'
							},{
								label: '未入职',
								value: '未入职'
							},{
								label: '已入职',
								value: '已入职'
							},{
								label: '--',
								value: '',
								'selected':true
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
		url="${ctx }/interview/findInterviews.do?validStatus=1"
		toolbar="#toolbar"
		rownumbers="true" pagination="true" fitColumns="true" singleSelect="true">
		<!-- table增加了pagination="true"属性，就增加了底部的分页工具栏 -->
	<thead>
		<tr>
			<!-- field必须不能重复，否则页面展示上比例调整起来很没有规律 -->
			<th data-options="field:'id',hidden:true">主键</th>
			<th field="userName" width="10">姓名</th>
			<th field="phone" width="20">手机</th>
			<th field="email" width="20">邮箱</th>
			<th field="genderCode" width="5" formatter="formatGenderCode">性别</th>
			<th field="interviewPhase" width="10">面试阶段</th>
			<th field="firstInterviewTime" width="20">一面时间</th>
			<th field="firstInterviewOfficer" width="20">一面面试官</th>
			<th field="university" width="20">毕业院校</th>
			<th field="major" width="30">专业</th>
			<th field="graduateMonth" width="20">毕业时间</th>
			
		</tr>
	</thead>
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRecord('编写工作日志')">编写工作日志</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRecord('修改工作日志')">修改工作日志</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyRecord()">删除工作日志</a>
</div>
	<!-- 下面dlg是为了有新增用户界面 -->
	<div id="dlg" class="easyui-dialog" style="width:800px;height:auto;padding:0px 0px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
		<table cellpadding="5" style="width:100%;">
			<tr>
				<td><label>姓名</label></td>
				<td>
					<input type="hidden" id="loginUserCode_edit" name="loginUserCode" value="${userMsgInfo.userCode }"/>
					<input type="hidden" id="loginUserName_edit" name="loginUserName" value="${userMsgInfo.userCName }"/>
					
					<input type="hidden" id="editType_edit" name="editType" value="new"/>
					<input type="hidden" id="id_edit" name="id" value=""/>
					<input type="hidden" id="validStatus_edit" name="validStatus" value=""/>
					<input id="userName_edit" name="userName" class="easyui-textbox" value=""/>
				</td>
				<td><label>英文名/拼音</label></td>
				<td>
					<input id="userEName_edit" name="userEName" class="easyui-textbox" value=""/>
				</td>
				
			</tr>

			<tr>
				<td><label>性别</label></td>
				<td>
					<input id="genderCode_edit" name="genderCode" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '男',
								value: 'M'
							},{
								label: '女',
								value: 'F'
							},{
								label: '--',
								value: '',
								'selected':true
							}]"/>
					<input type="hidden" id="genderName_edit" name="genderName" value=""/>
				</td>
				<td><label>出生年月(yyyy/MM)</label></td>
				<td>
					<input id="birth_edit" name="birth" class="easyui-textbox" value="" data-options="onChange:formatBirth,onBlur:formatBirth"/>
				</td>
			</tr>

			<tr>
				<td><label>电话</label></td>
				<td>
					<input id="phone_edit" name="phone" class="easyui-textbox" value=""/>
				</td>
				<td><label>邮箱</label></td>
				<td>
					<input id="email_edit" name="email" class="easyui-textbox" value=""/>
				</td>
				
			</tr>
			
			<tr>
				<td><label>毕业院校</label></td>
				<td>
					<input id="university_edit" name="university" class="easyui-textbox" value="">
				</td>
				<td><label>专业</label></td>
				<td>
					<input id="major_edit" name="major" class="easyui-textbox" value=""/>
				</td>
			</tr>
			<tr>
				<td><label>学历</label></td>
				<td>
					<input id="educationBackground_edit" name="educationBackground" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '大专',
								value: '大专'
							},{
								label: '三本',
								value: '三本'
							},{
								label: '非统招本科',
								value: '非统招本科'
							},{
								label: '统招本科',
								value: '统招本科',
								'selected':true
							},{
								label: '硕士及以上',
								value: '硕士及以上'
							},{
								label: '--',
								value: ''
							}]"/>
				</td>
				<td><label>毕业时间(yyyy/MM)</label></td>
				<td>
					<input id="graduateMonth_edit" name="graduateMonth" class="easyui-textbox" value="" data-options="onChange:formatGraduateMonth,onBlur:formatGraduateMonth"/>
				</td>
			</tr>
			
			<tr>
				<td><label>工作年限</label></td>
				<td>
					<input id="jobExperienceYear_edit" name="jobExperienceYear" class="easyui-numberspinner" value="1" data-options="increment:1"/>
				</td>
				<td><label>面试阶段</label></td>
				<td>
					<input id="interviewPhase_edit" name="interviewPhase" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '选完简历',
								value: '选完简历',
								'selected':true
							},{
								label: '电话约一面',
								value: '电话约一面'
							},{
								label: '电话筛除',
								value: '电话筛除'
							},{
								label: '爽约',
								value: '爽约'
							},{
								label: '一面不通过',
								value: '一面不通过'
							},{
								label: '约复试',
								value: '约复试'
							},{
								label: '复试不通过',
								value: '复试不通过'
							},{
								label: '复试通过',
								value: '复试通过'
							},{
								label: '未入职',
								value: '未入职'
							},{
								label: '已入职',
								value: '已入职'
							},{
								label: '--',
								value: ''
							}]"/>
				</td>
			</tr>
			
			<tr>
				<td><label>电话约面试时间</label></td>
				<td>
					<input id="firstPhoneCallTime_edit" name="firstPhoneCallTime" class="easyui-datetimebox" data-options="formatter:myDateTimeFormatter,parser:myDateTimeParser"/>
				</td>
				<td><label></label></td>
				<td>
				</td>
			</tr>
			<tr>
				<td><label>电话约面试备注</label></td>
				<td colspan="3">
					<input id="firstPhoneCallRemark_edit" name="firstPhoneCallRemark" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:60px"/>
				</td>
			</tr>
			<tr>
				<td><label>一面时间</label></td>
				<td>
					<input id="firstInterviewTime_edit" name="firstInterviewTime" class="easyui-datetimebox" data-options="formatter:myDateTimeFormatter,parser:myDateTimeParser"/>
				</td>
				<td><label>一面面试官</label></td>
				<td>
					<input id="firstInterviewOfficer_edit" name="firstInterviewOfficer" class="easyui-textbox" value=""/>
				</td>
			</tr>
			<tr>
				<td><label>一面评价</label></td>
				<td colspan="3">
					<input id="firstIntervirewRemark_edit" name="firstIntervirewRemark" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:100px">
				</td>
			</tr>
			<tr>
				<td><label>复试时间</label></td>
				<td>
					<input id="secondInterviewTime_edit" name="secondInterviewTime" class="easyui-datetimebox" data-options="formatter:myDateTimeFormatter,parser:myDateTimeParser"/>
				</td>
				<td><label></label></td>
				<td>
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