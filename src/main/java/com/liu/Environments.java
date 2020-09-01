package com.liu;

import com.liu.bean.UrlData;
import com.liu.bean.UrlPage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author icelly
 * 日期 2020/9/1 15:22
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public final class Environments {
    private boolean init =false;
    private AtomicInteger unFinishCounts =new AtomicInteger(1);
    private BlockingQueue<UrlPage> subLinksQueue =  new ArrayBlockingQueue<>(200);
    private BlockingQueue<UrlPage> urlTaskQueue = new ArrayBlockingQueue<>(500);
    private BlockingQueue<UrlData> urlDataQueue = new ArrayBlockingQueue<>(1000);

    private static Environments single = new Environments();


    private Environments() {
    }

    public static Environments inst() {

        return single;
    }

    public BlockingQueue<UrlPage> getSubLinksQueue() {
        return subLinksQueue;
    }

    public BlockingQueue<UrlPage> getUrlTaskQueue() {
        return urlTaskQueue;
    }

    public AtomicInteger getUnFinishCounts() {
        return unFinishCounts;
    }

    public BlockingQueue<UrlData> getUrlDataQueue() {
        return urlDataQueue;
    }
}
