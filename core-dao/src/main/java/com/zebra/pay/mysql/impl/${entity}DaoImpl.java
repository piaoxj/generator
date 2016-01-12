package com.zebra.pay.mysql.impl;
import java.util.List;
import com.zebra.pay.domain.${entity};
import com.zebra.pay.base.QueryDAO;
import com.zebra.pay.base.UpdateDAO;
import com.zebra.pay.mysql.${entity}Dao;

public class ${entity}DaoImpl implements ${entity}Dao{
	private UpdateDAO updateDAO;
	private QueryDAO queryDAO;

	public int insert${entity}BySelective(${entity} obj){
		return updateDAO.saveOrUpdateDel("com.zebra.pay.mysql.${entity}Mapper.zebra_insert${entity}BySelective",obj);
	}
	public ${entity} select${entity}ByPrimaryKey(java.lang.Long key){
		return (${entity})queryDAO.queryOne("com.zebra.pay.mysql.${entity}Mapper.zebra_select${entity}ByPrimaryKey",key);
	}

	@SuppressWarnings("unchecked")
	public List<${entity}> select${entity}BySelective(${entity} obj){
		return (List<${entity}>) queryDAO.queryList("com.zebra.pay.mysql.${entity}Mapper.zebra_select${entity}BySelective",obj);
	}
	public int count${entity}BySelective(${entity} obj){
		return (Integer)queryDAO.queryOne("com.zebra.pay.mysql.${entity}Mapper.zebra_count${entity}BySelective", obj);
	}
	public int update${entity}BySelective(${entity} obj){
		return updateDAO.saveOrUpdateDel("com.zebra.pay.mysql.${entity}Mapper.zebra_update${entity}BySelective",obj);
	}

	public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    public void setQueryDAO(QueryDAO queryDAO) {
        this.queryDAO = queryDAO;
    }

}

