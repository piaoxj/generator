package com.ldl.code.java;

import com.ldl.code.pdm.Column;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;

public class JdbcType2Java {

	public static String calculateJavaType(Column introspectedColumn) {
        String answer;
		switch (introspectedColumn.getJdbcType()) {
		case Types.ARRAY:
			answer = Object.class.getName();
			break;

		case Types.BIGINT:
		    answer = Long.class.getName();
			break;

		case Types.BINARY:
		    answer = "byte[]"; //$NON-NLS-1$
			break;

		case Types.BIT:
		    answer = Boolean.class.getName();
			break;

		case Types.BLOB:
		    answer = "byte[]"; //$NON-NLS-1$
			break;

		case Types.BOOLEAN:
		    answer = Boolean.class.getName();
			break;

		case Types.CHAR:
		    answer = String.class.getName();
			break;

		case Types.CLOB:
		    answer = String.class.getName();
			break;

		case Types.DATALINK:
		    answer = Object.class.getName();
			break;

		case Types.DATE:
		    answer = Date.class.getName();
			break;

		case Types.DECIMAL:
			if (introspectedColumn.getScale() > 0 || introspectedColumn.getLength() > 18 ) {
			    answer = BigDecimal.class.getName();
			} else if (introspectedColumn.getLength() > 9) {
			    answer = Long.class.getName();
			} else if (introspectedColumn.getLength() > 4) {
			    answer = Integer.class.getName();
			} else {
			    answer = Short.class.getName();
			}
			break;

		case Types.DISTINCT:
		    answer = Object.class.getName();
			break;

		case Types.DOUBLE:
		    answer = Double.class.getName();
			break;

		case Types.FLOAT:
		    answer = Double.class.getName();
			break;

		case Types.INTEGER:
		    answer = Integer.class.getName();
			break;

		case Types.JAVA_OBJECT:
		    answer = Object.class.getName();
			break;

		case Types.LONGVARBINARY:
		    answer = "byte[]"; //$NON-NLS-1$
			break;

		case Types.LONGVARCHAR:
		    answer = String.class.getName();
			break;

		case Types.NULL:
		    answer = Object.class.getName();
			break;

		case Types.NUMERIC:
			if (introspectedColumn.getScale() > 0 || introspectedColumn.getLength() > 18 ) {
			    answer = BigDecimal.class.getName();
			} else if (introspectedColumn.getLength() > 9) {
			    answer = Long.class.getName();
			} else if (introspectedColumn.getLength() > 4) {
			    answer = Integer.class.getName();
			} else {
			    answer = Short.class.getName();
			}
			break;

		case Types.OTHER:
		    answer = Object.class.getName();
			break;

		case Types.REAL:
		    answer = Float.class.getName();
			break;

		case Types.REF:
		    answer = Object.class.getName();
			break;

		case Types.SMALLINT:
		    answer = Short.class.getName();
			break;

		case Types.STRUCT:
		    answer = Object.class.getName();
			break;

		case Types.TIME:
		    answer = Date.class.getName();
			break;

		case Types.TIMESTAMP:
		    answer = Date.class.getName();
			break;

		case Types.TINYINT:
		    answer = Byte.class.getName();
			break;

		case Types.VARBINARY:
		    answer = "byte[]"; //$NON-NLS-1$
			break;

		case Types.VARCHAR:
		    answer = String.class.getName();
			break;

		default:
		    answer = null;
            break;
		}

        return answer;
	}
	
	public static String importJavaType(Column introspectedColumn) {
        String answer = null;
		switch (introspectedColumn.getJdbcType()) {
		case Types.ARRAY:
			break;
		case Types.BIGINT:
			break;
		case Types.BINARY:
			break;
		case Types.BIT:
			break;
		case Types.BLOB:
			break;
		case Types.BOOLEAN:
			break;
		case Types.CHAR:
			break;
		case Types.CLOB:
			break;
		case Types.DATALINK:
			break;
		case Types.DATE:
		    answer = Date.class.getName();
			break;
		case Types.DECIMAL:
			if (introspectedColumn.getScale() > 0 || introspectedColumn.getLength() > 18 ) {
			    answer = BigDecimal.class.getName();
			} else if (introspectedColumn.getLength() > 9) {
			} else if (introspectedColumn.getLength() > 4) {
			} else {
			}
			break;
		case Types.DISTINCT:
			break;
		case Types.DOUBLE:
			break;
		case Types.FLOAT:
			break;
		case Types.INTEGER:
			break;
		case Types.JAVA_OBJECT:
			break;
		case Types.LONGVARBINARY:
			break;
		case Types.LONGVARCHAR:
			break;
		case Types.NULL:
			break;
		case Types.NUMERIC:
			if (introspectedColumn.getScale() > 0 || introspectedColumn.getLength() > 18 ) {
			    answer = BigDecimal.class.getName();
			} else if (introspectedColumn.getLength() > 9) {
			} else if (introspectedColumn.getLength() > 4) {
			} else {
			}
			break;
		case Types.OTHER:
			break;
		case Types.REAL:
			break;
		case Types.REF:
			break;
		case Types.SMALLINT:
			break;
		case Types.STRUCT:
			break;
		case Types.TIME:
		    answer = Date.class.getName();
			break;
		case Types.TIMESTAMP:
		    answer = Date.class.getName();
			break;
		case Types.TINYINT:
			break;
		case Types.VARBINARY:
			break;
		case Types.VARCHAR:
			break;
		default:
		    answer = null;
            break;
		}

        return answer;
	}
}
