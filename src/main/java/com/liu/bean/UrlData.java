package com.liu.bean;

import lombok.Data;
import org.jsoup.nodes.Document;

/**
 * @author icelly
 * 日期 2020/9/1 15:21
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */

@Data
public class UrlData {
    private String url;
    private Document doc;


    public UrlData(String url, Document doc) {
        this.url = url;
        this.doc = doc;
    }
}
