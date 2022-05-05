package com.core.java.multhreading;

import java.util.Scanner;

class AppEx extends Thread{
    private volatile boolean running = true;

    @Override
    public void run() {
        super.run();
        while (running){
            System.out.println("Hello::");
        }
    }

    public void shutdown(){
        running = false;
    }
}
public class FourVolatileVariable {
    public static void main(String[] args) {
        AppEx t1 = new AppEx();
        t1.start();

        System.out.println("Enter to exit");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        t1.shutdown();
    }
}
