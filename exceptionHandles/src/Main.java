// JAVA 中的异常处理

public class Main {
    public static void main(String[] args) {
        // 异常的根为 Throwable ，子包括了两种异常：error 和 exception，其中 error 是严重错误，程序无法处理。exception 表示程序运行时错误，他可以通过程序捕获并处理
        // error 包括例如： 栈溢出/无法加载某个class/内存耗尽 的错误
        System.out.println("Hello world!");
    }
}