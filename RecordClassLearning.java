import java.util.Objects;

public class RecordClassLearning {
    // https://www.liaoxuefeng.com/wiki/1252599548343744/1331429187256353
    // 记录类学习
    public static void main(String[] args) {
        PointClass p = new PointClass(123, 456);
        PointClass p2 = new PointClass(123, 556);
        System.out.println(p.equals(p2));
        System.out.println(p.hashCode());
        System.out.println(p.x());
        System.out.println(p.y());
        System.out.println(p);
    }
}

record PointClass (int x, int y) {}

// 以上 record 类处理类似以下，重写 toString equals hashCode
/*
final class PointClass {
    private final int x;
    private final int y;

    public PointClass(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    @Override
    public String toString() {
        return String.format("Point[x=%s, y=%s]", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointClass that = (PointClass) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
       return Objects.hash(x, y);
    }
}

*/