package com.core.java.multhreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class AppCDL implements Runnable{
    private CountDownLatch countDownLatch;
    public AppCDL(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Work Started");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Work Ended");
        countDownLatch.countDown();
    }
}
public class EightCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        
        for (int i=1; i<=3; i++){
            executorService.submit(new AppCDL(countDownLatch));
        }

        try {
                countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All Works Completed");
    }
}
