package com.lgiter.practice.thread.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Author: lixiaolong
 * @Description:
 * @Date: 2021/11/8
 */
@Slf4j
public class ThreadPoolTest {
    static ThreadFactory threadFactory1 = new ThreadFactoryBuilder().setNameFormat("截图线程-1-%d").build();

    static ExecutorService executorService1 = Executors.newFixedThreadPool(5, threadFactory1);

    public static void main(String[] args) {
        log.info("线程 {} 开始执行",Thread.currentThread().getName());
        executorService1.execute(()->{
            log.info("线程 {} 开始执行",Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
            }
            log.info("线程 {} 执行结束",Thread.currentThread().getName());
        });
        log.info("线程 {} 执行结束",Thread.currentThread().getName());
    }

    /**
     * Junit 不支持多线程
     * 因为在主线程执行完之后，是不会阻塞等待子线程的执行完成，而是直接调用System.exit() 推出JVM进程
     * 解决方法：
     * 1.thread.sleep() 让主线程等待
     * 2.countDownLaunch 也是让主线程等待子线程执行完成之后再结束
     *
     */
    @Test
    public void test(){
        log.info("线程 {} 开始执行",Thread.currentThread().getName());
        executorService1.execute(()->{
            log.info("线程 {} 开始执行",Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
            }
            log.info("线程 {} 执行结束",Thread.currentThread().getName());
        });
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("线程 {} 执行结束",Thread.currentThread().getName());
    }


//    public static void main(String[] args) {
//
//        log.info("线程 {} 开始执行",Thread.currentThread().getName());
//        new Thread(()->{
//         log.info("线程 {} 开始执行",Thread.currentThread().getName());
//            try {
//                Thread.sleep(2000L);
//            } catch (InterruptedException e) {
//            }
//            log.info("线程 {} 执行结束",Thread.currentThread().getName());
//        }).start();
//        log.info("线程 {} 执行结束",Thread.currentThread().getName());
//    }

}
