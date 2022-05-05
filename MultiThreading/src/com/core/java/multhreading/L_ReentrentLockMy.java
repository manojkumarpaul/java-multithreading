package com.core.java.multhreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AppRL{
    private static final int LIMIT = 10 ;
    private List<Integer> list = new ArrayList<>(LIMIT);
    private Lock reentrentLock = new ReentrantLock();
    private Condition condition = reentrentLock.newCondition();

    public void producer() throws InterruptedException {
        reentrentLock.lock();
        int value=0;
        try {
            while (true) {
                if (list.size() == LIMIT) {
                    condition.await();
                }
                list.add(value++);
                System.out.print("List size: " + list.size());
                condition.signal();
                System.out.println("; Value added: " + value);
            }
        }
        finally {
            reentrentLock.unlock();
        }
    }

    public void consumer() throws InterruptedException {
        reentrentLock.lock();
        try {
            int index = 0, value = 0;
            while (true) {
                if (list.size() == 0) {
                    condition.await();
                }
                System.out.print("List size: " + list.size());
                value = list.remove(index);
                System.out.println("; Value removed: " + value);
                condition.signal();
                Thread.sleep(new Random().nextInt(5000));
            }
        }finally {
            reentrentLock.unlock();
        }
    }
}

public class L_ReentrentLockMy
{
    public static void main(String[] args) {
        AppRL appRL= new AppRL();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appRL.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appRL.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
