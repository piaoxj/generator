<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/public_input.jsp" %>
<script>
$().ready(function() {
	 $("#inputForm").validate({
        rules: {
			<#if (table.columnList?size > 0)>
			<#assign x=0 />
			<#list table.columnList as column>
			<#assign x=x+1 />
			${column.code}: {
				required: true,
			}<#if (x<table.columnList?size) >,</#if>
			</#list>
			</#if>
  		},
        messages: {
			<#if (table.columnList?size > 0)>
			<#assign x=0 />
			<#list table.columnList as column>
			<#assign x=x+1 />
			${column.code}:"必填字段",
			</#list>
			</#if>
  		}
  		
    });
});
</script>
	<title>${table.comment}</title>
</head>

<body>
<div>
	<form id="inputForm" action="save.do" method="post">
        <div id="message"><font color="red">${'$'}{error} ${'$'}{msg}</font></div>
        <div>${table.comment}</div>
		<#if (table.primaryKeyList?size > 0)>
		<#list table.primaryKeyList as pri>
		<input type="hidden" id="${pri}" name="${pri}" value="${'$'}{entity.${pri}}"/>			
		</#list>
		</#if>
		
		<table class="noborder">
			<#if (table.columnList?size > 0)>
			<#list table.columnList as column>
			<tr>
				<td>${column.comment}:</td>
				<td>
					<input type="text" name="${column.code}" value="${'$'}{entity.${column.code}}" size="15"/> 		
				</td>
			</tr>					
			</#list>
			</#if>
		</table>
         <div align="left">
                <input type="submit" value="提交"/>&nbsp;
                <input type="reset" class="input-button" value="重置"/>
          </div>
	</form>
</div>
</body>
</html>

