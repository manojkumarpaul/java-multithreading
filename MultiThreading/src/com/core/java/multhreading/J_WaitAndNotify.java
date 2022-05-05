package com.core.java.multhreading;

import java.util.Scanner;

class AppPC{
    void producer() throws InterruptedException {
        synchronized (this){
            System.out.println("Started producing");
            Thread.sleep(4000);
            System.out.println("Started waiting for consumer to notify");
            wait();
            System.out.println("Resume producing");
        }
    }

    void consumer() throws InterruptedException {
        Thread.sleep(2000);
        Scanner scanner = new Scanner(System.in);
        synchronized (this){
            System.out.println("Started consuming");
            System.out.println("Waiting for return key press - ");
            scanner.nextLine();
            System.out.println("Return key pressed...");
            notify();
            System.out.println("Notify done");
            Thread.sleep(5000);
        }
    }
}

public class J_WaitAndNotify {
    public static void main(String[] args) {
        AppPC appPC = new AppPC();

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appPC.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ;
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appPC.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ;
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
