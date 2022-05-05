package com.core.java.multhreading;

import java.util.concurrent.*;

public class P_InterruptingThreads {
    public static void main(String[] args) {
        System.out.println("Start");
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<?> fu = executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (int i = 0; i<1E8; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted");
                        break;
                    }
                }
                return null;
            }
        });
        //For interrupting threads we can use
        //t1.interrupt()
        executorService.shutdownNow();
        //fu.cancel(true); -- not working
        executorService.shutdown();

        try {
            Thread.sleep(500);
            //fu.cancel(true);
            executorService.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("End");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
