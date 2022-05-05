package com.core.java.multhreading;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NineProducerConsumer {
    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);

    public static void producer() throws InterruptedException {
        try {
            while(true)
                blockingQueue.put(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void consumer() throws InterruptedException {
        while (true){
            //Thread.sleep(100);
            if(new Random().nextInt(10) == 0)
            {
                Integer value = blockingQueue.take();
                System.out.println("Value taken out:: "+value+", Queue size:: "+blockingQueue.size());
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }
}
