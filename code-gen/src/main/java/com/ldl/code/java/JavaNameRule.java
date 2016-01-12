package com.ldl.code.java;

import com.ldl.code.pdm.Table;

import java.util.Date;


public class JavaNameRule {
	
	public String getPackageName(String packageName,String type)
	{
		String sret = packageName;
		if(GlobalConst.JAVA_TYPE_DAO.equalsIgnoreCase(type))
		{
			sret = sret + "." + GlobalConst.JAVA_TYPE_DAO.toLowerCase();
		}
		else if(GlobalConst.JAVA_TYPE_DOMAIN.equalsIgnoreCase(type))
		{
			sret = sret + "." + GlobalConst.JAVA_TYPE_DOMAIN.toLowerCase();
		}
		
		return sret;
	}
	
	public String getFieldName(String columnName)
	{
		if(columnName == null || columnName.trim().length() <= 0)
			return "";
		
		return columnName.toLowerCase();
	}
	
	public String getGetMethod(String columnName)
	{
		if(columnName == null || columnName.trim().length() <= 0)
			return "";
		
		String head = columnName.substring(0,1);
		String tail = columnName.substring(1);
		return "get" + head.toUpperCase() + tail.toLowerCase();
	}
	
	public String getSetMethod(String columnName)
	{
		if(columnName == null || columnName.trim().length() <= 0)
			return "";
		
		String head = columnName.substring(0,1);
		String tail = columnName.substring(1);
		return "set" + head.toUpperCase() + tail.toLowerCase();
	}
	
	public String getClassName(Table table,String type)
	{
		String sret = "";
		String[] stemps = table.getTableName().split("_");
		for(String stemp : stemps)
		{
			String head = stemp.substring(0,1);
			String tail = stemp.substring(1);
			sret = sret + head.toUpperCase() + tail.toLowerCase();
		}
		
		if(GlobalConst.JAVA_TYPE_DAO.equalsIgnoreCase(type))
		{
			sret = sret + GlobalConst.JAVA_TYPE_DAO;
		}
		
		return sret;
	}
	
	public String getFilePath(String targetPath,String packageName)
	{
		String sret = targetPath;
		if(!sret.substring(sret.length() - 1).equals("/"))
			sret = sret + "/";
		
		String[] stemps = packageName.split("\\.");
		for(String stemp : stemps)
		{
			if(stemp.trim().length() > 0)
				sret = sret + stemp.trim() + "/"; 
		}
		
		return sret;
	}
	
	public String getFileName(Table table,String type)
	{
		String sret = this.getClassName(table,type);
		if(sret != null && !sret.equals(""))
			sret = sret + ".java";
		
		return sret;
	}
	

}
