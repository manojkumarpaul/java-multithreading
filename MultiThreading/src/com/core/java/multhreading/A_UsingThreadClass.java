package com.core.java.multhreading;
class AppE extends Thread {
        @Override
        public void run() {
            super.run();
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
public class A_UsingThreadClass{
    public static void main(String[] args) {
        AppE t1 = new AppE();
        t1.start();

        new AppE().start();
        new AppE().start();
        new AppE().start();
        new AppE().start();
        new AppE().start();
        new AppE().start();
        new AppE().start();
        new AppE().start();
        new AppE().start();
    }

}