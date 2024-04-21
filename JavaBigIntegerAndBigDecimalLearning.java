import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class JavaBigIntegerAndBigDecimalLearning {
    // BigInteger: https://www.liaoxuefeng.com/wiki/1252599548343744/1279767986831393
    // BigDecimal: https://www.liaoxuefeng.com/wiki/1252599548343744/1279768011997217
    public static void main(String[] args) {
        // cpu 原生提供大最大整型 long 类型是 64 位的，这个时候只能使用软件来模拟一个大整数。
        // java.math.BigInteger 就是用来表示任意大小的整数。BigInteger 内部用一个 int[] 数组来模拟一个非常大的整数
        BigInteger i = new BigInteger("123456789000"); // 123456789000
        // pow 幂运算 pow 99 ，表示 99 次方
        BigInteger n = new BigInteger("999999").pow(99);
        float f = n.floatValue();

        System.out.println(f); // Infinity 无穷大，超出浮点型后
        System.out.println(i.longValue()); // 123456789000
        System.out.println(n.longValue()); // 5920146666018336447 多余位丢失了
        try {
            System.out.println(n.longValueExact()); // 超出 long 类型范围，抛出错误 “java.lang.ArithmeticException: BigInteger out of long range”
        } catch(ArithmeticException e) {
            System.out.println(e.getMessage()); // BigInteger out of long range
        }

        // BigDecimal 实际上是通过一个BigInteger和一个scale来表示的，即BigInteger表示一个完整的整数，而scale表示小数位数
        // 不同的是 BigDecimal 加上了小数的概念。BigDecimal可以表示一个任意大小且精度完全准确的浮点数。一般 BigDecimal 用的比 BigInteger 多一些
        BigDecimal j = new BigDecimal(i, 5); // 1234567.89000
        System.out.println("i: " + i + "; j: " + j);

        BigDecimal k = j.stripTrailingZeros(); // 1234567.89 去除了末尾的 三个0
        System.out.println(j.scale()); // 5
        System.out.println(k.scale()); // 2
        // 如果是整数的情况调用 stripTrailingZeros 得到的数，取 scale() 将会是一个负数，舍去了末尾几个0 就返回负几

        // 四则运算
        // 对BigDecimal做加、减、乘时，精度不会丢失，但是做除法时，存在无法除尽的情况，这时，就必须指定精度以及如何进行截断
        BigDecimal bd1 = new BigDecimal("123.456");
        BigDecimal bd2 = new BigDecimal("23.456789");
        BigDecimal bd3 = bd1.divide(bd2, 10, RoundingMode.HALF_UP); // 保留10位小数并四舍五入
        System.out.println(bd3);
        // BigDecimal bd4 = bd1.divide(bd2); // 报错：ArithmeticException，因为除不尽
        BigDecimal[] dr = bd1.divideAndRemainder(bd2); // 计算出商和余数
        System.out.println("商为： " + dr[0] + " 余数为： " + dr[1]); // 商为： 5 余数为： 6.172055

        // BigDecimal 约分 使用 RoundingMode https://blog.csdn.net/qq_36497605/article/details/70597318
        BigDecimal d1 = new BigDecimal("124.45645");
        BigDecimal d2 = d1.setScale(4, RoundingMode.HALF_UP); // 四舍五入，124.4565
        System.out.println(d2);
        BigDecimal d3 = d1.setScale(4, RoundingMode.DOWN); // 直接截断，124.4564
        System.out.println(d3);
        BigDecimal d4 = d1.setScale(4, RoundingMode.HALF_EVEN); // 四舍六入五成双，5后有数 进一，没数看前一位，奇数进一，偶数舍去  24.4564
        System.out.println(d4);

        // 比较 BigDecimal 使用 compareTo 不要使用 equals，equals 还需要精度相同
        BigDecimal d5 = new BigDecimal("123.456");
        BigDecimal d6 = new BigDecimal("123.45600");
        System.out.println(d5.equals(d6)); // false,因为scale不同
        System.out.println(d5.equals(d6.stripTrailingZeros())); // true 因为d2去除尾部0后 scale 变为3
        System.out.println(d5.compareTo(d6)); // 0  0表示相等 -1表示d5小于d6 1表示大于

        // 千分位分隔
        DecimalFormat df = new DecimalFormat("###,###.##");
        df.setRoundingMode(RoundingMode.HALF_UP); // 设置保留两位小数的规则 四舍五入
        BigDecimal d7 = new BigDecimal("123456789.10511213");
        System.out.println(df.format(d7));
    }
}
