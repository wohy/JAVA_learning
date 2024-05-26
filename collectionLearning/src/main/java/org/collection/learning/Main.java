package org.collection.learning;

import java.time.DayOfWeek;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        StudentObject studentOne = new StudentObject("小明", 12, "一班");
        StudentObject studentTwo = new StudentObject("小红", 13, "二班");
        StudentObject studentThree = new StudentObject("小芳", 14, "二班");
        StudentObject studentFour = new StudentObject("小亮", 13, "二班");
        List<StudentObject> list = new ArrayList<StudentObject>();
        list.add(studentOne);
        list.add(studentTwo);

        // List.of 返回的只是一个只读的 list
        List<StudentObject> listNewHandler = List.of(studentOne, studentTwo);
        System.out.println(list.size());
        printStudents(listNewHandler);


        // list to Array
        StudentObject[] studentObjects = list.toArray(new StudentObject[0]);
        System.out.println(studentObjects.length);
        System.out.println(Arrays.toString(Arrays.stream(studentObjects).toArray()));


        List<Integer> intList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            intList.add(i);
        }
        // 随机删除List中的一个元素:
        int removed = intList.remove((int) (Math.random() * intList.size()));
        int found = findOutMissingNumber(intList);
        System.out.println(intList);
        System.out.println("missing number: " + found);
        System.out.println(removed == found ? "测试成功" : "测试失败");


        List<String> stringList = List.of("A", "B", "C");
        // contains()、indexOf() 之所以能正常放入 String、Integer 这些对象，是因为 Java标准库 定义的这些类已经正确实现了 equals() 方法
        System.out.println(stringList.contains(new String("C"))); // true
        System.out.println(stringList.contains("C")); // true
        System.out.println(stringList.indexOf(new String("C"))); // 2


        System.out.println("是否存在一位叫的小红同学? " + (list.contains(new StudentObject("小红", 12, "二班")) ? "存在" : "不存在"));
        System.out.println(list.indexOf(new StudentObject("小红", 12, "二班")));


        // Map
        // HashMap 之所以能根据 key 直接拿到 value ，原因是它内部通过空间换时间的方法，
        // 用一个大数组存储所有 value，并根据 key 直接计算出 value 应该存储在哪个索引
        // 通过 key 计算索引的方式就是调用 key 对象的 hashCode() 方法，它返回一个int整数。HashMap 正是通过这个方法直接定位 key 对应的 value 的索引，继而直接返回 value。
        // key 是无序的
        Map<String, StudentObject[]> map = new HashMap<>();
        map.put("一班--小胡老师", new StudentObject[]{studentOne});
        map.put("二班--小黄老师", new StudentObject[]{studentTwo, studentThree, studentFour});
        /*
          正确使用Map必须保证：
          1.作为key的对象必须正确覆写 equals() 方法，相等的两个 key 实例调用 equals() 必须返回 true；
          2.作为key的对象还必须正确覆写 hashCode() 方法，且 hashCode() 方法要严格遵循以下规范：
             1) 如果两个对象相等，则两个对象的 hashCode() 必须相等；
             2) 如果两个对象不相等，则两个对象的 hashCode() 尽量不要相等。
         */
        for(Map.Entry<String, StudentObject[]> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "带领以下学生: " + Arrays.toString(entry.getValue()));
        }

        // 如果 key 为枚举类元素 推荐使用EnumMap，既保证速度，也不浪费空间
        Map<DayOfWeek, String> dayMap = new EnumMap<>(DayOfWeek.class);
        dayMap.put(DayOfWeek.MONDAY, "星期一");
        dayMap.put(DayOfWeek.TUESDAY, "星期二");
        dayMap.put(DayOfWeek.WEDNESDAY, "星期三");
        dayMap.put(DayOfWeek.THURSDAY, "星期四");
        dayMap.put(DayOfWeek.FRIDAY, "星期五");
        dayMap.put(DayOfWeek.SATURDAY, "星期六");
        dayMap.put(DayOfWeek.SUNDAY, "星期日");
        System.out.println(dayMap);
        System.out.println(dayMap.get(DayOfWeek.MONDAY));

        // SortedMap 内部会对 key 进行排序，内部是通过 treeMap 来实现的
        // 使用 TreeMap 时，放入的 Key 必须实现 Comparable 接口。String、Integer 这些类已经实现了 Comparable 接口，因此可以直接作为 Key 使用。
        // 作为 Key 的 class 没有实现 Comparable 接口，那么，必须在创建 TreeMap 时同时指定一个自定义排序算法
        Map<StudentObject, Integer> studentObjectMap = new TreeMap<>(new Comparator<StudentObject>() {
            // 根据年纪高到低排序
            public int compare(StudentObject s1, StudentObject s2) {
                // 相等时需要返回 0
                if (s2.age == s1.age) {
                    return 0;
                }
                return s1.age > s2.age ? -1 : 1;
            }
        });
        studentObjectMap.put(studentOne, 1);
        studentObjectMap.put(studentTwo, 2);
        studentObjectMap.put(studentThree, 3);
        for (StudentObject key : studentObjectMap.keySet()) {
            System.out.println(key);
        }


        // Set
        Set<StudentObject> studentSet = new HashSet<StudentObject>(list);
        studentSet.add(new StudentObject("小红", 13, "二班"));
        System.out.println(studentSet);


        // queue 队列 FIFO
        // int size()：获取队列长度；
        // boolean add(E)/boolean offer(E)：添加元素到队尾；
        // E remove()/E poll()：获取队首元素并从队列中删除；
        // E element()/E peek()：获取队首元素但并不从队列中删除。
        // 其中 add remove element 失败会直接抛错，其他一种失败则只会返回false或null
        // 失败场景如： 空队列继续移除、队满继续添加


        // priorityQueue 优先队列 可根据 StudentComparator 提供的优先级，决定谁先出列
        Queue<StudentObject> q = new PriorityQueue<>(new StudentComparator());
        q.offer(studentOne);
        q.offer(studentTwo);
        q.offer(studentThree);
        q.offer(studentFour);
        // age 最大的先出列
        System.out.println(q.poll()); // [name=小芳, age=14, classNumber=二班]


        // Deque 双端队列


        // Iterator 迭代器
        List<String> fruits = List.of("Apple", "Orange", "Pear");
        System.out.println("顺序：");
        for (String s : fruits) {
            System.out.println(s);
        }
        // java编译器并不知道如何遍历List。上述代码能够编译通过，只是因为编译器把for each循环通过Iterator改写为了普通的for循环
        /*
            for (Iterator<String> it = fruits.iterator(); it.hasNext(); ) {
                String s = it.next();
                System.out.println(s);
            }
        */
        ReverseList<String> reverselist = new ReverseList<>();
        reverselist.add("Apple");
        reverselist.add("Orange");
        reverselist.add("Pear");
        System.out.println("倒序：");
        for (String s : reverselist) {
            System.out.println(s);
        }


    }

    static void printStudents(List<StudentObject> students) {
        for (StudentObject studentObject : students) {
            System.out.println("学生：" + studentObject);
        }
    }

    // 一串原本连续的数字中找出缺失的数字
    static int findOutMissingNumber(List<Integer> list) {
        int result = 0;
        int i = 0;
        for (; i < list.size(); i++) {
            int item = list.get(i) + 1;
            if (item != list.get(i + 1)) {
                result = item;
                break;
            }
        }
        System.out.println(i);
        return result;
    }

}

class StudentObject {
    String name;
    int age;
    String classNumber;
    public StudentObject(String name, int age, String classNumber) {
        this.name = name;
        this.age = age;
        this.classNumber = classNumber;
    }
    @Override
    public String toString() {
        return "[name=" + name + ", age=" + age + ", classNumber=" + classNumber + "]";
    }
    // 需要重写其中的 equals ，才能在使用 List 的 contain/indexOf 时，正确匹配到元素
    // 否则会因为传入元素的存储地址不同导致 contain 永远返回 false，而实际已经存在值相同的元素了
    // 使用 set 更具某个元素去重时也需要重写，结合 hashCode 确认元素是否已经存在
    // 对比名字相等即相等
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentObject student = (StudentObject) o;
        return Objects.equals(name, student.name);
    }
    // 名字相等则 hashCode 也一致
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

// 实现Comparable接口，PriorityQueue会根据元素的排序顺序决定出队的优先级
class StudentComparator implements Comparator<StudentObject> {
    @Override
    public int compare(StudentObject o1, StudentObject o2) {
        if (o2.age == o1.age) {
            return 0;
        }
        return o1.age > o2.age ? -1 : 1;
    }
}

// 基于 iterator 实现具有 倒叙遍历 功能的类
class ReverseList<T> implements Iterable<T> {
    private final List<T> list = new ArrayList<>();

    public void add(T t) {
        list.add(t);
    }

    @Override
    public Iterator<T> iterator() {
        return new ReverseIterator(list.size());
    }

    class ReverseIterator implements Iterator<T> {
        int index;

        public ReverseIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index > 0;
        }

        @Override
        public T next() {
            index--;
            return ReverseList.this.list.get(index);
        }
    }
}
