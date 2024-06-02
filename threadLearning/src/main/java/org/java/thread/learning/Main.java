package org.java.thread.learning;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;

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


        // 线程协调：
        // 多线程协调运行的原则就是：当条件不满足时，线程进入等待状态；当条件满足时，线程被唤醒，继续执行任务。
        // 使用 this.wait 将该线程置于等待，使用 this.notify 通知等待线程执行。
        // 一般使用 notifyAll, 用于唤醒所有等待线程，其中将有一个会获取到锁，其他再次进入等待


        // java collection 提供的线程安全集合
        // interface	 non-thread-safe	             thread-safe
        // List	           ArrayList	              CopyOnWriteArrayList
        // Map	           HashMap	                    ConcurrentHashMap
        // Set	         HashSet / TreeSet	            CopyOnWriteArraySet
        // Queue	     ArrayDeque / LinkedList	   ArrayBlockingQueue / LinkedBlockingQueue
        // Deque	     ArrayDeque / LinkedList   	     LinkedBlockingDeque

        // 使用java.util.concurrent.atomic提供的原子操作可以简化多线程编程：
        // 原子操作实现了无锁的线程安全；
        // 适用于计数器，累加器等。

        //对线程池提交一个 Callable 任务，可以获得一个Future对象；
        //可以用Future在将来某个时刻获取结果。
        // CompletableFuture 异步获取最新值，CompletableFuture可以指定异步处理流程：
        // thenAccept()处理正常结果；
        // exceptional()处理异常结果；
        // thenApplyAsync()用于串行化另一个CompletableFuture；
        // anyOf()和allOf()用于并行化多个CompletableFuture。
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

// ReentrantLock 可以替代 synchronized 同步，
// ReentrantLock 可以使用 tryLock 尝试获取锁，ReentrantLock 获取锁更安全，
// 获取到锁之后才会执行 try 中代码，并在 finally 中释放锁
class CounterWithReentrantLock {
    private final Lock lock = new ReentrantLock();
    private int count;

    public void add(int n) {
        lock.lock();
        try {
            count += n;
        } finally {
            lock.unlock();
        }
    }
}

// 使用 newCondition 达到条件不满足时线程等待的目的
class TaskQueueWithReentrantLock {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void addTask(String s) {
        lock.lock();
        try {
            queue.add(s);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String getTask() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                condition.await();
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }
}

// 为提升读取的效率，理想实现应该是允许多个线程同时读，但只要有一个线程在写，其他线程就必须等待。
// 使用 ReadWriteLock
class CounterWithReadWriteLock {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock rlock = readWriteLock.readLock();
    private final Lock wlock = readWriteLock.writeLock();
    private final int[] counts = new int[10];

    public void inc(int index) {
        wlock.lock(); // 加写锁
        try {
            counts[index] += 1;
        } finally {
            wlock.unlock(); // 释放写锁
        }
    }

    public int[] get() {
        rlock.lock(); // 加读锁
        try {
            return Arrays.copyOf(counts, counts.length);
        } finally {
            rlock.unlock(); // 释放读锁
        }
    }
}

// ReadWriteLock 在读取时，无法写入，而需要等待读取完成才能写入。
// 更理想的实现时，读取时，也允许写入（乐观锁），只是读取时需要判断是否有新的写入，如果有的话，再通过读取时无法写入的方式（悲观锁）读取最新的值
class CounterWithStampedLock {
    private final StampedLock stampedLock = new StampedLock();
    private int count;

    public void addCount(int n) {
        long stamp = stampedLock.writeLock(); // 获取写锁
        try {
            count += n;
        } finally {
            stampedLock.unlockWrite(stamp); // 释放写锁
        }
    }

    public int getCount() {
        long stamp = stampedLock.tryOptimisticRead(); // 获取一个乐观读锁
        int res = count;
        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock(); // 获取一个悲观读锁
            try {
                res = count;
            } finally {
                stampedLock.unlockRead(stamp); // 释放悲观读锁
            }
        }
        return res;
    }
}

// 限制允许几个线程可同时访问
class AccessLimitControl {
    // 任意时刻仅允许最多3个线程获取许可:
    final Semaphore semaphore = new Semaphore(3);

    public String access() throws Exception {
        // 如果超过了许可数量,其他线程将在此等待:
        semaphore.acquire();
        try {
            return UUID.randomUUID().toString();
        } finally {
            semaphore.release();
        }
    }
}