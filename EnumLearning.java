public class EnumLearning {

    public static void main(String[] args) {
        Weekday day1 = Weekday.MONDAY;
        day1.toDoToday();
        Weekday day2 = Weekday.TUESDAY;
        day2.toDoToday();
        Weekday day7 = Weekday.SUNDAY;
        day7.toDoToday();
    }
}

enum Weekday {
    MONDAY(1, "星期一"), TUESDAY(2, "星期二"), WEDNESDAY(3, "星期三"), THURSDAY(4, "星期四"), FRIDAY(5, "星期五"), SATURDAY(6, "星期六"), SUNDAY(0, "星期日");

    public final int dayValue;
    private final String dayName;
    private Weekday(int dayValue, String dayName) {
            this.dayValue = dayValue;
            this.dayName = dayName;
    }

    // 枚举类的 toString 方法可以被重写，但是 name() 方法不行
    @Override
    public String toString() {
        return this.dayName;
    }

    public void toDoToday() {
        System.out.println(this); // 对应的 dayName
        System.out.println(this.toString()); // 对应的 dayValue
        System.out.println(this.name());
        if (this == Weekday.SATURDAY || this == Weekday.SUNDAY) {
            System.out.println("Today is " + this + ". rest");
        } else  {
            System.out.println("Today is  " + this + ". work");
        }
    }
}
