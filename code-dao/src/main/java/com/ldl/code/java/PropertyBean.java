package com.ldl.code.java;

public class PropertyBean {
	
	private String type;
	private String name;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGetMethod()
	{
		String ret = "get";
		String stemp = this.name.substring(0,1);
		String stemp1 = this.name.substring(1);
		
		ret = ret + stemp.toUpperCase() + stemp1;
		return ret;		
	}	
	
	public String getSetMethod()
	{
		String ret = "set";
		String stemp = this.name.substring(0,1);
		String stemp1 = this.name.substring(1);
		
		ret = ret + stemp.toUpperCase() + stemp1;
		return ret;		
	}	
	
}
