package com.liu;

import com.liu.bean.UrlData;
import com.liu.bean.UrlPage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author icelly
 * 日期 2020/9/1 15:29
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public class BaseEnvironments {

    public BlockingQueue<UrlPage> getSubLinksQueue() {
        return Environments.inst().getSubLinksQueue();
    }

    public BlockingQueue<UrlPage> getUrlTaskQueue() {
        return Environments.inst().getUrlTaskQueue();
    }

    public AtomicInteger getUnFinishCounts() {
        return Environments.inst().getUnFinishCounts();
    }

    public BlockingQueue<UrlData> getUrlDataQueue() {
        return Environments.inst().getUrlDataQueue();
    }
}
