package test.concurrent;

import concurrent.AutoSizeThreadPool;
import concurrent.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class AutoSizeThreadPoolTest {

    public static void main(String[] args) {
        AutoSizeThreadPool autoSizeThreadPool = new AutoSizeThreadPool(5, 10,
                10, TimeUnit.SECONDS, new ArrayBlockingQueue(100));
        for (int i = 0; i < 100; i++) {
            Task task = new Task(i);
            autoSizeThreadPool.execute(task);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
        System.out.println(autoSizeThreadPool.monitor());
        autoSizeThreadPool.shutdown();
    }
}
