package com.liu;

/**
 * @author icelly
 * 日期 2020/8/27 16:11
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public class Starter {

    public static void main(String[] args)  {
        PageHandler handler = new PageHandlerImpl();
        UrlChecker checker = new UrlCheckImpl();
        String url = "http://zrzyhghj.zhengzhou.gov.cn/tdcr/index.jhtml";
        TaskFactory.start(url,checker,handler);
    }



}
