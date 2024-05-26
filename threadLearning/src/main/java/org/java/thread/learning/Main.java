package org.java.thread.learning;

/**
 * 线程学习
 */
public class Main {
    public static void main(String[] args) {
        // 线程的创建
        // 1. 从Thread派生一个自定义类，然后覆写run()方法
        Thread th1 = new MyThread();
        th1.start();

        // 2. 创建Thread实例时，传入一个Runnable实例
        Thread th2 = new Thread(new MyRunnable());
        Thread th3 = new Thread(() -> {
            System.out.println("start new thread3!");
        });
        th2.start();
        th3.start();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("start new thread1!");
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("start new thread2!");
    }
}