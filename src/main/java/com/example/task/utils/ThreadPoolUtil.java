package com.example.task.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class ThreadPoolUtil {
    private final ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolUtil() {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1000);
        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 10, TimeUnit.SECONDS, blockingQueue);
        threadPoolExecutor.setRejectedExecutionHandler((r, executor) ->
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            threadPoolExecutor.execute(r);
        });
    }

    public void executeTask(TaskThread taskThread)
    {
        threadPoolExecutor.submit(taskThread);
    }
}
