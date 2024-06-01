package org.java.thread.learning;

/**
 * 线程学习
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 线程的创建
        // 1. 从Thread派生一个自定义类，然后覆写run()方法
        MyThread th1 = new MyThread();
        th1.start();
        // 2. 创建Thread实例时，传入一个Runnable实例
        Thread th2 = new Thread(new MyRunnable());
        Thread th3 = new Thread(() -> {
            System.out.println("start new thread3!");
        });

        th1.join(); // 等待 th1 结束，即会执行
        long EndTime = System.currentTimeMillis();
        // 需要等待 2s 多才会执行 打印 th1 end!
        System.out.println("th1 end!");
        System.out.println("continued time: " + (EndTime - th1.startTime)); // continued time: 2006

        th3.setDaemon(true); // 将 th3 设置为守护线程。在JVM中，所有非守护线程都执行完毕后，无论有没有守护线程，虚拟机都会自动退出。
        th2.start();
        // 运行
        th3.start();

        // synchronized， 使用 synchronized 对某个对象进行加锁。同一时间仅有一个线程可以访问该对象。达到数据同步的目的。
        // 使下一个线程执行时，可以顺利拿到上一个线程的执行结果


        // 死锁
//        public void add(int m) {
//            synchronized(lockA) { // 获得lockA的锁
//                this.value += m;
//                synchronized(lockB) { // 获得lockB的锁
//                    this.another += m;
//                } // 释放lockB的锁
//            } // 释放lockA的锁
//        }
//
//        public void dec(int m) {
//            synchronized(lockB) { // 获得lockB的锁
//                this.another -= m;
//                synchronized(lockA) { // 获得lockA的锁
//                    this.value -= m;
//                } // 释放lockA的锁
//            } // 释放lockB的锁
//        }
        // 线程 add 依赖等待 lockB 释放，才会释放 lockA， 而线程 dec 再等待 lockA 释放，才会释放 lockB, 两个线程锁相互依赖，导致死锁
        // 避免死锁：线程获取锁的顺序要一致
    }
}

class MyThread extends Thread {
    public long startTime;
    @Override
    public void run() {
        this.startTime = System.currentTimeMillis();
        System.out.println("start new thread1!");
        try {
            // 线程进入 记时等待状态， 2s 后继续
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("start new thread2!");
    }
}


// 线程安全类，及该类运行多线程访问
class Counter {
    private int count = 0;

    // 锁住当前实例，实现实例隔离，add dec 每次改变的都为该实例上的 count，且多个线程调用 add / dec 方法改变当前实例时，都是需要等待锁释放
    // 锁住当前实例 方法一
    public void add(int n) {
        synchronized(this) {
            count += n;
        }
    }

    // 锁住当前实例 方法二
    public synchronized void dec(int n) {
        count -= n;
    }

    public int get() {
        return count;
    }
}