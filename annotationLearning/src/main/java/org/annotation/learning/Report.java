package org.annotation.learning;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用@interface语法来定义注解
 * <p>
 * 元注解 用于修饰注解 @Target @Retention @Repeatable
 * Target  定义 Annotation 能够被应用于源码的哪些位置
 * 类或接口：ElementType.TYPE；
 * 字段：ElementType.FIELD；
 * 方法：ElementType.METHOD；
 * 构造方法：ElementType.CONSTRUCTOR；
 * 方法参数：ElementType.PARAMETER。
 * <p>
 * Retention 定义 Annotation 的生命周期
 * 仅编译期：RetentionPolicy.SOURCE； SOURCE 类型的注解在编译期就被丢掉了
 * 仅class文件：RetentionPolicy.CLASS；CLASS 类型的注解仅仅保存在 class 文件中，不会加载入 JVM 中
 * 运行期：RetentionPolicy.RUNTIME。 RUNTIME 类型的注解会被加载进JVM，并且在运行期可以被程序读取
 * <p>
 * Repeatable 定义 Annotation 是否可重复
 * 修饰后，一处可以使用多次该注解
 * <p>
 * Inherited 定义子类是否可以 继承父类的注解
 * Inherited 仅针对 @Target(ElementType.TYPE) 类型的 annotation 有效，并且仅针对 class 的继承，对 interface 的继承无效
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Report {
    /**
     * default 用于设置默认值，推荐所有字段都设置默认值
     * 注解定义时 必须设置 @Target 和 @Retention 两个元注解
     */
    int type() default 0;
    String name() default "标题";
    String description() default "注解学习";
    String author() default "yi.hu";
    String date() default "2024-05-19";
    String value() default "这是第一个注解";
}

