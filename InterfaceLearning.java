// 接口学习
public class InterfaceLearning {
    public static void main(String[] args) {
        ChildPerson a = new ChildPerson("Jack");
        a.eat();
        a.sleep();
        SchoolStudent b = new SchoolStudent("James", "ShangHai", "交通大学");
        b.eat();
        b.sleep();
        b.schoolName();
        b.schoolLocation();
        b.schoolLevel();
    }
}

// 抽象类
abstract class ParentPerson {
    public abstract void eat();
    public abstract void sleep();
}

// 如果一个抽象类没有字段，所有方法都是抽象方法，可以把这个抽象类改成一个接口
// 接口中的定义的方法默认都是 public abstract, 所以这两个修饰不需要加上，加上与不加上的效果一致
interface ParentPersonInterface {
    void eat();
    void sleep();
}

// 使用一个 class 去实现一个 接口时，需要使用 implements 关键字
// 若少实现或声明一个方法，如少声明或实现 run 方法，则会报错，类“ChildPerson”必须声明为抽象，或为实现“parentPersonInterface”中的抽象方法“run()。
class ChildPerson implements ParentPersonInterface {
    private final String name;
    public ChildPerson(String name) {
        this.name = name;
    }
    @Override
    public void eat() {
        System.out.println(this.name + " Eat");
    }
    @Override
    public void sleep() {
        System.out.println(this.name + " Sleep");
    }
}

interface School {
    void schoolName();
    void schoolLocation();
    String getSchoolName();
    // 接口可以定义 default 方法， 实现类可以不重写该方法，也可重写该方法
    // 因为接口中没有字段，所以普通方法无法访问字段，而抽象方法可以访问实例字段
    // 学习了 静态字段后，会发现 interface 中可以有 静态字段，且只能是 public static final 类型
    default void schoolLevel() {
        System.out.println(getSchoolName() + " is a university");
    }
}
// 一个类可以实现多个接口
class SchoolStudent implements School, ParentPersonInterface {
    private final String name;
    private final String location;
    private final String schoolName;
    public SchoolStudent(String name, String location, String schoolName) {
        this.name = name;
        this.location = location;
        this.schoolName = schoolName;
    }
    @Override
    public void eat() {
        System.out.println(this.name + " Eat");
    }

    @Override
    public void sleep() {
        System.out.println(this.name + " Sleep");
    }

    @Override
    public void schoolName() {
        System.out.println(this.name + "在位于" + this.location + "的一所名为" + this.schoolName + "的学校上学");
    }

    @Override
    public void schoolLocation() {
        System.out.println(this.name + "在位于" + this.location + "的一所学校上学");
    }

    @Override
    public String getSchoolName() {
        return this.schoolName;
    }
}

