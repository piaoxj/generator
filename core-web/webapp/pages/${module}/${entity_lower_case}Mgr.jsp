<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${table.comment}</title>
    <%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/public.jsp" %>
    <%@ include file="${entity?lower_case}Mgr-js.jsp" %>
</head>

<body>
<%@ include file="/common/header.jsp" %>
<table id="management" style="display: none"></table>
<%@ include file="/common/footer.jsp" %>
<div class="sDiv2">
  <table border=1>
	<tr>
		<#list table.columnList as column>
			<td>${column.comment}: <input class="qsbox" type="text" name="filter_${column.code}" value="${'$'}{param['filter_${column.code}']}" size="15"/></td>
		</#list>
  		
  	</tr>
	<tr>		
  		<td colspan="2">
  				<select id="filter_queryType" name="filter_queryType" class="qsbox">
            		<option value="createTime">创建时间</option>
					<option value="refundConfirmTime">退款完成时间</option>
            	</select>
            	<input class="qsbox Wdate" type="text" id="filter_queryStart" name="filter_queryStart" size=20 value="${'$'}{param['filter_queryStart']}" 
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'filter_queryEnd\',{M:-1})}',maxDate:'#F{$dp.$D(\'filter_queryEnd\')||\'%y-%M-%d\'}'})" readOnly/>
	            -
	            <input class="qsbox Wdate" type="text" id="filter_queryEnd" name="filter_queryEnd" size=20 value="${'$'}{param['filter_queryEnd']}" 
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'filter_queryStart\')}',maxDate:'#F{$dp.$D(\'filter_queryStart\',{M:1})||\'%y-%M-%d\'}'})" readOnly/>
  		</td>
		<td>
  			<input type="button" value="清除"/>
  			<input type="button" value="搜索"/>
  		</td>
  	</tr>
  </table>
    列表中已完成订单的汇总金额为：<span id="sumAmount"></span>元
</div>

</body>
</html>
