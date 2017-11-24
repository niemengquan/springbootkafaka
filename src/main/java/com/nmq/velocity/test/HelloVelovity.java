package com.nmq.velocity.test;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 使用 Velocity 模板引擎快速生成代码：Helloworld
 * Created by niemengquan on 2017/11/24.
 */
public class HelloVelovity {
    public static void main(String[] args) {
        VelocityEngine ve=new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER,"classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Template t = ve.getTemplate("velocity_template"+ File.separator+"hellovelocity.vm","UTF-8");

        //填充数据
        VelocityContext ctx=new VelocityContext();
        ctx.put("name","Hello Velocity");
        ctx.put("date",new SimpleDateFormat("yyyy年-MM月-dd日 HH时:mm分:ss秒").format(new Date()));

        List temp=new ArrayList();
        temp.add("1");
        temp.add("2");
        ctx.put("list",temp);
        //模板天剑java对象
        Person person=new Person();
        person.setName("velocity");
        person.setAge(20);
        person.setSex("男");
        ctx.put("person",person);

        //输出流对象
        StringWriter sw=new StringWriter();
        t.merge(ctx,sw);
        System.out.println(sw.toString());
    }
}
