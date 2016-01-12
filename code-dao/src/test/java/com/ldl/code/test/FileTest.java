package com.ldl.code.test;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 2013159
 * Date: 14-5-5
 * Time: 下午12:06
 * To change this template use File | Settings | File Templates.
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        File file = new File("/tmp/");
        System.out.println(file.getCanonicalPath());
        System.out.println(file.getPath());
    }
}
