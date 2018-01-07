var url;

/**
 * 页面初始化后做些东西
 */
$(document).ready(function(){
	console.log('init');
	
	// 当查询完数据之后绘制图表，图表的数据来源于datagrid
	$('#dg').datagrid({  
        onLoadSuccess:function(data){  
            //要判断或者执行的代码
        	//console.log(data);
        	drawEcharts(data);
        }  
    });
	
	
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

/**
 * 绘制一面时间分布的图表
 * @returns
 */
function drawEcharts(data){
	console.log('drawEcharts');
	
	//组装数据
	//console.log(data)
	var xAxisData = [];
	var amData = [];
	var pmData = [];
	
	var amMap = {};
	var pmMap = {};
	
	if(data != null && data.rows != null && data.rows.length > 0){
		var rows = data.rows;
		for(var i=0;i<rows.length;i++){
			//2017-09-20 AM
			var id = rows[i].id;
			var count = rows[i].count;
			
			var date = id.substring(0,10);
			var flag = id.substring(11,13);
			
			// 避免插入重复数据
			if($.inArray(date, xAxisData) == -1){
				xAxisData.push(date);
			}
			
			if(flag != null){
				if(flag == 'AM' || flag == 'am'){
					amMap[date] = count;
				}else if(flag == 'PM' || flag == 'pm'){
					pmMap[date] = count;
				}
			}
		}
	}
	
	if(xAxisData != null && xAxisData.length > 0){
		for(var i=0;i<xAxisData.length;i++){
			var date = xAxisData[i];
			//am
			var amCount = amMap[date];
			if(amCount == null){
				amCount = 0;
			}
			amData.push(amCount);
			//pm
			var pmCount = pmMap[date];
			if(pmCount == null){
				pmCount = 0;
			}
			pmData.push(pmCount);
			
		}
	}
	
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echartsMain'));

    // 指定图表的配置项和数据
    var option = {
		title: {
            text: '一面时间分布堆叠柱状图'
        },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:['AM','PM']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : xAxisData
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'AM',
	            type:'bar',
	            data:amData
	        },
	        {
	            name:'PM',
	            type:'bar',
	            data:pmData
	        }
	    ]
	};

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
