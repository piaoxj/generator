package com.ldl.code.pdm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

public class PdmParser {
	
	public static List<Table> parse(File pdmFile) throws DocumentException{
		List<Table> list = new ArrayList<Table>();
		if(pdmFile == null || !pdmFile.exists() || pdmFile.isDirectory()){
			return list;
		}
			
		SAXReader reader = new SAXReader();
        Document document = reader.read(pdmFile);
        Map<String,String> map = new HashMap<String, String>();  
        map.put("c","collection"); 
        XPath path = document.createXPath("//c:Tables"); 
        path.setNamespaceURIs(map); 
        Element tableElements = (Element)path.selectSingleNode(document);
        if(tableElements == null){
        	return list;
        }
        Column column = null;
        Table table = null;
        List<Column> columnList = null;
        for (Iterator<Element> it = tableElements.elementIterator("Table"); it.hasNext();) {
        	table = new Table();
        	columnList = new ArrayList<Column>();
			Element tableElement = it.next();
			String tableCode = tableElement.elementText("Code"); 
			if(tableCode == null || "".equals(tableCode)){
				continue;
			}
			table.setCode(tableCode);
			table.setName(tableElement.elementText("Name"));
			table.setComment(tableElement.elementText("Comment"));
            System.out.println("****************************"+tableElement.elementText("Comment"));
			table.setCreator(tableElement.elementText("Creator"));
			table.setCreationDate(Long.valueOf(tableElement.elementText("CreationDate")));
			String primaryColumnId = null;
			Element primaryKeyElement = tableElement.element("PrimaryKey");
			if(primaryKeyElement != null){
				String keyId = primaryKeyElement.element("Key").attributeValue("Ref");
				Attribute primaryColumnIdAttribute = (Attribute)tableElement.selectSingleNode("c:Keys/o:Key[@Id=\"" + keyId + "\"]/c:Key.Columns/o:Column/@Ref");
				if(primaryColumnIdAttribute != null){
					primaryColumnId = primaryColumnIdAttribute.getValue();
				}
			}
			
			Element columnsElement = tableElement.element("Columns");
			if(columnsElement == null){
				return list;
			}
			Iterator<Element> columnsIt = columnsElement.elementIterator("Column");
			while (columnsIt.hasNext()) {
				Element columnElement = columnsIt.next();
				column = new Column();
				column.setName(columnElement.elementText("Name"));
				column.setCode(columnElement.elementText("Code"));
				column.setComment(columnElement.elementText("Comment"));
                System.out.println("****************************列名称："+columnElement.elementText("Name"));
				column.setDataType(columnElement.elementText("DataType"));
				if(primaryColumnId != null && primaryColumnId.equals(columnElement.attributeValue("Id"))){
					table.setPrimaryColumn(column);
				}
				columnList.add(column);
			}
			table.setColumnList(columnList);
			list.add(table);
		}
		return list;
	};
	
/*	public static void main(String[] args) throws DocumentException {
		File pdmFile = new File("D:/java_project/freemarker_test/src/com/ldl/freemarker/ff/temp.xml");
		
		List<Table> list = PdmParser.parse(pdmFile);
		for (Table table : list) {
			System.out.println("----------------------------");
			System.out.println(table.getTableName() + ":" + table.getName());
			for (int i = 0; i < table.getColumnList().size(); i++) {
				System.out.println("--------" + table.getColumnList().get(i).getColumnName() + ":" +  table.getColumnList().get(i).getJavaDataType());
			}
		}
	}	*/
}
