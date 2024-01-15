package com.lgiter.practice.mmp.asm.thread;

import java.util.concurrent.CompletableFuture;

/**
 * Author: lixiaolong
 * Date: 2023/5/19
 * Desc:
 */
public class AsyncThread {

    public static void print(){

    }


    public static void main(String[] args) {
        CompletableFuture.runAsync(() -> {
            print();
        });
    }


}
