// 静态方法/静态字段学习
// https://www.liaoxuefeng.com/wiki/1252599548343744/1260464690677856
public class StaticMethodLearning {
    public static void main(String[] args) {
        // 静态方法可以通过类直接访问
        StaticPeron.setName("Messi");
        StaticPeron.getName(); // Messi
        // 通过实例引用调用静态方法时不规范的，危险的做法
        StaticPeron personOne = new StaticPeron();
        personOne.setName("Kobe");
        personOne.getName(); // Kobe
        StaticPeron personTwo = new StaticPeron();
        personTwo.setName("James");
        personTwo.getName(); // James
        System.out.println(StaticPeron.getCount() + " instances are created");
    }
}

class StaticPeron {
    // 静态字段不属于实例，而属于类，类的所有实例公用一个静态字段。每个实例都有自己独立空间，但是静态字段、方法存放在共享的空间
    public static String name;

    public static int count;

    public StaticPeron() {
        count++;
    }

    public static int getCount() {
        return count;
    }

    public static void setName(String value) {
        name = value;
    }
    public static void getName() {
        System.out.println(name);
    }
}
