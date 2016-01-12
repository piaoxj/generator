package com.mls.paymonitordao.mysql;
import java.util.List;
import com.mls.paymonitordao.domain.${entity};

public interface ${entity}Dao{

	public int insert${entity}BySelective(${entity} obj);

	public ${entity} select${entity}ByPrimaryKey(java.lang.Long key);

	public List<${entity}> select${entity}BySelective(${entity} obj);

	public int count${entity}BySelective(${entity} obj);

	public int update${entity}BySelective(${entity} obj);

}

