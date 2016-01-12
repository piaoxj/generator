package com.ldl.code.pdm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ldl.code.java.PropertyBean;
import com.ldl.code.util.StringUtils;

public class Table {
    private String catalog;
    private String schema;
	private String name;
	private String code;//表名
	private String comment;
	private String creator;
	private Long creationDate;
	private Column primaryColumn;//主键字段
	private List<Column> columnList = new ArrayList<Column>();
    private List<String> primaryKeyList = new ArrayList<String>();
    private List<PropertyBean> propertyBeanList = new ArrayList<PropertyBean>();
    private List<String> importList = new ArrayList<String>();
    private String serialVersionUID;
    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getComment() {
		if(comment == null || "".equals(comment)){
			return name;
		}		
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreationDate() {
		SimpleDateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(creationDate);
	}
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}

	public Column getPrimaryColumn() {
		return primaryColumn;
	}
	public void setPrimaryColumn(Column primaryColumn) {
		this.primaryColumn = primaryColumn;
	}
	public List<Column> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}
	
	public String getTableName(){
		String tableName = "";
		if(code == null || "".equals(code)){
			return tableName;
		}
		String[] codes = code.split("_");
		for (int i = 0; i < codes.length; i++) {
			tableName += StringUtils.capitalize(codes[i].toLowerCase());
		}
		return tableName;
	}


    public void addColumn(Column column)
    {
        this.columnList.add(column);
    }

    public void addPrimaryKeyColumn(String column)
    {
        this.primaryKeyList.add(column);
    }

    public List<String> getPrimaryKeyList() {
        return primaryKeyList;
    }

    public void setPrimaryKeyList(List<String> primaryKeyList) {
        this.primaryKeyList = primaryKeyList;
    }

    public List<PropertyBean> getPropertyBeanList() {
        return propertyBeanList;
    }

    public void setPropertyBeanList(List<PropertyBean> propertyBeanList) {
        this.propertyBeanList = propertyBeanList;
    }

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public String getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setSerialVersionUID(String serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }
}
