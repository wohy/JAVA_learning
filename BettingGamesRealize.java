import java.util.Random;
import java.util.Scanner;
/**
 * 押宝游戏
 */
public class BettingGamesRealize {
    public static void main(String[] args) {
        boolean getIt = false;
        int i = 3;
        do {
            // 生成  [0, 6]  随机数 3 个骰子 Math.random 成一个随机数x，x的范围是0 <= x < 1。

            /*
            int a = (int)(Math.random() * 6 + 1);
            int b = (int)(Math.random() * 6 + 1);
            int c = (int)(Math.random() * 6 + 1);
            */

            // 可以直接使用 Random();
            Random rand = new Random();
            // 生成 [0, 6] 随机数
            int a = rand.nextInt(7);
            int b = rand.nextInt(7);
            int c = rand.nextInt(7);

            --i;
            Scanner scanner = new Scanner(System.in);
            System.out.print("请押宝（豹子、大、小）：");
            String input = "";
            do {
                if (!scanner.hasNext()) {
                    System.out.println("输入错误，请输入‘豹子’、‘大’、或 ‘小’：");
                    scanner.next();
                }
                input = scanner.next();
                if (!input.equals("豹子") && !input.equals("大") && !input.equals("小")) {
                    System.out.println("输入错误，请输入‘豹子’、‘大’、或 ‘小’：");
                }
            } while (!input.equals("豹子") && !input.equals("大") && !input.equals("小"));
            String correctResult = "";
            boolean flag = false;
            if (a == b && a == c) {
                correctResult = "豹子";
            } else if (a + b + c > 9) {
                correctResult = "大";
            } else {
                correctResult = "小";
            }

            if (input.equals(correctResult)) {
                System.out.println("押对了 " + a + " " + b + " " + c + " " + correctResult);
                scanner.close();
                getIt = true;
            } else {
                System.out.println("押错了 " + a + " " + b + " " + c + " " + correctResult + (i != 0 ? (" 你还有 " + i + " 次机会") : " 没有机会了") );
            }
        } while (!getIt && i > 0);
    }
}
