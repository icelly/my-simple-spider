package com.liu.processer;

import com.liu.BaseEnvironments;
import com.liu.UrlChecker;
import com.liu.bean.UrlPage;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

/**
 * @author icelly
 * 日期 2020/9/1 14:51
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class UrlCheckProcesser extends BaseEnvironments implements Runnable {

    private Set<String> urlHashs = new ConcurrentSkipListSet<>();
    private UrlChecker checker;
    private int maxDeep = -1;
    private long count = 1;

    public UrlCheckProcesser(UrlChecker checker) {
        this.checker = checker;
    }

    public UrlCheckProcesser(UrlChecker checker, int maxDeep) {
        this.checker = checker;
        this.maxDeep = maxDeep;
    }

    public void addRoot(String url) {
        log.info("根地址：{}",url);
        urlHashs.add(url);
    }


    @Override
    public void run() {
        log.info("url校验器准备完毕...");
        while (getUnFinishCounts().get() > 0 || !getSubLinksQueue().isEmpty()) {
            try {
                UrlPage sub = getSubLinksQueue().poll(10, TimeUnit.SECONDS);
                if (null == sub) {
                    break;
                }
                String subUrl = sub.getUrl();
                if (maxDeep > 0 && sub.getDeep() > maxDeep) {
                    continue;
                }

                if (!urlHashs.contains(subUrl.trim())) {
                    if (checker.check(subUrl)) {
                        count++;
                        urlHashs.add(subUrl.trim());
                        getUnFinishCounts().incrementAndGet();
                        getUrlTaskQueue().offer(sub);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        log.info("url校验器 结束...");
        log.info("总共爬取地址个数：{}",count);
    }

}
