package com.core.java.multhreading;

class AppI implements Runnable {
    @Override
    public void run() {
        for(int i = 0; i<10; i++){
            System.out.println(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TwoUsingRunnableInterface{
    public static void main(String [] args) {
        Thread t1 = new Thread(new AppI());
        t1.start();

        new Thread(new AppI()).start();
        new Thread(new AppI()).start();
        new Thread(new AppI()).start();
        new Thread(new AppI()).start();
        new Thread(new AppI()).start();
        new Thread(new AppI()).start();
    }
}