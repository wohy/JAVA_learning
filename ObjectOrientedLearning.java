public class ObjectOrientedLearning {
    // 面向对象学习
    public static void main(String[] args) {

        // setAge 传入的为基础类型参数，实参 和 形参 存储的位置不同，相互不影响
        PersonsAge personsAge = new PersonsAge();
        int age = 1;
        personsAge.setAge(age);
        System.out.println(personsAge.getAge()); // 1
        age = 2;
        System.out.println(personsAge.getAge()); // 1

        // setName 形参 和 实参 中的 nameArray 指向同一块内存地址，所以 当 nameArray 中的第一项改变时，personsName1 的 name 也会改变
        PersonsNameArray personsName1 = new PersonsNameArray();
        String[] nameArray = new String[] {"a", "b", "c"};
        personsName1.setName(nameArray);
        System.out.println(personsName1.getName()); // a, b, c
        nameArray[0] = "d";
        System.out.println(personsName1.getName()); // d, b, c
        // getName 输出的依然为 d, b, c
        // 当前 nameArray 指向一块新的内存地址，但是没有改变原传入 setName 中那块内存地址存放数组的值
        nameArray = new String[] {"d", "e", "f"};
        System.out.println(personsName1.getName()); // d, b, c

        // String 为不可变类 传入 setName 中参数依然时原有的 e
        // 除了 String 外，所有的包装类型都是不变类
        PersonsName personsName2 = new PersonsName();
        String nameString = "e";
        personsName2.setName(nameString);
        System.out.println(personsName2.getName()); // e
        nameString = "f";
        System.out.println(personsName2.getName()); // e



        // 构造方法 根据入参决定调用的哪个构造方法
        Classes classOne = new Classes("数学");
        Classes classTwo = new Classes("体育", 90);
        System.out.println(classOne); // 数学课：45 分钟
        System.out.println(classTwo); // 体育课：30 分钟

        //继承
        Students students = new Students("小胡", 18, "软件工程", "1班");
        System.out.println(students);
    }
}

class PersonsName {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class PersonsNameArray {
    private String[] name;

    public String getName() {
        return String.join(", ", name);
    }

    public void setName(String[] name) {
        this.name = name;
    }
}



class PersonsAge {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class Classes {
    private String name;
    private int minutes;

    // 构造方法
    public Classes(String name, int minutes) {
        this.name = name;
        this.minutes = minutes;
    }

    // 构造方法 掉 构造方法  重载构造函数
    public Classes(String name) {
        this(name, 45);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return name + "课：" + minutes + " 分钟";
    }
}

// 继承 Students 为子类（扩展类），Person 为父类（超类、基类）
class Students extends Person {
    private String major;
    private String classes;
    public Students(String name, int age, String major, String classes) {
        this.major = major;
        this.classes = classes;
        setName(name);
        setAge(age);
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getClasses() {
        return classes;
    }
    public void setClasses(String classes) {
        this.classes = classes;
    }
    // Override 入参返回值相同重写, Overload 入参或者返回值不同，生成一个新的方法
    // 父类中 final 声明的方法无法重写
    // 所有的 class 最终都继承自 Object
    // 如果父类中只声明方法，而没有{}中执行语句的逻辑，那么需要将 方法声明为 abstract -- 抽象方法
    // 因为无法执行抽象方法，因此这个类也必须申明为抽象类（abstract class）
    // 使用abstract修饰的类就是抽象类。我们无法实例化一个抽象类
    // 抽象类本身被设计成只能用于被继承，因此，抽象类可以强迫子类实现其定义的抽象方法，否则编译会报错。因此，抽象方法实际上相当于定义了“规范”。
    @Override
    public String toString() {
        return "姓名: " + super.getName() + " 年龄: " + super.getAge() + "岁 专业: " + major + "专业 班级: " + classes;
    }
}