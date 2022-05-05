package com.core.java.multhreading;

public class E_Synchronized {
    private int count;

    public synchronized void increaseCount() {
        count++;
    }

    public static void main(String[] args) {
        E_Synchronized e_synchronized = new E_Synchronized();
        e_synchronized.doJob();
    }

    public void doJob() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<1000; i++)
                    increaseCount();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<1000; i++)
                    increaseCount();
            }
        });
        t1.start();
        t2.start();

        System.out.println("T1 Alive:: "+t1.isAlive());

        try {
            t1.join();
            System.out.println("T2 Alive:: "+t2.isAlive());
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("T1 Alive:: "+t1.isAlive());
        System.out.println("T2 Alive:: "+t2.isAlive());

        System.out.println("Count is :: "+count);
    }
}
