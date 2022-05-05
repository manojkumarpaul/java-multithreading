package com.core.java.multhreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class AppLLS{
    private static final int LIMIT = 10 ;
    private List<Integer> list = new ArrayList<>(LIMIT);
    private Object lock = new Object();

    public void producer() throws InterruptedException {
        int value=0;
        Thread.sleep(new Random().nextInt(7000));
        synchronized (lock) {
            while (true) {
                if (list.size() == LIMIT) {
                    lock.wait();
                }
                list.add(value++);
                System.out.print("List size: " + list.size());
                lock.notify();
                System.out.println("; Value added: " + value);
            }
        }
    }

    public void consumer() throws InterruptedException {
        int index=0, value=0;
        synchronized (lock) {
            while (true) {
                if (list.size() == 0) {
                    lock.wait();
                }
                System.out.print("List size: " + list.size());
                value = list.remove(index);
                System.out.println("; Value removed: " + value);
                lock.notify();
                Thread.sleep(new Random().nextInt(5000));
            }

        }
    }
}

public class K_LowLevelSynchronization
{
    public static void main(String[] args) {
        AppLLS appLLS= new AppLLS();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appLLS.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appLLS.consumer();
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
