// 包 学习
package packageLearning;

public class PackageLearningClass {
    public void handleSayHello() {
        System.out.println("Hello World!");
        ANestedClass a = new ANestedClass();
        a.handleSayHelloPrivate();
    }

    // 建议私有方法发在公有方法之后
    private static void sayHelloPrivate() {
        System.out.println("How are you?");
    }

    // 内部类，可访问当前类的私有方法
     static class ANestedClass {
        public void handleSayHelloPrivate() {
            PackageLearningClass.sayHelloPrivate();
        }
    }
}
