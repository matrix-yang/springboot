package com.yang.util;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
public class XmlManager {

    /**
     * 解析xml 一层并列
     */

    public static Object parserXml(InputStream in, Class<?> classType) {
        Object object = null;
        try {
            object = Class.forName(classType.getName()).newInstance();
            Document document = new SAXReader().read(in);
            System.out.println("接收的结果" + document.asXML());
            Element users = document.getRootElement();
            Field[] declaredFields = classType.getDeclaredFields();
            List<String> list = new ArrayList<>();
            for (Field field : declaredFields)
                list.add(field.getName());
            for (Iterator i = users.elementIterator(); i.hasNext(); ) {
                Element user = (Element) i.next();
                String name = user.getName();
                String firstLetter = name.substring(0, 1).toUpperCase();
                String fieldName = name.substring(0, 1).toLowerCase() + name.substring(1);
                String setMethodName = "set" + firstLetter + name.substring(1);
                if (list.contains(fieldName)) {
                    Field field = classType.getDeclaredField(fieldName);
                    Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()});//注意set方法需要传入参数类型
                    setMethod.invoke(object, new Object[]{user.getText()});
                }
            }
            return object;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static String buildXml(Object object) {
        try {
            //DocumentHelper提供了创建Document对象的方法
            Document document = DocumentHelper.createDocument();
            //添加节点信息
            Element rootElement = document.addElement("xml");
            //这里可以继续添加子节点，也可以指定内容
            Class<?> classType = object.getClass();
            //获得对象的所有成员变量
            Field[] fields = classType.getDeclaredFields();
            for (Field field : fields) {
                //获取成员变量的名字
                String name = field.getName();    //获取成员变量的名字，此处为id，name,ag
                //获取get和set方法的名字
                String firstLetter = name.substring(0, 1).toUpperCase();    //将属性的首字母转换为大写
                String getMethodName = "get" + firstLetter + name.substring(1);
                try {
                    Method getMethod = classType.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(object, new Object[]{});
                    //request
                    if (value != null) {
                        String nodeName = getMethodName.substring(3);
                        if ("MediaId".equals(nodeName)) {
                            Element nElement = rootElement.addElement("Image");
                            Element nameElement = nElement.addElement(getMethodName.substring(3));
                            nameElement.setText(value.toString());
                        }
                        Element nameElement = rootElement.addElement(getMethodName.substring(3));
                        nameElement.setText(value.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("返回的结果" + document.asXML());
            return document.asXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}