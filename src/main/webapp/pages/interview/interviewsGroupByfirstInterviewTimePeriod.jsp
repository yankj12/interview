<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/main/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Access-Control-Allow-Origin" content="*">

<script src="${ctx }/main/js/mycommon.js" type="text/javascript"></script>
<script src="${ctx }/pages/interview/js/interviewsGroupByfirstInterviewTimePeriod.js" type="text/javascript"></script>
<script src="${cdn}/echarts/echarts.min.js" type="text/javascript"></script>

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
					<input name="firstInterviewTimeStart" class="easyui-textbox" data-options="onChange:formatDateTimeStringOnChange"/>
				</td>
				<td>一面结束日期:</td>
				<td>
					<input name="firstInterviewTimeEnd" class="easyui-textbox" data-options="onChange:formatDateTimeStringOnChange"/>
				</td>
				<td>电话约面试起始日期:</td>
				<td>
					<input name="firstPhoneCallTimeStart" class="easyui-textbox" data-options="onChange:formatDateTimeStringOnChange"/>
				</td>
				<td>电话约面试结束日期:</td>
				<td>
					<input name="firstPhoneCallTimeEnd" class="easyui-textbox" data-options="onChange:formatDateTimeStringOnChange"/>
				</td>
				<td>一面电话邀约人</td>
				<td>
					<input name="firstPhoneCallOfficer" class="easyui-textbox"/>
				</td>
				</td>
				<td></td>
				<td>
				</td>
			</tr>
			
			<tr>
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
				<td></td>
				<td>
				</td>
				<td></td>
				<td>
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
				<td>一面邀请邮件:</td>
				<td>
					<input name="firstInterviewEmailSendFlag" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '还未发送',
								value: '0'
							},{
								label: '发送失败',
								value: '1'
							},{
								label: '发送成功',
								value: '2'
							},{
								label: '--',
								value: '',
								'selected':true
							}]"/>
				</td>
				<td>面试是否结束:</td>
				<td>
					<input name="interviewEndFlag" class="easyui-combobox" 
						data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '未结束',
								value: '0'
							},{
								label: '已结束',
								value: '1'
							},{
								label: '--',
								value: '',
								'selected':true
							}]"/>
				</td>
				<td>一面面试时间段:</td>
				<td>
					<input name="firstInterviewTimePeriod" class="easyui-textbox" data-options="onChange:formatDateTimeToPerioidOnChange"/>
				</td>
				<td></td>
				<td>
				</td>
				<td></td>
				<td>
				</td>
				<td></td>
				<td>
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

<div class="easyui-tabs" style="width:100%;height:auto;">
	<div title="查询结果" style="padding:5px">
		<table id="dg" class="easyui-datagrid" style="width:100%;height:auto;"
				url="${ctx }/interview/showInterviewsGroupByfirstInterviewTimePeriod.do?validStatus=1"
				rownumbers="true" pagination="false" fitColumns="true">
				<!-- table增加了pagination="true"属性，就增加了底部的分页工具栏 -->
			<thead>
				<tr>
					<th field="id" width="40">一面时间</th>
					<th field="count" width="60">面试人员个数</th>
					
				</tr>
			</thead>
		</table>
	</div>
	<div title="图表" style="padding:5px">
		<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    	<div id="echartsMain" style="width: 1200px;height:600px;"></div>
	</div>
</div>

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