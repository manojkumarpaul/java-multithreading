package com.core.java.multhreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SixSynchronizedCodeBlock {
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    Object object1 = new Object();
    Object object2 = new Object();

    private  void stageOne(){
        synchronized (object1) {
            list1.add(new Random().nextInt());
        }
    }

    private  void stageTwo(){
        synchronized (object2){
            list2.add(new Random().nextInt());
        }
    }

    public void process(){
        for(int i=0; i<1000;i++){
            stageOne();
            stageTwo();
        }

    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SixSynchronizedCodeBlock sixSynchronizedCodeBlock = new SixSynchronizedCodeBlock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sixSynchronizedCodeBlock.process();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sixSynchronizedCodeBlock.process();
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

        long end = System.currentTimeMillis();

        System.out.println("Total Time Taken: "+(end-start));
        System.out.println("List1 size: "+sixSynchronizedCodeBlock.list1.size());
        System.out.println("List2 size: "+sixSynchronizedCodeBlock.list2.size());

    }
}
