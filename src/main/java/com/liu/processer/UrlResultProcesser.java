package com.liu.processer;

import com.liu.BaseEnvironments;
import com.liu.Environments;
import com.liu.PageHandler;
import com.liu.UrlChecker;
import com.liu.bean.UrlData;
import com.liu.bean.UrlPage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author icelly
 * 日期 2020/9/1 15:40
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class UrlResultProcesser extends BaseEnvironments implements Runnable {

    private PageHandler handler;

    public UrlResultProcesser( PageHandler handler) {
        this.handler = handler;
    }


    @Override
    public void run() {
        log.info("url结果存储 准备完毕...");
        while (Environments.inst().getUnFinishCounts().get() > 0 || !Environments.inst().getUrlDataQueue().isEmpty()) {

            try {
                UrlData data = Environments.inst().getUrlDataQueue().poll(10, TimeUnit.SECONDS);
                if(null==data){
                    break;
                }
                handler.save(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        handler.flush();
        log.info("url结果存储 结束...");
    }


}
