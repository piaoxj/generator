package com.ldl.code.test;

import java.io.File;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import com.ldl.code.mojo.GeneratorMojo;

public class MojoTest extends AbstractMojoTestCase{
    protected void setUp() throws Exception {
        super.setUp();

    }
    public void testGeneratorMojo() throws Exception {
    	
    	File testPom = new File( getBasedir(),"src/test/resources/plugin-test.xml");
    	GeneratorMojo mojo = (GeneratorMojo) lookupMojo ("generate", testPom );
    	mojo.execute();
    }

}
