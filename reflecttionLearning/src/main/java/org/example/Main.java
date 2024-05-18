package org.example;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<String> cls1 = String.class;
        System.out.println(cls1); // class java.lang.String
        String s = "Hello";
        Class<? extends String> cls2 = s.getClass();
        // instanceof不但匹配指定类型，还匹配指定类型的子类。而用==判断class实例可以精确地判断数据类型，但不能作子类型比较。
        System.out.println(cls2 == cls1); // true
        System.out.println("Class name: " + cls2.getName()); // java.lang.String
        System.out.println("Simple name: " + cls2.getSimpleName()); // String
        if (cls2.getPackage() != null) {
            System.out.println("Package name: " + cls2.getPackage().getName()); // java.lang
        }
        System.out.println("is interface: " + cls2.isInterface()); // false
        System.out.println("is enum: " + cls2.isEnum()); // false
        System.out.println("is array: " + cls2.isArray()); // false
        System.out.println("is primitive: " + cls2.isPrimitive()); // false

        // jvm 中的 class 是动态加载的，当需要 DynamicLoading 类时，才会加载，所以就可以在运行时，控制加载哪一个类
        DynamicLoading dynamicLoading = new DynamicLoading("小胡", 12);
        System.out.println(dynamicLoading);

        // 使用反射获取类上的字段
        Class<? extends DynamicLoading> DynamicLoadingClass = dynamicLoading.getClass();
        try {
            // 获取 public 字段 包括父类上的字段
            System.out.println(DynamicLoadingClass.getField("name"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        try {
            // 获取 private 字段 包括父类上的字段
            System.out.println(DynamicLoadingClass.getDeclaredField("age"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        // 获得所有 public 字段 包括父类上的字段
        System.out.println(Arrays.toString(DynamicLoadingClass.getFields()));
        // 获得所有 private 字段 不包括父类上的字段
        System.out.println(Arrays.toString(DynamicLoadingClass.getDeclaredFields()));


        Object personTwo = new DynamicLoading("小明", 18);
        Field[] fields = DynamicLoadingClass.getDeclaredFields();
        // 获取字段的值
        for (Field field : fields) {
            System.out.println("字段名称：" + field.getName() + " 字段类型：" + field.getType() + " 字段修饰符：" + field.getModifiers());
            int modifiers = field.getModifiers();
            if (Modifier.isPublic(modifiers)) { // 是 public 修饰字段
                // 直接获取该值
                Object value = field.get(personTwo);
                System.out.println(value); // 小明
            }
            if (Modifier.isPrivate(modifiers)) { // 是 private 修饰字段
                // 将该字段改为允许方法 日常一般不使用，如果不设置 直接获取 private 修饰的字段值，将抛出 IllegalAccessException 错误
                // SecurityManager 可能会阻止 setAccessible(true)
                field.setAccessible(true);
                Object value = field.get(personTwo);
                System.out.println(value); // 18
            }
        }

        // 设置字段的值 正常 直接 set 就好，这里只是学习反射 使用反射的方式 set
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            String type = field.getType().getSimpleName();
            String name = field.getName();
            System.out.println("修改前:\n字段名称：" + name + " 字段类型：" + type + " 字段修饰符：" + modifiers);
            getValue(field, modifiers, personTwo);

            if (name.equals("name")) {
                setValue(field, modifiers, personTwo, "小红");
            }
            if (name.equals("age")) {
                setValue(field, modifiers, personTwo, 25);
            }
            System.out.println("修改后:\n字段名称：" + name + " 字段类型：" + type + " 字段修饰符：" + modifiers);
            getValue(field, modifiers, personTwo);

        }
        System.out.println(personTwo); // 姓名：小红 年龄： 25


        // 获取方法并调用
        String a = "hello world";
        Class<String> sclass = String.class;
        Method methodOne = getMethod(sclass, "substring", int.class); // 获取含有一个 int 类型形参的 substring 方法
        // 如果是调用静态方法时 第一个参数需要传 null
        // 如果获取的是 private 方法，需要先执行 methodOne.setAccessible(true); 再执行方法
        String b = (String)methodOne.invoke(a, 6);
        System.out.println(b); // world


        // 获取构造函数并调用
        Constructor<?> personConstructor = getContract(personTwo.getClass(), String.class, String.class, int.class);
        DynamicLoading personThree = (DynamicLoading)personConstructor.newInstance("小芳", "上海", 19);
        System.out.println(personThree.getLocation()); // 上海


        // 实现动态代理
        ProxyHandler handler = new ProxyHandler();

        // 创建代理对象，使用接口 Hello 类型来引用
        Hello proxyHello = (Hello) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class<?>[]{Hello.class, Hi.class}, // 代理实现 Hello 和 Hi 接口
                handler);
        Hi proxyHi = (Hi) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class<?>[]{Hello.class, Hi.class}, // 代理实现 Hello 和 Hi 接口
                handler);

        // 通过代理调用方法，使用接口 Hello 和 Hi 引用
        proxyHello.sayHello("小胡", "morning");  //这次代理了是 sayHello 方法    Hello, 小胡, Good Morning
        proxyHi.sayHi("小胡", "afternoon"); // 这次代理了是 sayHi 方法    Hi, 小胡, Good Afternoon
    }

    // 取字段值
    public static void getValue(Field field, int modifiers, Object personTwo) throws IllegalAccessException {
        if (Modifier.isPublic(modifiers)) {
            Object value = field.get(personTwo);
            System.out.println("字段值为：" + value);
        }
        if (Modifier.isPrivate(modifiers)) {
            field.setAccessible(true);
            Object value = field.get(personTwo);
            System.out.println("字段值为：" + value);
        }
    }
    // 设置字段值
    public static void setValue(Field field, int modifiers, Object personTwo, Object value) throws IllegalAccessException {
        if (Modifier.isPublic(modifiers)) {
            field.set(personTwo, value);
        }
        if (Modifier.isPrivate(modifiers)) {
            field.setAccessible(true);
            field.set(personTwo, value);
        }
    }

    // 获取方法 并调用
    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    public static Method getMethod(@NotNull Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        // Method getMethod(name, Class...)：获取某个public的Method（包括父类）
        // Method getDeclaredMethod(name, Class...)：获取当前类的某个Method（不包括父类）
        // Method[] getMethods()：获取所有public的Method（包括父类）
        // Method[] getDeclaredMethods()：获取当前类的所有Method（不包括父类）
        return clazz.getMethod(methodName, parameterTypes);
    }

    // 获取构造函数
    @Contract(pure = true)
    public static @NotNull Constructor<?> getContract(@NotNull Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getConstructor(parameterTypes);
    }

    // 通过Class对象可以获取继承关系：
    // Class getSuperclass()：获取父类类型；
    // Class[] getInterfaces()：获取当前类实现的所有接口。
    // 通过Class对象的isAssignableFrom()方法可以判断一个向上转型是否可以实现。
}

class DynamicLoading {
    public String name;
    public String location;
    private int age;
    public DynamicLoading(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public  DynamicLoading(String name, String location, int age) {
        this.name = name;
        this.location = location;
        this.age = age;
    }
    private void getName() {
        System.out.println(this.name);
    }
    private void getAge() {
        System.out.println(this.age);
    }
    public String getLocation() {
        return this.location;
    }
    @Override
    public String toString() {
        return "姓名：" + this.name + " 年龄： " + this.age;
    }
}

// 接口定义
interface Hello {
    void sayHello(String name, String time);
}

interface Hi {
    void sayHi(String name, String time);
}

// 代理处理器
class ProxyHandler implements InvocationHandler {
    private String capitalize(String time) {
        if (time == null || time.isEmpty()) return time;
        return time.substring(0, 1).toUpperCase() + time.substring(1).toLowerCase();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在这里可以添加额外的逻辑
        if (method.getName().equals("sayHello")) {
            System.out.println("这次代理了是 sayHello 方法");
            System.out.println("Hello, " + args[0] + ", Good " + capitalize(String.valueOf(args[1])));
        }
        if (method.getName().equals("sayHi")) {
            System.out.println("这次代理了是 sayHi 方法");
            System.out.println("Hi, " + args[0] + ", Good " + capitalize(String.valueOf(args[1])));
        }
        return null;
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

/**
 * 使用反射可实现：
 * 1. 获取类上的各个字段，字段的 字段名称、字段类型、字段修饰符 属性，以及 字段对应的值
 * 2. 获取类上的方法，并通过 invoke 调用
 * 3. 获取类的构造函数，并通过 newInstance 创建新的实例
 * 4. 通过Class对象可以获取继承关系 Class getSuperclass() 获取父类类型；
 *    Class[] getInterfaces()：获取当前类实现的所有接口；
 *    通过Class对象的isAssignableFrom()方法可以判断一个向上转型是否可以实现。
 *
 * 还有一种与反射相关的一种功能实现：动态代理， 即实现 允许在运行期动态创建一个接口的实例
 */