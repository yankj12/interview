package com.yan.interview.excel;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yan.interview.dao.InterviewMongoDaoUtil;
import com.yan.interview.model.Interview;
import com.yan.interview.util.InterviewColumnNameMapping;

public class ReadInterviewXlsx {

	public static void main(String[] args) throws Exception {
		FileInputStream fs = new FileInputStream(new File("C:\\Users\\Yan\\Desktop\\约面试.xlsx"));
		boolean result = readSalesFeeConfigExcel(fs);
		System.out.println(result);
	}

	static boolean readSalesFeeConfigExcel(FileInputStream finlViewDataIn){

		try {
			// 创建对Excel工作簿文件的引用
			XSSFWorkbook workbook = new XSSFWorkbook(finlViewDataIn);
			XSSFSheet aSheet = workbook.getSheetAt(0);// 获得一个sheet
			
			int tableHeadRowNum = 3;
			int lastRowNum = aSheet.getLastRowNum();
			
			Map<Integer, String> tableHeadColIndexAndColNameMap = new HashMap<Integer, String>();
			XSSFRow tableHeadRow = aSheet.getRow(tableHeadRowNum-1);
			int tableHeadLastColNum = tableHeadRow.getLastCellNum();
			for(int colNum=0;colNum<tableHeadLastColNum;colNum++){
				//电话沟通备注	姓名	拼音	性别	手机	邮箱	出生年月	学校	专业	学历	毕业时间	工作年限	阶段	电话约面试时间	预约一面时间	一面面试人	预约复试时间	一面评价
				XSSFCell cell = tableHeadRow.getCell(colNum);
				String colName = null;
				if(cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK){
					colName = cell.getStringCellValue().trim();
				}
				tableHeadColIndexAndColNameMap.put(colNum, colName);
			}
			
			//循环每一行
			for(int rowNum = tableHeadRowNum;rowNum<lastRowNum;rowNum++){
				Class clazz = Interview.class;
				Interview interview = (Interview)clazz.newInstance();
				
				XSSFRow row = aSheet.getRow(rowNum);
				//int lastColNum = row.getLastCellNum();
				
				for(int colNum=0;colNum<tableHeadLastColNum;colNum++){
					//先获取列的中文名
					String colCName = tableHeadColIndexAndColNameMap.get(colNum);
					String colEName = InterviewColumnNameMapping.colCNameToEName(colCName);
					String getterMethodName = "get" + colEName.substring(0, 1).toUpperCase() + colEName.substring(1);
					String setterMethodName = "set" + colEName.substring(0, 1).toUpperCase() + colEName.substring(1);
					
					Method getterMethod = clazz.getMethod(getterMethodName, new Class[0]);
					Class returnType = getterMethod.getReturnType();
					Method setterMethod = clazz.getMethod(setterMethodName, returnType);
					
					XSSFCell cell = row.getCell(colNum);
					
					if(cell != null){
						if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
							double cellValue = cell.getNumericCellValue();
							Object value = returnType.cast(cellValue);
							setterMethod.invoke(interview, value);
							//System.out.println(cellValue);
						}else{
							String cellValue = cell.getStringCellValue();
							//System.out.println(rowNum + "," + colNum + "," + colCName + ":" + cellValue);
							if(cellValue != null){
								Object value = null;
								if(returnType == Integer.class || returnType == int.class){
									value = Integer.parseInt(cellValue);
								}else if(returnType == Double.class || returnType == double.class){
									value = Double.parseDouble(cellValue);
								}else if(returnType == String.class){
									value = cellValue;
								}else{
									value = cellValue;
								}
								setterMethod.invoke(interview, value);
							}
						}
						
					}
					
				}
				//System.out.println(interview.getUserName());
				
				InterviewMongoDaoUtil interviewMongoDaoUtil = new InterviewMongoDaoUtil();
				
				//根据姓名、手机号码、邮箱（或者的关系）查找是否有对应记录
				String userName = interview.getUserName();
				String phone = interview.getPhone();
				String email = interview.getEmail();
				List<Interview> interviews = interviewMongoDaoUtil.findInterviewDocumentsByUserNameOrPhoneOrEmail(userName, phone, email);
				//存在多条记录，优先级，手机号码》邮箱》姓名
				if(interviews != null && interviews.size() > 0){
					//修改
					Interview userNameInterview = null;
					Interview phoneInterview = null;
					Interview emailInterview = null;
					
					for(Interview view:interviews){
						if(userName != null && !"".equals(userName.trim()) 
								&& userNameInterview == null
								&& view.getUserName() != null && !"".equals(view.getUserName().trim())
								&& userName.trim().equals(view.getUserName().trim())){
							userNameInterview = view;
						}
						if(phone != null && !"".equals(phone.trim()) 
								&& phoneInterview == null
								&& view.getPhone() != null && !"".equals(view.getPhone().trim())
								&& phone.trim().equals(view.getPhone().trim())){
							phoneInterview = view;
						}
						if(email != null && !"".equals(email.trim()) 
								&& emailInterview == null
								&& view.getEmail() != null && !"".equals(view.getEmail().trim())
								&& email.trim().equals(view.getEmail().trim())){
							emailInterview = view;
						}
					}
					
					Interview interviewTmp = null;
					if(phoneInterview != null){
						interviewTmp = phoneInterview;
					}else{
						if(emailInterview != null){
							interviewTmp = emailInterview;
						}else{
							interviewTmp = userNameInterview;
						}
					}
					
					if(interviewTmp != null && interviewTmp.getId() != null){
						interview.setId(interviewTmp.getId());
						
						interview.setUpdateTime(new Date());
						interviewMongoDaoUtil.updateInterview(interview);
					}
				}else{
					//新增
					interview.setInsertTime(new Date());
					interview.setUpdateTime(new Date());
					interviewMongoDaoUtil.insertInterview(interview);
				}
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
