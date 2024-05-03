package org.example;

public class Main {
    public static void main(String[] args) {
        Class<String> cls1 = String.class;
        System.out.println(cls1);
        String s = "Hello";
        Class<? extends String> cls2 = s.getClass();
        // instanceof不但匹配指定类型，还匹配指定类型的子类。而用==判断class实例可以精确地判断数据类型，但不能作子类型比较。
        System.out.println(cls2 == cls1); // true
        System.out.println("Class name: " + cls2.getName());
        System.out.println("Simple name: " + cls2.getSimpleName());
        if (cls2.getPackage() != null) {
            System.out.println("Package name: " + cls2.getPackage().getName());
        }
        System.out.println("is interface: " + cls2.isInterface());
        System.out.println("is enum: " + cls2.isEnum());
        System.out.println("is array: " + cls2.isArray());
        System.out.println("is primitive: " + cls2.isPrimitive());

        // jvm 中的 class 是动态加载的，当需要 DynamicLoading 类时，才会加载，所以就可以在运行时，控制加载哪一个类
        DynamicLoading dynamicLoading = new DynamicLoading("小胡", 12);
        System.out.println(dynamicLoading);
    }
}


class DynamicLoading {
    String name;
    int age;
    public DynamicLoading(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Override
    public String toString() {
        return "姓名：" + this.name + " 年龄： " + this.age;
    }
}



// class是由JVM在执行过程中动态加载的。JVM在第一次读取到一种class类型时，将其加载进内存。
// 每加载一种class，JVM就为其创建一个Class类型的实例，并关联起来。注意：这里的Class类型是一个名叫Class的
//public final class Class {
//    // Class 的构造方法是一个 private ，只有 jvm 内部可以创建，我们写的 java 代码无法创建
//    private Class() {}
//}
// 例如加载 一个 String 类时，jvm 会先将 String.class 文件读取到内存，然后为 String 创建一个 Class 实例关联起来
// Class cls = new Class(String);
// 这个 Class 实例上将会包含该类的所有信息，我们可以通过该 Class 实例获取类的信息。 这种方式就是反射
