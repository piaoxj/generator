package com.mls.paymonitordao.domain;
import java.io.Serializable;

public class ${entity} implements Serializable{
	private static  final  long  serialVersionUID  =  1L; 
	
	<#if (table.propertyBeanList?size > 0)>
	<#list table.propertyBeanList as column>
		private ${column.type} ${column.name};
	</#list>
	</#if>

	<#if (table.propertyBeanList?size > 0)>
	<#list table.propertyBeanList as property>
	public ${property.type} ${property.getGetMethod()}(){
			return this.${property.name};
	}	
	public void ${property.getSetMethod()}(${property.type} ${property.name} ){
			this.${property.name} = ${property.name};
	}			
	</#list>
	</#if>
}