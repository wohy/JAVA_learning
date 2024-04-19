public class ImportantClassInJava {
    public static void main(String[] args) {
        /*
         * 核心类
         * https://www.liaoxuefeng.com/wiki/1252599548343744/1260576204194144
         * 字符串/StringBuilder/StringJoiner/包装类型/JavaBean/枚举/常用工具类
         * */


        // String 太常用了，所以 Java提供了 "..." 这种字符串字面量表示方法
        String s1 = "Hello";
        // String 是一个引用类型，它本身也是一个 class 。 实际上字符串在 String 内部是通过一个 char[] 数组来表示的
        String s2 = new String(new  char[] {'H','e','l','l','o'});
        // 比较字符串内容是否相同必须使用 equals 而不能使用 ==
        System.out.println(s1.equals(s2)); // true

        // Java 字符串的一个重要特点是字符串不可变，这种不可变性是通过内部的 private final char[] 字段，以及没有任何修改 char[] 的方法实现的
        // 为啥可变更了？
        String s3 = "Hello";
        System.out.println(s3); // Hello
        s3 = s3.toUpperCase();
        System.out.println(s3); // HELLO
        s3 = "world1";
        System.out.println(s3); // world1
        String s4 = "HELLO";
        System.out.println(s4.equalsIgnoreCase(s1)); // true 忽略大小写的比较


        // 其他方法常用方法
        // contains 是否包含子串;  indexOf 搜索子串索引/lastIndexOf 最后一个相同子串的索引/startsWith/endsWith/subString/trim/split/join/replace/replaceAll 等等和前端相同；
        // stripLeading 方法也可以移除字符串首尾空白字符和trim()不同的是，类似中文的空格字符\u3000也会被移除; isEmpty 字符串长度是否为 0 / isBlank 字符串是否只包含空白字符
        String s5 = "Hi %s, your score is %d!";
        // 占位 %s：显示字符串；%d：显示整数；%x：显示十六进制整数；%f：显示浮点数。 %.2f两位小数浮点数
        System.out.printf((s5) + "%n", "Alice", 80);
        System.out.printf("Hi %s, your score is %.2f!%n", "Bob", 59.5);

        // 类型转换
        // 1.要把任意基本类型或引用类型转换为字符串，可以使用静态方法valueOf()
        System.out.println(String.valueOf(45.67)); // "45.67"
        System.out.println(String.valueOf(true)); // "true"
        System.out.println(String.valueOf(new Object())); // "java.lang.Object@30f39991"
        // 2.把字符串转换为int类型
        int n1 = Integer.parseInt("123"); // 123
        int n2 = Integer.parseInt("ff", 16); // 按十六进制转换，255
        System.out.println(n1);
        System.out.println(n2);
        // 3.把字符串转换为boolean类型
        boolean b1 = Boolean.parseBoolean("true"); // true
        boolean b2 = Boolean.parseBoolean("FALSE"); // false
        System.out.println("b1: " + b1);
        System.out.println("b2: " + b2);
        // 4.把字符串转换为char[]
        char[] cs = "Hello".toCharArray(); // String -> char[]
        System.out.println(cs);
        String s = new String(cs); // char[] -> String
        System.out.println(s);

        String str1 = 3.5f + "";
        System.out.println(str1);
        System.out.println(3+4+"hello!");
        System.out.println("a"+3+4);
        System.out.println('a'+1+"b");
        System.out.println("hello"+'a'+1);
        System.out.println('*' + 1); // 43
        System.out.println("*   *"); // *   *
        System.out.println("*\t*"); // *   *
        System.out.println('*' +  "\t" + "*"); // *   *
        System.out.println('*' +  '\t' + "*"); // 51*
        System.out.println('*' +  "\t" + '*'); // *   *
        System.out.println('*' +  '\t' + '*'); //93



    }
}
