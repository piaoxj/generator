package com.zebra.pay.admin.controller.paymonitor.${module?replace('/', '.' )};

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zebra.pay.admin.utils.Constants;
import com.zebra.pay.admin.ExportUtil;
import com.zebra.pay.admin.controller.SimpleBaseController;
import com.zebra.pay.admin.manager.${entity}Manager;
import com.zebra.pay.admin.utils.Page;
import com.zebra.pay.admin.enums.${entity}Status;
import com.zebra.pay.domain.${entity};
import com.zebra.pay.uuidGenerate.util.FormatUtil;


@Controller
@RequestMapping(value="/${module}/${entity?lower_case}mgr")
public class ${entity}MgrController extends SimpleBaseController<${entity}> {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ${entity}Manager ${entity?uncap_first}Manager;
	
	@RequestMapping(value = "/main.do")
	public String main(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		//model.addAttribute("allStatus", Constants.get${entity}InfoStatus());
		return "/${module}/${entity?lower_case}mgr/${entity?uncap_first}Mgr.jsp";
	}
	
	@RequestMapping(value = "/list.do")
	public String list(
			HttpServletRequest request, 
			HttpServletResponse response,Model model) throws Exception {
		Page<${entity}> page = buildPage(request);
		Map<String, Object> searchParams = buildParamsFromHttpRequest(request,page);
		page = ${entity?uncap_first}Manager.searchNewByPage(searchParams,page);
		model.addAttribute("page", page);
		
		return "/${module}/${entity?lower_case}mgr/list.jsp";
	}


	@RequestMapping(value="/input.do")
	public String input(HttpServletRequest request, 
			 HttpServletResponse response,
			 Model model) {
		${entity} entity = null;
		String id=request.getParameter("id");
		try {
			if(id!=null){
					entity = ${entity?uncap_first}Manager.getByPrimaryKey(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(entity == null){
			entity = new ${entity}();
		}
		model.addAttribute("entity", entity);
		return "/${module}/${entity?lower_case}mgr/input.jsp";
	}


	@RequestMapping(value="/save.do")
	public String save(HttpServletRequest request,
			HttpServletResponse response,
			${entity} entity,Model model){
		String msg="";
		${entity} entity2=null;		
		if(entity==null){
			msg="0";
		}else {
			try {
				entity2=${entity?uncap_first}Manager.getByPrimaryKey(entity.getId());
				if(entity2!=null){
					${entity?uncap_first}Manager.update(entity);
					msg="1";
				}else{
					${entity?uncap_first}Manager.save(entity);
					msg="2";
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
				msg="3";
			}
		}
		model.addAttribute("msg", msg);
		model.addAttribute("entity", entity);
		
		return "/${module}/${entity?lower_case}mgr/input.jsp";
	}

	
	@RequestMapping(value = "/sumAmount.do")
	public void sumAmount(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		Map<String, Object> searchParams = buildParamsFromHttpRequest(request);
		long totalAmount = 0L;
		
		if(searchParams.containsKey("status") && !(${entity}Status.FINISH.value()+"").equals(searchParams.get("status"))){
			totalAmount = 0L;
		}else {
			searchParams.put("status", ${entity}Status.FINISH.value()+"");
			totalAmount = ${entity?uncap_first}Manager.sum(searchParams);
		}
		
		BigDecimal bd = new BigDecimal(totalAmount/100.00);
	    render(response, bd.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString());
	}
	
	

	@RequestMapping(value = "/export.do")
	public void export(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		Map<String, Object> searchParams = buildParamsFromHttpRequest(request);
		String zipName = generateZipName(searchParams);
//		System.out.println("1"+zipName);
		try {
			String zipPath = new ExportUtil<${entity}>(zipName, searchParams,"${entity}").startExportWithMap();
			responseFile(response, zipPath);
		} catch (Exception e) {
			//throw e;
			e.printStackTrace();
			render(response, "<script>alert('"+e.getMessage()+"');</script>");
		}
	}

	
	private String generateZipName(Map<String, Object> searchParams){
		
		StringBuilder zipNameBuilder = new StringBuilder();
		String timeQueryTypeStart = (String) searchParams.get("queryStart");
		String timeQueryTypeEnd = (String) searchParams.get("queryEnd");
		String timeQueryType = (String) searchParams.get("queryType");
		zipNameBuilder.append("1");
		if(!org.apache.commons.lang.StringUtils.isBlank(timeQueryTypeStart) || 
				!org.apache.commons.lang.StringUtils.isBlank(timeQueryTypeEnd))
		{	
			zipNameBuilder.append("_"+timeQueryType+"[");
			zipNameBuilder.append(org.apache.commons.lang.StringUtils.isBlank(timeQueryTypeStart)?"":FormatUtil.formatDate(FormatUtil.parseDate(timeQueryTypeStart, FormatUtil.DATA_FOAMAT_YYYYMMDDHHMMSS),FormatUtil.DATA_FOAMAT_SIMPLE_YYYYMMDDHHMMSS) + "-");
			zipNameBuilder.append(org.apache.commons.lang.StringUtils.isBlank(timeQueryTypeEnd)?"":FormatUtil.formatDate(FormatUtil.parseDate(timeQueryTypeEnd, FormatUtil.DATA_FOAMAT_YYYYMMDDHHMMSS),FormatUtil.DATA_FOAMAT_SIMPLE_YYYYMMDDHHMMSS));
			zipNameBuilder.append("]");
		}
		String tppCode = (String) searchParams.get("tppCode");
		if(!org.apache.commons.lang.StringUtils.isBlank(tppCode)){
			zipNameBuilder.append("_"+tppCode);
		}
		
		String orderNo = (String) searchParams.get("orderNo");
		if(!org.apache.commons.lang.StringUtils.isBlank(orderNo)){
			zipNameBuilder.append("_"+orderNo);
		}
		
		String statusStr = (String) searchParams.get("status");
		if(!org.apache.commons.lang.StringUtils.isBlank(statusStr)){
			zipNameBuilder.append("_"+Constants.getOrderInfoStatus().get(Byte.valueOf(statusStr)));
		}
		
		return zipNameBuilder.toString();
	}


	
	@RequestMapping(value="/check.do")
	public void check(HttpServletRequest request, 
			 HttpServletResponse response) {
		Boolean value=true;
		String key = request.getParameter("key");
		String Oldkey=request.getParameter("Oldkey");
		try {
			if(key.equals(Oldkey)){
				value=true; 
			}else {
				${entity} entity = ${entity?uncap_first}Manager.getByPrimaryKey(key);
				if(entity!=null){
					value=false;
				}
			}
			response.getWriter().write(value+"");
			response.getWriter().flush();
		} catch (Exception e) {
			throw new RuntimeException("1");
		}
	}

}
