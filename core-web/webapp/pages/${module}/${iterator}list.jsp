<%@ page contentType="text/plain;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
    <json:property name="page">${'$'}{page.pageNo}</json:property>
    <json:property name="total">${'$'}{page.totalRecord}</json:property>
    <json:array name="rows">
    	<c:forEach items="${'$'}{page.results}" var="entity">
    		<json:object>
			<#if (table.columnList?size > 0)>
			<#list table.columnList as column>
					<#if (column.dataType == "date" || column.dataType == "timestamp")>
					<json:property name="${column.code}" ><fmt:formatDate value="${'$'}{entity.${column.code}}" pattern="yyyy-MM-dd HH:mm:ss"/></json:property>	<#elseif (column.code?index_of('Amount')>0) >
					<json:property name="${column.code}" ><fmt:formatNumber value="${'$'}{entity.${column.code}/100}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></json:property>
					<#else>
					<json:property name="${column.code}" >${'$'}{entity.${column.code}}</json:property>
					</#if>					
			</#list>
			</#if>
        	</json:object>
    	</c:forEach>
    </json:array>
</json:object>
   