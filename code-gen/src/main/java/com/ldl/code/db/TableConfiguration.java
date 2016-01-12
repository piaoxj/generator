package com.ldl.code.db;

public class TableConfiguration {
	
	private String catalog;
	private String schema;
	private String tableName;
	
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
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public boolean equals(TableConfiguration obj)
	{
		System.out.println("TableConfiguration:equals");
		if(obj == null)
			return false;
		
		boolean bret = true;
		if(obj.getCatalog() != this.catalog && !obj.getCatalog().equals(catalog))
			bret = false;
		if(obj.getSchema() != this.schema && !obj.getSchema().equals(schema))
			bret = false;
		if(obj.getTableName() != this.tableName && !obj.getTableName().equals(tableName))
			bret = false;
		
		return bret;
	}
}
