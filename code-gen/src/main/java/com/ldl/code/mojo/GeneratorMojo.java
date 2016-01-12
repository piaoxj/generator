package com.ldl.code.mojo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ldl.code.db.DBConfig;
import com.ldl.code.db.DBParser;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import com.ldl.code.freemarker.FreeMarkerConfig;
import com.ldl.code.pdm.Table;
import com.ldl.code.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @goal generate
 * @author nandi.ldl
 *
 */
public class GeneratorMojo extends AbstractMojo{

    /**
     * @parameter expression="${templateDirectory}" default-value="${user.home}/codeTemplate"
     * @required
     */
    private File templateDirectory;

    /**
     * @parameter expression="${outputDirectory}" default-value="${basedir}"
     * @required
     */
    private File outputDirectory;

    /**
     * @parameter expression="${channel}" default-value=""
     */
    private String channel;
    /**
     * @parameter expression="${channelClass}" default-value=""
     * @required
     */
    private String channelClass;


    /**
     * @parameter expression="${type}" default-value=""
     */
    private String type;
    /**
     * @parameter expression="${typeClass}" default-value=""
     * @required
     */
    private String typeClass;



    /**
     * @parameter expression="${tablesName}" default-value=""
     */
    private String tablesName;




    private Configuration configuration;

    private List<Table> tableList;
//    private String entity;
    //private String tables;

    protected final String PLACEHOLDER_CHANNEL = "${channel}";
    protected final String PLACEHOLDER_CHANNEL_CLASS = "${channelClass}";
    protected final String PLACEHOLDER_TYPE = "${type}";
    protected final String PLACEHOLDER_TYPE_CLASS = "${typeClass}";

    protected final String PLACEHOLDER_ITERATOR = "${iterator}";
//    protected final String PLACEHOLDER_CAPTABLENAME = "${TableName}";
//    protected final String PLACEHOLDER_UNCAPTABLENAME = "${tableName}";
//    protected final String PLACEHOLDER_ENTITY = "${entity}";

    private final String ENCODING_GB18030 = "utf-8";

    private static final String CONFIG_PATH="code-maven-plugin-config.xml";

    public void execute()throws MojoExecutionException{
        //todo
        //必须输入
        //templateDirectory = new File("D:\\tmp\\code-maven-plugin\\mytemplate-admin");
        //outputDirectory = new File("D:\\tmp\\code-maven-plugin\\output");
        //channel = "auth";
        //tablesName = "auth_resource";
//        String[] value = tablesName.split("_");
//        StringBuffer stringBuffer = new StringBuffer();
//        for(String v:value){
//            stringBuffer.append(StringUtils.capitalize(v));
//        }
//        entity = stringBuffer.toString();
        //参数校验
        try {

            //模板目录不能为空
            if(!templateDirectory.exists()){
                throw new MojoExecutionException("ERROR:templateDirectory:" + templateDirectory.getCanonicalPath() + " not exist。");
            }else if(!templateDirectory.isDirectory()){
                throw new MojoExecutionException("ERROR:templateDirectory:" + templateDirectory.getCanonicalPath() + "is not folder。");
            }else{
                getLog().info("templateDirectory:" + templateDirectory.getCanonicalPath());
            }
            //导出目录不能为空
            if(!outputDirectory.exists()){
                outputDirectory.mkdirs();
                getLog().info("outputDirectory:" + outputDirectory.getCanonicalPath());
            }else if(!outputDirectory.isDirectory()){
                throw new MojoExecutionException("ERROR:outputDirectory:" + outputDirectory.getCanonicalPath() + "is not folder。");
            }else{
                getLog().info("outputDirectory:" + outputDirectory.getCanonicalPath());
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new MojoExecutionException("ERROR:folder not exist。");
        }


        try {
            //首先读取模板文件下的配置文件
            String configPath = templateDirectory.getPath()+File.separator+CONFIG_PATH;
            getLog().info("read configfilepath:["+configPath+"]");
//            DBConfig config = new DBConfig(tablesName.toLowerCase(),configPath);
//            config.parseConfig(new ArrayList<String>());
//            tableList = DBParser.parse(config);
        } catch (Exception e) {
            throw new MojoExecutionException("parse db struct error",e);
        }
        if(channel == null){
            channel = "";
        }
        if(channelClass == null){
            channelClass = "";
        }
        if(type == null){
            type = "";
        }
        if(typeClass == null){
            typeClass = "";
        }

        configuration = FreeMarkerConfig.getInstance().getConfiguration(templateDirectory);
        generateCode();
    }
    /**
     * 生成代码的具体实现方法
     * @throws MojoExecutionException
     */
    public void generateCode() throws MojoExecutionException{
        recursionFile(templateDirectory);
    }

    /**
     *递归遍历模板生成代码文件
     * @param file
     * @throws MojoExecutionException
     */
    @SuppressWarnings("unchecked")
    private void recursionFile(File file) throws MojoExecutionException{
        if(!file.exists() || file.isHidden())return;
        String relativePath;
        File outputFile = null;
        try {
            //相对路径（相对模板目录）
            relativePath = file.getCanonicalPath().substring(templateDirectory.getCanonicalPath().length());
            //路径上的替换都替换为小写
            String outputPath = outputDirectory.getCanonicalPath()
                    + relativePath.replace(File.separator + PLACEHOLDER_CHANNEL, !"".equals(channel) ? File.separator + channel : channel)
                    .replace(PLACEHOLDER_CHANNEL, channel.toLowerCase())
                    .replace(File.separator + PLACEHOLDER_CHANNEL_CLASS, !"".equals(channelClass) ? File.separator + channelClass : channelClass)
                    .replace(PLACEHOLDER_CHANNEL_CLASS, channelClass.toUpperCase())
                    .replace(File.separator + PLACEHOLDER_TYPE, !"".equals(type) ? File.separator + type : type)
                    .replace(PLACEHOLDER_TYPE, type.toUpperCase())
                    .replace(File.separator + PLACEHOLDER_TYPE_CLASS, !"".equals(typeClass) ? File.separator + typeClass : typeClass)
                    .replace(PLACEHOLDER_TYPE_CLASS, typeClass.toUpperCase());
//                    .replace(PLACEHOLDER_ENTITY,entity)
//                    .replace(File.separator + "${entity_lower_case}", !"".equals(entity)?File.separator + entity.toLowerCase():entity.toLowerCase())
//                    .replace("${entity_lower_case}",entity.toLowerCase());
            getLog().info("relativePath:"+relativePath+"-----> outputPath:"+outputPath);
            //getLog().info("the path can support replace ${channel} ${entity}");
            outputFile = new File(outputPath);
        } catch (IOException e) {
            throw new MojoExecutionException("template file not exist", e);
        }

        //如果是目录，递归遍历子文件生成
        if(file.isDirectory()){
            File []childFiles = file.listFiles();
            if(childFiles.length <= 0){
                if(!outputFile.exists()){
                    outputFile.mkdirs();
                }
            }
            for (int i = 0; i < childFiles.length; i++) {
                recursionFile(childFiles[i]);
            }
        //如果是文件
        }else{
            if(file.getName().endsWith(CONFIG_PATH)){
                return;
            }
            if(!outputFile.getParentFile().exists() &&
                    !outputFile.getParentFile().getName().startsWith(PLACEHOLDER_ITERATOR)){
                outputFile.getParentFile().mkdirs();
            }

            try {
                //是否属于迭代生成文件:以${iterator}开头
                boolean isStartWithPlaceHolder = outputFile.getName().startsWith(PLACEHOLDER_ITERATOR);
                if(!outputFile.exists() && !isStartWithPlaceHolder){
                    outputFile.createNewFile();
                }
                Map root = new HashMap();
                root.put("channel", channel);
                root.put("channelClass",channelClass);
                root.put("type",type);
                root.put("typeClass",typeClass);
                Template template = configuration.getTemplate(relativePath);
                //如果是迭代文件
                if(isStartWithPlaceHolder){
                    for (Table table : tableList) {
                        root.put("table", table);
                        Writer out = null;
                        String outFileName = outputFile.getName()
//                                .replace(PLACEHOLDER_CAPTABLENAME, table.getTableName())
//                                .replace(PLACEHOLDER_UNCAPTABLENAME, StringUtils.uncapitalize(table.getTableName()))
                                .replace(PLACEHOLDER_ITERATOR, "");
                        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFile.getParentFile(),outFileName)),ENCODING_GB18030));
                        template.process(root, out);
                        out.flush();
                        out.close();
                    }
                //如果不是迭代文件
                }else{
//                    root.put("table",tableList.get(0));
                    Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile),ENCODING_GB18030));
                    template.process(root, out);
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                throw new MojoExecutionException("ERROR:根据模板生成代码出错",e);
            }
        }
    }

    public static void main(String[] args){

    }
}