public class FirstClass {
    @SuppressWarnings("LanguageDetectionInspection")
    public static void main(String[] args) {
        System.out.println("Hello World");
        String a = "1123";
        char b = a.charAt(0);
        char c = 'c';
        double d = 1.1;
        // double 强转 float
        float e = (float) d;
        // byte 存储整数的最小类型，只占一个字节，只能表示 【-128 ～ 127】 范围内的数，超出需要强转
        byte f = 127;
        /*
        * 为什么会出现这样的结果呢？
        * 129 使用二进制表示就是 10000001，但因为所有的数值类型都是使用补码来表示，在强制转换之后，第一位会被认为是符号位，表示负数。
        * 负数补码的反码是除符号位以外取反，所以是：11111110。
        * 负数的原码是反码加1，所以是 11111111，也就是 -127。
        * */
        byte f1 = (byte) 129; // -127
        short g = 123;
        float h = 123.456f;

        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
        System.out.println(e);
        System.out.println(f);
        System.out.println(g);
        System.out.println(h);
        System.out.println(f1);


        short i = 5;
        i = (short)(i + 2);
        System.out.println(i);

    }
}
