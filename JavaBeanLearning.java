import java.beans.*;

public class JavaBeanLearning {
    public static void main(String[] args) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(PersonInfo.class);
        // 循环打印 pd beanInfo 上的属性。  age/class/name，class 是从 Object 继承的 getClass() 方法带来的
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            System.out.println(pd.getName());
            System.out.println(pd.getReadMethod());
            System.out.println(pd.getWriteMethod());
        }
    }
}

class Person {
    private String name;
    private int age;

    // 定义好对应属性后，直接右键自动生成 getter setter。 之后导入 beans 库 可使用
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
