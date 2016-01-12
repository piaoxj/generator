package com.ldl.code.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.ldl.code.pdm.Table;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class DBConfig {

	private Map<String, Object> propertyMap;
	private List<String> schemaList;
	private List<TableConfiguration> tableList;
	//private Map<String, TaskInterceptor> interceptorMap;
	private List<String> interceptorString;
    private String tables;
    private String configFilePath;

	public DBConfig() {
		propertyMap = new HashMap<String, Object>();
		schemaList = new ArrayList<String>();
		tableList = new ArrayList<TableConfiguration>();
		//interceptorMap = new HashMap<String, TaskInterceptor>();
		interceptorString = new ArrayList<String>();
	}

    public DBConfig(String tables) {
        propertyMap = new HashMap<String, Object>();
        schemaList = new ArrayList<String>();
        tableList = new ArrayList<TableConfiguration>();
        interceptorString = new ArrayList<String>();
        this.tables = tables;
    }

    public DBConfig(String tables,String configFilePath) {
        this(tables);
        File configFile = new File(configFilePath);
        if(configFilePath == null || configFilePath.equals("") ||
                !configFile.exists() || configFile.isDirectory()){
           this.configFilePath = null;
        }else {
            this.configFilePath = configFilePath;
        }


    }

	public boolean parseConfig(List<String> warnings) {
		try {
            InputSource is = null;
            //如果有传入的配置文件则使用，否则使用默认配置文件
            if(configFilePath!=null){
                is = new InputSource(new FileInputStream(configFilePath));
            }else {
                is = new InputSource(DBConfig.class.getClassLoader().getResourceAsStream("code-maven-plugin-config.xml"));
            }


			Document document = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(false);
			DocumentBuilder builder = factory.newDocumentBuilder();

			try {
				document = builder.parse(is);
			} catch (Exception ex) {
				warnings.add(ex.getMessage());
				return false;
			}

			if (document == null) {
				warnings.add("parse config file error!");
				return false;
			}

			Element root = document.getDocumentElement();
			NodeList nodeList = root.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node childNode = nodeList.item(i);
				if (childNode.getNodeType() != 1) {
					continue;
				}

				if ("jdbcConnection".equals(childNode.getNodeName())) { //$NON-NLS-1$
					parseJdbcConnection(childNode);
				} else if ("generator".equals(childNode.getNodeName())) { //$NON-NLS-1$
					parseGenerator(childNode);
				} else if ("interceptorList".equals(childNode.getNodeName())) { //$NON-NLS-1$
					parseInterceptorList(childNode);
				} else if ("schemaList".equals(childNode.getNodeName())) { //$NON-NLS-1$
					parseSchemaList(childNode);
				}
			}

            parseTableList(tables);
		} catch (Exception e) {
			e.printStackTrace();
			warnings.add(e.getMessage());
			return false;
		}

		return true;
	}

	private void parseInterceptorList(Node node) {
		if (node == null)
			return;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() != 1) {
				continue;
			}

			String className = child.getAttributes().getNamedItem("className")
					.getNodeValue();
			String type = child.getAttributes().getNamedItem("type")
					.getNodeValue();
			String stemp = "<interceptor className=\"" + className
					+ "\" type=\"" + type + "\" />";
			interceptorString.add(stemp);
			try {
				ClassLoader loader = Thread.currentThread()
						.getContextClassLoader();
				Class<?> clazz = loader.loadClass(type);
				if (clazz == null) {
					clazz = Class.forName(type);
				}
				/*if (clazz != null) {
					TaskInterceptor obj = (TaskInterceptor) clazz.newInstance();
					interceptorMap.put(className, obj);
				}*/
			} catch (Throwable e) {
				e.printStackTrace();
			}

		}
	}

	private void parseSchemaList(Node node) {
		if (node == null)
			return;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() != 1) {
				continue;
			}

			String schema = child.getAttributes().getNamedItem("schema")
					.getNodeValue();
			if (!schemaList.contains(schema))
				schemaList.add(schema);
			// System.out.println(schema);
		}
	}

	private void parseTableList(Node node) {
		if (node == null)
			return;

		List<String> itemList = new ArrayList<String>();
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() != 1) {
				continue;
			}

			String schema = child.getAttributes().getNamedItem("schema")
					.getNodeValue();
			// System.out.println(schema);
			String tableName = child.getAttributes().getNamedItem("tableName")
					.getNodeValue();
			// System.out.println(tableName);
			TableConfiguration table = new TableConfiguration();
			table.setSchema(schema);
			table.setTableName(tableName);

			String item = schema.trim().toUpperCase() + "_"
					+ tableName.trim().toUpperCase();
			if (!itemList.contains(item)) {
				tableList.add(table);
				itemList.add(item);
			}
		}
	}

    /**
     * 解析表
     * @param tables
     */
    private void parseTableList(String tables) {

        List<String> itemList = new ArrayList<String>();
        String[] tableArr = tables.split(",");
        for (int i = 0; i < tableArr.length; i++) {
            String item = "_"
                    + tableArr[i].trim().toUpperCase();
            TableConfiguration table = new TableConfiguration();
            table.setSchema("");
            table.setTableName(tableArr[i]);
            if (!itemList.contains(tableArr[i])) {
                tableList.add(table);
                itemList.add(item);
            }
        }
    }

	private void parseGenerator(Node node) {
		if (node == null)
			return;

		String packageName = node.getAttributes().getNamedItem("packageName")
				.getNodeValue();
		// System.out.println(packageName);
		String targetPath = node.getAttributes().getNamedItem("targetPath")
				.getNodeValue();
		String extendPackage = node.getAttributes().getNamedItem("extendPackage")
				.getNodeValue();
		// System.out.println(targetPath);
		propertyMap.put("packageName", packageName);
		propertyMap.put("targetPath", targetPath);
		propertyMap.put("extendPackage", extendPackage);
	}

	private void parseJdbcConnection(Node node) {
		if (node == null)
			return;

		String driverClass = node.getAttributes().getNamedItem("driverClass")
				.getNodeValue();
		// System.out.println(driverClass);
		String jdbcConnectString = node.getAttributes()
				.getNamedItem("connectString").getNodeValue();
		// System.out.println(jdbcConnectString);
		String jdbcUserName = node.getAttributes().getNamedItem("userId")
				.getNodeValue();
		// System.out.println(jdbcUserName);
		String jdbcPassword = node.getAttributes().getNamedItem("password")
				.getNodeValue();
		// System.out.println(jdbcPassword);
		propertyMap.put("jdbcDriver", driverClass);
		propertyMap.put("jdbcConnectString", jdbcConnectString);
		propertyMap.put("jdbcUserName", jdbcUserName);
		propertyMap.put("jdbcPassword", jdbcPassword);
	}

	public List<String> getSchemaList() {
		return schemaList;
	}

	public List<TableConfiguration> getTableList() {
		return tableList;
	}

	public void completeTableList(File file, Map<String, List<String>> map) {
		try {
			System.out.println("file :" + file);
			OutputStream output = new FileOutputStream(file);
			String stemp = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r";
			output.write(stemp.getBytes());
			stemp = "<ibatorConfiguration>";
			output.write(stemp.getBytes());

			String driverClass = (String) propertyMap.get("jdbcDriver");
			String jdbcConnectString = (String) propertyMap
					.get("jdbcConnectString");
			String jdbcUserName = (String) propertyMap.get("jdbcUserName");
			String jdbcPassword = (String) propertyMap.get("jdbcPassword");

			stemp = "<jdbcConnection driverClass=\"" + driverClass + "\" \r";
			stemp = stemp + "connectString=\"" + jdbcConnectString + "\" \r";
			stemp = stemp + "userId=\"" + jdbcUserName + "\" \r";
			stemp = stemp + "password=\"" + jdbcPassword + "\"/>\r";
			output.write(stemp.getBytes());

			String packageName = (String) propertyMap.get("packageName");
			String targetPath = (String) propertyMap.get("targetPath");
			stemp = "<generator packageName=\"" + packageName
					+ "\" targetPath=\"" + targetPath + "\" /> \r";
			output.write(stemp.getBytes());

			stemp = "<interceptorList>\r";
			output.write(stemp.getBytes());
			for (String interceptor : interceptorString) {
				stemp = interceptor + "\r";
				output.write(stemp.getBytes());
			}
			stemp = "</interceptorList>\r";
			output.write(stemp.getBytes());

			stemp = "<schemaList>\r";
			output.write(stemp.getBytes());
			for (String schema : schemaList) {
				stemp = "<schema schema=\"" + schema + "\" />\r";
				output.write(stemp.getBytes());
			}
			stemp = "</schemaList>\r";
			output.write(stemp.getBytes());

			stemp = "<tableList>\r";
			output.write(stemp.getBytes());

			List<String> itemList = new ArrayList<String>();
			for (TableConfiguration table : tableList) {
				String item = table.getSchema().trim().toUpperCase() + "_"
						+ table.getTableName().trim().toUpperCase();
				if (!itemList.contains(item)) {
					stemp = "<table schema=\"" + table.getSchema()
							+ "\" tableName=\"" + table.getTableName()
							+ "\" />\r";
					output.write(stemp.getBytes());
					itemList.add(item);
				}
			}
			for (String schema : schemaList) {
				List<String> tableNameList = map.get(schema);
				for (String tableName : tableNameList) {
					TableConfiguration table = new TableConfiguration();
					table.setSchema(schema);
					table.setTableName(tableName);
					String item = table.getSchema().trim().toUpperCase() + "_"
							+ table.getTableName().trim().toUpperCase();
					if (!itemList.contains(item)) {
						stemp = "<table schema=\"" + schema + "\" tableName=\""
								+ tableName + "\" />\r";
						output.write(stemp.getBytes());
						tableList.add(table);
						itemList.add(item);
					} else {
						System.out.println("table repeat,schema=" + schema
								+ ",tableName=" + tableName);
					}
				}
			}
			stemp = "</tableList>\r";
			output.write(stemp.getBytes());

			stemp = "</ibatorConfiguration>";
			output.write(stemp.getBytes());
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;
	}

	public Object getParam(String name) {
		return propertyMap.get(name);
	}

/*
	public TaskInterceptor getInterceptor(String className) {
		return interceptorMap.get(className);
	}
*/

	public static void main(String[] argv) {
		System.out.println("++++++++++++++++++++");
		try {
            DBConfig config = new DBConfig();
			File file = new File("resources/code-maven-plugin-config.xml");
			List<String> warnings = new ArrayList<String>();

			config.parseConfig(warnings);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
