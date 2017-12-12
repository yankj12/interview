<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Access-Control-Allow-Origin" content="*">

<!-- Insert this line above script imports  -->
<script>if (typeof module === 'object') {window.module = module; module = undefined;}</script>

<script src="./easyui/jquery.min.js" type="text/javascript"></script>
<script src="./easyui/jquery.easyui.min.js" type="text/javascript"></script>

<script src="./main/js/mycommon.js" type="text/javascript"></script>
<script src="./interview.js" type="text/javascript"></script>

<link href="./easyui/themes/default/easyui.css" rel="stylesheet"type="text/css" />
<link href="./easyui/themes/icon.css" rel="stylesheet" type="text/css" />

<!-- Insert this line after script imports -->
<script>if (window.module) module = window.module;</script>

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
								label: '约一面',
								value: '约一面'
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
		url="http://localhost:8080/findInterviews"
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
			<th field="genderCode" width="5">性别</th>
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
	<div id="dlg" class="easyui-dialog" style="width:600px;height:auto;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
		<table cellpadding="5">
			<tr>
				<td><label>工作日志日期</label></td>
				<td>
					<input type="hidden" id="editType_edit" name="editType" value="new"/>
					<input type="hidden" id="id_edit" name="id" value=""/>
					<input id="day_edit" name="day" class="easyui-textbox" value="" data-options="onChange:setWorkReportTitle"/>
				</td>
				<td><label>日志编写人</label></td>
				<td>
					<input id="writerName_edit" name="writerName" class="easyui-textbox" value=""/>
				</td>
				
			</tr>

			<tr>
				<td><label>日志类型:</label></td>
				<td>
					<input id="type_edit" name="type" class="easyui-textbox" value="" data-options="onChange:setWorkReportTitle"/>
				</td>
				<td><label>person/team</label></td>
				<td>
					
				</td>
				
			</tr>

			<tr>
				<td><label>日志标题</label></td>
				<td colspan="3">
					<input id="title_edit" name="title" class="easyui-textbox" value="" style="width:100%"/>
				</td>
				
			</tr>
			
			<tr>
				<td><label>项目代码:</label></td>
				<td>
					<input id="projectCode_edit" name="projectCode" class="easyui-textbox" value="" data-options="onChange:setWorkReportProjectName"/>
				</td>
				<td><label>项目名称:</label></td>
				<td>
					<input id="projectName_edit" name="projectName" class="easyui-textbox" value=""/>
				</td>
				
			</tr>
			
			<tr>
				<td colspan="4"><label>工作日志内容:</label></td>
			</tr>
			
			<tr>
					<td colspan="4">
						<a href="#" class="easyui-linkbutton"   data-options="iconCls:'icon-indent',onClick:indentRowText">去除行首空格</a>
					</td>
				</tr>
			<tr>
				<!-- 待审批工单内容概要 -->
				<td colspan="4">
					<input id="workText_edit" name="workText" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:200px">
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