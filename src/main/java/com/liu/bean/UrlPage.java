package com.liu.bean;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.nodes.Document;

/**
 * @author 刘路阳
 * 日期 2020/3/17 15:15
 * 描述  页面对象
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
public class UrlPage {

    /**
     * 爬深
     */
    private int deep;
    /**
     * url地址
     */
    private String url;

    /**
     * 页面 Html
     */
    private Document document;

    public UrlPage( String url,int deep) {
        this.deep = deep;
        this.url = url;
    }
}
