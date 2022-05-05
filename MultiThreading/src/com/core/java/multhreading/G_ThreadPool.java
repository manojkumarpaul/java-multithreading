package com.core.java.multhreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class AppTP implements Runnable{
    private int id;

    public AppTP(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting task # :"+id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed task # :"+id);
    }
}
public class G_ThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 1; i<=5; i++){
            executorService.submit(new AppTP(i));
        }

        executorService.shutdown();
        System.out.println("All Tasks submitted");
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All Tasks completed");
    }
}
