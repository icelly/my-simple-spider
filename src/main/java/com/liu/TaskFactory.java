package com.liu;

import com.liu.bean.UrlData;
import com.liu.bean.UrlPage;
import com.liu.processer.UrlCheckProcesser;
import com.liu.processer.UrlResultProcesser;
import com.liu.processer.UrlTaskProcesser;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author icelly
 * 日期 2020/9/1 15:01
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public class TaskFactory {

    public static void start (String rootUrl,UrlChecker checker ,PageHandler handler){
        start(rootUrl,checker,handler,-1);
    }

    public static void start (String rootUrl,UrlChecker checker ,PageHandler handler,int maxDeep){
        ExecutorService loopThread = new ThreadPoolExecutor(3,5,50, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000));

        UrlTaskProcesser processer = new UrlTaskProcesser();

        loopThread.submit(processer);

        UrlCheckProcesser checkProcesser = new UrlCheckProcesser(checker,maxDeep);
        checkProcesser.addRoot(rootUrl.trim());
        loopThread.submit(checkProcesser);

        UrlResultProcesser resultProcesser = new UrlResultProcesser(handler);
        loopThread.submit(resultProcesser);

        UrlPage page = new UrlPage(rootUrl,1);
        Environments.inst().getUrlTaskQueue().offer(page);

        loopThread.shutdown();

    }





}
