package com.yang.util;

import com.yang.reciver.annotation.CName;
import com.yang.reciver.model.Project;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TableUtil {
    public static String buildMenuFromList(String head, String rex, List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(head+"\n");
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(rex + i + "," + list.get(i) + "\n");
        }
        return stringBuffer.toString();
    }

    public static String buildMenuFromBean(String head, Project project) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuffer result=new StringBuffer();
        result.append(head+"\n");
        Class clazz=project.getClass();
        Field field[] = clazz.getDeclaredFields(); // 取得全部属性
        for (int x = 0; x < field.length; x++) {
            // 获取属性的名字
            String name = field[x].getName();
            // 将属性的首字符大写，方便构造get，set方法
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            // 获取属性的类型
            String type = field[x].getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名
            //System.out.println("属性为：" + name+"的类型是"+type);
            Method m = project.getClass().getMethod("get" + name);
            // 调用getter方法获取属性值
            String value = (String) m.invoke(project);
            String desc ="";
            boolean hasCname = field[x].isAnnotationPresent(CName.class);
            if (hasCname){
                CName cName=field[x].getAnnotation(CName.class);
                desc=cName.value();
            }
            result.append(desc+"："+value+"\n");
        }
        return result.toString();
    }
}
