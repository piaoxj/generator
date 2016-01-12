/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.ldl.code.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class assumes that classes are cached elsewhere for performance reasons,
 * but also to make sure
 * that any native libraries are only loaded one time (avoids the dreaded
 * UnsatisfiedLinkError library loaded in another classloader)
 * 
 * @author Jeff Butler
 */
public class ConnectionFactory {

    private static ConnectionFactory instance = new ConnectionFactory();
	
	public static ConnectionFactory getInstance() {
	    return instance;
	}
	
	/**
	 *  
	 */
	private ConnectionFactory() {
		super();
	}

	public Connection getConnection(DBConfig config)
			throws SQLException {
		Driver driver = getDriver(config);
		Properties props = new Properties();

		if (StringUtility.stringHasValue((String)config.getParam("jdbcUserName"))) {
			props.setProperty("user", (String)config.getParam("jdbcUserName")); //$NON-NLS-1$
		}

		if (StringUtility.stringHasValue((String)config.getParam("jdbcPassword"))) {
			props.setProperty("password", (String)config.getParam("jdbcPassword")); //$NON-NLS-1$
		}

		Connection conn = driver.connect((String)config.getParam("jdbcConnectString"), props);
		if (conn == null) {
			throw new SQLException(Messages.getString("RuntimeError.7")); //$NON-NLS-1$
		}

		return conn;
	}

	private Driver getDriver(DBConfig config) {
		String driverClass = (String)config.getParam("jdbcDriver");
		Driver driver;
        
		try {
			Class<?> clazz = externalClassForName(driverClass);
			driver = (Driver) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(Messages.getString("RuntimeError.8"), e); //$NON-NLS-1$
		}

		return driver;
	}
	
	private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
	
	public static Class<?> externalClassForName(String type) throws ClassNotFoundException {        
        Class<?> clazz;
        try {
            clazz = getClassLoader().loadClass(type);
        } catch (Throwable e) {
            // ignore - fail safe below
            clazz = null;
        }
        
        if (clazz == null) {
            clazz = Class.forName(type);
        }
        
        return clazz;
    }
}
