package com.ldl.code.db;

import com.ldl.code.pdm.Column;
import com.ldl.code.pdm.Table;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 2013159
 * Date: 14-5-2
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public class DBParser {
    public static List<Table> parse(DBConfig dbConfig) throws DocumentException {
        List<Table> list = new ArrayList<Table>();
        if(dbConfig == null){
            return list;
        }
        QueryFieldUtil fieldUtil = new QueryFieldUtil();
        List<Table> tableInfoList = fieldUtil.queryFields(dbConfig);
        return tableInfoList;
    };
}
