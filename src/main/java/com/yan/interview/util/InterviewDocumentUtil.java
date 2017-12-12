package com.yan.interview.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.yan.interview.model.Interview;

public class InterviewDocumentUtil {

	public static Document interviewToDocument(Interview interview){
		Document doc = new Document();
		
		Class clazz = Interview.class;
		Method[] methods = clazz.getDeclaredMethods();
		if(methods != null && methods.length > 0){
			for(Method method : methods){
				String methodName = method.getName();
				//遍历get方法
				if(methodName.startsWith("get")){
					String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
					Class returnType = method.getReturnType();
					Object value = null;
					try {
						value = method.invoke(interview, new Object[0]);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
					
					if(value != null){
						if("id".equals(fieldName)){
							if(!"".equals(value.toString().trim())){
								doc.append("_id", new ObjectId(value.toString()));
							}
						}else{
							doc.append(fieldName, value);
						}
					}
					
				}
				
			}
		}
		
		return doc;
	}
	
	public static Interview documentToInterview(Document document){
		Interview interview = null;
		if(document != null){
			interview = new Interview();
			
			Class clazz = Interview.class;
			Method[] methods = clazz.getDeclaredMethods();
			if(methods != null && methods.length > 0){
				for(Method method : methods){
					String methodName = method.getName();
					//遍历get方法
					if(methodName.startsWith("get")){
						String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
						Class returnType = method.getReturnType();
						
						String setterMethodName = "set" + methodName.substring(3);
						
						try {
							Method setterMethod = clazz.getMethod(setterMethodName, returnType);
							
							Object value = null;
							
							if("id".equals(fieldName)){
								value = document.get("_id");
								value = value.toString();
							}else{
								value = document.get(fieldName);
							}
							
							setterMethod.invoke(interview, value);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					
				}
			}
		}
		
		return interview;
	}
}
