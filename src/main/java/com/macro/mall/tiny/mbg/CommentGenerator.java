package com.macro.mall.tiny.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * 自定义CommentGenerator实现中文注释
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;
    private static final String EXAMPLE_SUFFIX = "Example";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";

    /**
     * 设置用户配置的参数
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
//        读取配置文件中的addRemarkComments，判断是否要加上注释信息
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }


    /**
     * 给字段添加注释信息
     *
     * @param field              java类中的字段
     * @param introspectedTable  整个表结构
     * @param introspectedColumn 对应表中的字段
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
//        获取数据库中该字段的comment信息
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            //数据库中特殊字符需要转义
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'");
            }
            //给model的字段添加swagger注解
            //@ApiModelProperty 用于修饰实体类的属性，当实体类是请求参数或返回结果时，直接生成相关文档信息
            field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
        }
    }


    /**
     * 给model的字段添加注释，这个函数是自定义的，没有用上
     */
    private void addFieldJavaDoc(Field field, String remarks) {
        //文档注释开始
        field.addJavaDocLine("/**");
        //获取数据库字段的备注信息
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for (String remarkLine : remarkLines) {
            field.addJavaDocLine(" * " + remarkLine);
        }
        addJavadocTag(field, false);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        //只在model中添加swagger注解类的导入
        //不是接口类，并且全类名不包含Example，则在其类中导入io.swagger.annotations.ApiModelProperty
        if (!compilationUnit.isJavaInterface() && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }
    }
}
