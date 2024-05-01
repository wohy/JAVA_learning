import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

// JAVA 中的异常处理
public class Main {
    public static void main(String[] args) {
        // 异常的根为 Throwable ，它的子类包括了两种异常：error 和 exception，其中 error 是严重错误，程序无法处理。exception 表示程序运行时错误，他可以通过程序捕获并处理
        // error 包括例如： OutOfMemoryError 栈溢出/ NoClassDefFoundError 无法加载某个class/OutOfMemoryError 内存耗尽 的错误
        // exception 包括如：NumberFormatException 数据格式错误 / FileNotFoundException 为找到文件 / SocketException 读取网络失败 / 常见的 NullPointerException 空指针（对某个null的对象调用方法或字段）/ IndexOutOfBoundsException 数组越界

        Person p = new Person();
        // 会精确的给出 null 对象是谁
        // Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.toLowerCase()" because "p.address.city" is null
        // System.out.println(p.address.city.toLowerCase());

        try {
            System.out.println(p.address.city.toLowerCase());
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Cannot invoke "String.toLowerCase()" because "p.address.city" is null
        }

        // 当 catch 中抛出异常时，会先执行 finally 再执行抛出异常
        // 当 catch 和 finally 中都抛出异常时，会先抛出 finally，之后 catch 会屏蔽掉（被屏蔽掉的异常，称为 Suppressed Exception）
        try {
            Integer.parseInt("abc");
        } catch (Exception e) {
            System.out.println("catched");
            // 当 catch 中抛出异常时，会先执行 finally 再执行抛出异常
            // throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
        }

        try {
            int a = -1;
            assert a > 0; // 断言 a 一定大于 0，断言失败，会抛出AssertionError。
            // 这是因为 JVM 默认关闭断言指令，即遇到 assert 语句就自动忽略了，不执行。
            //要执行 assert 语句，必须给Java虚拟机传递 -enableassertions（可简写为-ea）参数启用断言 java -ea Main.java
            System.out.println(a);
        } catch (Exception e) {
            System.out.println(e.getMessage()); //Exception in thread "main" java.lang.AssertionError
        }

        // logger 使用记录日志 替代 System.out 可以精确记录日志时间
        Logger logger = Logger.getLogger(Main.class.getName());
        // 5月 01, 2024 5:30:25 下午 Main main
        // 严重: invalidCharsetName
        logger.info("Start process...");
        try {
            "".getBytes("invalidCharsetName");
        } catch (UnsupportedEncodingException e) {
            // 5月 01, 2024 5:30:25 下午 Main main
            // 严重: invalidCharsetName
            logger.severe(e.getMessage());
        }
        logger.info("Process end.");
    }
}

class Person {
    String[] name = new String[2];
    Address address = new Address();
}

class Address {
    String city;
    String street;
    String zipcode;
}