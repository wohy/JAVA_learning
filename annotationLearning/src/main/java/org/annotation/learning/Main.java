/**
 * 注解学习
 * 注解是可以被编译器打包注入到 class 文件中，作为标注的一种“元数据”
 */
package org.annotation.learning;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static boolean isTimeWithinRange(String timeStr, TimeRange timeRange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime givenTime = LocalTime.parse(timeStr, formatter);
        // 定义时间范围的开始和结束
        int maxHour = timeRange.MaxHour();
        int maxMinute = timeRange.MaxMinute();
        int minMinute = timeRange.SmallMinute();
        int minHour = timeRange.SmallHour();
        LocalTime startTime = LocalTime.of(minHour, minMinute);
        LocalTime endTime = LocalTime.of(maxHour, maxMinute);

        // 检查给定时间是否在开始时间和结束时间之间
        return !givenTime.isBefore(startTime) && !givenTime.isAfter(endTime);
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Class<? extends Hello> clazz = Hello.class;
        // 获取类上的所有的注解
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation.annotationType().getName());

            System.out.print("日期");
            getAnnotation(annotation, "date");
        }

        // 获取类上的指定注解
        Report report = clazz.getAnnotation(Report.class);
        System.out.println("作者：" + report.author() + " 日期：" + report.date() + " 描述：" + report.description());

        // 获取类中字段上的注解
        Field time = clazz.getField("time");
        TimeRange timeRange = (TimeRange) time.getAnnotation(TimeRange.class);
        Hello helloOne = new Hello("23:58", "小明");
        Hello helloTwo = new Hello("00:00", "小胡");
        String timeOne = helloOne.getTime();
        String timeTwo = helloTwo.getTime();
        if (!isTimeWithinRange(timeOne, timeRange)) {
            System.out.println(timeOne + " 时间错误");
        }
        if (!isTimeWithinRange(timeTwo, timeRange)) {
            System.out.println(timeTwo + "时间错误");
        }

    }

    /**
     * 获取注解上某个 字段的值
     * @param annotation 注解
     * @param name 字段名
     */
    public static void getAnnotation(Annotation annotation, String name) throws IllegalAccessException, InvocationTargetException {
        Class<? extends Annotation> annotationClass = annotation.getClass();
        Method[] methods = annotationClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                Object value = (Object) method.invoke(annotation);
                System.out.println(value);
            }
        }
    }
}

@Report(type = 1, name = "Hello", date = "1999-08-23")
class Hello {
    // TimeRange注释 可以结合单元测试，直接检测 Hello 的实例，限制时间范围
    @TimeRange(MaxHour = 12)
    public String time;
    public String name;
    public Hello(String time, String name) {
        this.time = time;
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    @Override
    public String toString() {
        return "Hello " + this.name + ", this is " + this.time;
    }
}

