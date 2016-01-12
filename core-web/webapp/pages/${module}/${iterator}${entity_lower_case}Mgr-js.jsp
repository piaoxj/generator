<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<script type="text/javascript">
$(function () {
    var entityManagement = new EntityManagement();
    entityManagement.entityName = "${table.comment}";
    entityManagement.entityPath = "/${module}/${entity?lower_case}mgr";
    entityManagement.entityTable = "management";
    $("#" + entityManagement.entityTable).flexigrid({
        url:'list.do',
        dataType:'json',
        colModel:[
            <#if (table.columnList?size > 0)>
			<#assign x=0 />
			<#list table.columnList as column>
					<#assign x=x+1 />
					<#if (column.code?index_of('Amount')>0)>
					{display:'${column.comment}', name:'${column.code}', width:140, sortable:false,align:'right'}<#if (x<table.columnList?size) >,</#if>
					<#else>
					{display:'${column.comment}', name:'${column.code}', width:140, sortable:false, align:'left'}<#if (x<table.columnList?size) >,</#if>
					</#if>					
			</#list>
			</#if>
        ],
        buttons:[
			<security:authorize ifAnyGranted="AUTH_ALL,AUTH_ADD_${entity?upper_case}">
             	{name:'新增', bclass:'add', onpress:entityManagement.add},
             	{separator:true},
             </security:authorize>
			<security:authorize ifAnyGranted="AUTH_ALL,AUTH_EDIT_${entity?upper_case}">
             	{name:'修改', bclass:'edit', onpress:entityManagement.edit},
             	{separator:true},
             </security:authorize>
             <security:authorize ifAnyGranted="AUTH_ALL,AUTH_EXPORT,AUTH_EXPORT_${entity?upper_case}">
             	{name:'导出', bclass:'edit', onpress:entityManagement.exportRecords},
             	{separator:true}
             </security:authorize>
        ],
        onSuccess:entityManagement.sumAmount,
        searchCheck:entityManagement.check
    });

});
</script>