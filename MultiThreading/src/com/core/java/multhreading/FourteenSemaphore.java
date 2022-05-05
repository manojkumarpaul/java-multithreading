package com.core.java.multhreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Connection{
    private static Connection instance = new Connection();
    private int connections = 0;
    private Semaphore semaphore = new Semaphore(10, true);

    public static Connection getInstance(){
        return instance;
    }

    public void connect() throws InterruptedException {
        try {
            semaphore.acquire();
            doConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
    public void doConnect() throws InterruptedException {
        synchronized (this) {
            connections++;
            System.out.println("Current active connections:: "+connections);
        }
        Thread.sleep(1000);
        synchronized (this) {
            connections--;
        }
    }
}
public class FourteenSemaphore {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i=1; i<=200; i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection.getInstance().connect();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }
}
