package com.lgiter.practice.thread.abc;

public class PrintABC {
    public static void main(String[] args) {
        ShareSource shareSource = new ShareSource();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareSource.printA();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareSource.printB();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareSource.printC();
            }
        },"C").start();
    }
}
