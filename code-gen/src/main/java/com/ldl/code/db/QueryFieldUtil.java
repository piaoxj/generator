package com.ldl.code.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ldl.code.java.JdbcType2Java;
import com.ldl.code.java.PropertyBean;
import com.ldl.code.pdm.Column;
import com.ldl.code.pdm.Table;

public class QueryFieldUtil {

	public QueryFieldUtil() {

	}

	public List<Table> queryFields(DBConfig config) {
		List<Table> tableInfoList = new ArrayList<Table>();
		Connection conn = null;
		try {
			List<TableConfiguration> tableList = (List<TableConfiguration>) config
					.getTableList();
			if (tableList == null || tableList.size() <= 0)
				return tableInfoList;

			conn = ConnectionFactory.getInstance().getConnection(config);
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			for (TableConfiguration table : tableList) {
                Table tableInfo = new Table();

				String localCatalog = table.getCatalog();
				String localSchema = table.getSchema();
				String localTableName = table.getTableName();
				if (databaseMetaData.storesLowerCaseIdentifiers()) {
					localCatalog = localCatalog == null ? null : localCatalog
							.toLowerCase();
					localSchema = localSchema == null ? null : localSchema
							.toLowerCase();
					localTableName = localTableName == null ? null
							: localTableName.toLowerCase();
				} else if (databaseMetaData.storesUpperCaseIdentifiers()) {
					localCatalog = localCatalog == null ? null : localCatalog
							.toUpperCase();
					localSchema = localSchema == null ? null : localSchema
							.toUpperCase();
					localTableName = localTableName == null ? null
							: localTableName.toUpperCase();
				}

                Statement stmt = conn.createStatement();
                ResultSet tableRs = stmt.executeQuery("SHOW CREATE TABLE " + localTableName);
                if(tableRs != null && tableRs.next()) {
                    String create = tableRs.getString(2);
                    String comment = parse(create);
                    tableInfo.setComment(comment);
                }

				ResultSet rs = databaseMetaData.getColumns(localCatalog,
						localSchema, localTableName, null);
                tableInfo.setSerialVersionUID(System.nanoTime()+"L");
				while (rs.next()) {
					tableInfo.setCatalog(rs.getString("TABLE_CAT"));
					tableInfo.setSchema(rs.getString("TABLE_SCHEM"));
					tableInfo.setName(rs.getString("TABLE_NAME"));
                    tableInfo.setCode(rs.getString("TABLE_NAME"));

					Column introspectedColumn = new Column();
					introspectedColumn.setTableAlias(table.getTableName());
                    introspectedColumn.setName(rs.getString("COLUMN_NAME"));
                    introspectedColumn.setJdbcType(rs.getInt("DATA_TYPE"));
                    introspectedColumn.setDataType(JdbcTypeNameTranslator.getJdbcTypeName(rs.getInt("DATA_TYPE"))); //$NON-NLS-1$
					introspectedColumn.setLength(rs.getInt("COLUMN_SIZE")); //$NON-NLS-1$
                    introspectedColumn.setCode(rs.getString("COLUMN_NAME"));
					/*introspectedColumn.setActualColumnName(rs
							.getString("COLUMN_NAME"));*/ //$NON-NLS-1$
					introspectedColumn
							.setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable); //$NON-NLS-1$
					introspectedColumn.setScale(rs.getInt("DECIMAL_DIGITS")); //$NON-NLS-1$
                    introspectedColumn.setComment(rs.getString("REMARKS"));

					tableInfo.addColumn(introspectedColumn);

                    PropertyBean pb = new PropertyBean();

                    pb.setName(convertFirstUpper(getFieldName(rs.getString("COLUMN_NAME"))));
                    pb.setType(JdbcType2Java.calculateJavaType(introspectedColumn));
                    String importType = JdbcType2Java.importJavaType(introspectedColumn);
                    if(importType != null && !importType.equals("")){
                        if(importType.indexOf("java.lang") < 0 && !tableInfo.getImportList().contains(importType))
                            tableInfo.getImportList().add(importType);
                    }
                    tableInfo.getPropertyBeanList().add(pb);

				}
				closeResultSet(rs);

				rs = databaseMetaData.getPrimaryKeys(localCatalog, localSchema,
						localTableName);
				while (rs.next()) {
					tableInfo.addPrimaryKeyColumn(rs.getString("COLUMN_NAME"));
				}
				closeResultSet(rs);
				tableInfoList.add(tableInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tableInfoList;
	}

	private void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// ignore
				;
			}
		}
	}

	public Map<String, List<String>> querySchema(DBConfig config) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> schemaList = config.getSchemaList();
		Connection conn = null;
		DatabaseMetaData databaseMetaData = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection(config);
			databaseMetaData = conn.getMetaData();
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}

		for (String schema : schemaList) {
			List<String> tableList = new ArrayList<String>();
			try {
				String localSchema = schema;
				if (databaseMetaData.storesLowerCaseIdentifiers()) {
					localSchema = localSchema == null ? null : localSchema
							.toLowerCase();
				} else if (databaseMetaData.storesUpperCaseIdentifiers()) {
					localSchema = localSchema == null ? null : localSchema
							.toUpperCase();
				}

				ResultSet rs = databaseMetaData.getTables(null, localSchema,
						null, null);
				while (rs.next()) {
					String tableName = rs.getString("TABLE_NAME");
					tableList.add(tableName);
					// System.out.println(tableName);
				}
				map.put(schema, tableList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * IbatorConfig config = new IbatorConfig(); config.parseConfig(null,
		 * null); QueryFieldUtil obj = new QueryFieldUtil();
		 * obj.queryFields(config);
		 */
		String s = Date.class.getSimpleName();
		System.out.println(s);
        DBConfig dbConfig = new DBConfig("couponaccountdetail");
        List<String> warns = new ArrayList<String>();
        dbConfig.parseConfig(warns);
        QueryFieldUtil obj = new QueryFieldUtil();
        obj.queryFields(dbConfig);
	}

    public static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if(index < 0) {
            return "";
        }
        comment = all.substring(index+9);
        comment = comment.substring(0,comment.length() - 1);
        return comment;
    }

    protected String convertFirstUpper(String columnName){
        String[] valueArray = columnName.split("_");
        if(valueArray.length<=1){
            return columnName;
        }
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(valueArray[0]);
        for (int i = 1; i < valueArray.length; i++) {
            String first = valueArray[1].substring(0, 1).toUpperCase();
            String rest = valueArray[1].substring(1, valueArray[1].length());
            String newStr = new StringBuffer(first).append(rest).toString();
            sBuffer.append(first).append(rest);
        }
        return sBuffer.toString();


    }

    public String getFieldName(String columnName){
        if(columnName == null || columnName.trim().length() <= 0)
            return "";

        return columnName;
    }

}
