package com.core.java.multhreading;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account{
    private int balance = 10000;

    private void deposit(int amount){
        balance+=amount;
    }

    private void withdraw(int amount){
        balance-=amount;
    }

    public int getBalance(){
        return balance;
    }

    public static void transfer(Account account1, Account account2, int amount) {
        account1.withdraw(amount);
        account2.deposit(amount);
    }
}

class Runner{
    private Account account1 = new Account();
    private Account account2 = new Account();

    private Lock lockForAccount1 = new ReentrantLock();
    private Lock lockForAccount2 = new ReentrantLock();

    private void acquireLock(Lock lock1, Lock lock2){
        boolean gotFirstLock = false;
        boolean gotSecondLock = false;

        while (true){
            try {
                //Acquire Lock
                gotFirstLock  = lock1.tryLock();
                gotSecondLock = lock2.tryLock();

            } finally {
                if(gotFirstLock && gotSecondLock)
                    return;
                //Locks not acquired
                if(gotFirstLock)
                    lock1.unlock();
                if(gotSecondLock)
                    lock2.unlock();
            }
        }
    }

    public void firstThread(){
        acquireLock(lockForAccount1, lockForAccount2);
        try {
            for (int i = 0; i < 10000; i++)
                Account.transfer(account1, account2, new Random().nextInt(100));
        }finally {
            lockForAccount1.unlock();
            lockForAccount2.unlock();
        }
    }

    public void secondThread(){
        acquireLock(lockForAccount2, lockForAccount1);
        try {
            for (int i = 0; i < 10000; i++)
                Account.transfer(account2, account1, new Random().nextInt(100));
        }finally {
            lockForAccount2.unlock();
            lockForAccount1.unlock();
        }
    }

    public void finish(){
        System.out.println("Account1 Balance:: "+account1.getBalance());
        System.out.println("Account2 Balance:: "+account2.getBalance());
        System.out.println("Total Balance:: "+(account1.getBalance() + account2.getBalance()));
    }

}

public class M_Deadlock {
    public static void main(String[] args) {
        Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.firstThread();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.secondThread();
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
        runner.finish();
    }
}
