package com.liu.processer;

import com.liu.BaseEnvironments;
import com.liu.bean.UrlPage;
import com.liu.bean.UrlTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘路阳
 * 日期 2020/9/1 15:09
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class UrlTaskProcesser extends BaseEnvironments implements Runnable {

    ExecutorService executorService = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void run() {
        log.info("url任务处理 准备完毕...");
        while (getUnFinishCounts().get() > 0 || !getUrlTaskQueue().isEmpty()) {
            try {
                UrlPage sub = getUrlTaskQueue().poll(10, TimeUnit.SECONDS);
                if(null==sub){
                    continue;
                }
                UrlTask t = new UrlTask(sub);
                executorService.submit(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        log.info("url任务处理 结束...");
        executorService.shutdown();

    }
}
