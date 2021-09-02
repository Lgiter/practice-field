package com.lgiter.practice.thread.abc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ShareSource {
    private int num = 1;
    private final static ReentrantLock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printA() {
        try {
            lock.lock();
            while (num != 1) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t==>" + num);
            num = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        try {
            lock.lock();
            while (num != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t==>" + num);
            num = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        try {
            lock.lock();
            while (num != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t==>" + num);
            num = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
