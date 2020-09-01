package com.liu.bean;


import com.liu.BaseEnvironments;
import lombok.Data;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * url发现任务
 */
@Data
public class UrlTask extends BaseEnvironments implements Runnable {

    private UrlPage urlPage;

    public UrlTask(UrlPage page) {
        this.urlPage = page;
    }

    /**
     * 解析并拆分子任务
     *
     * @return Long 总数
     */

    @Override
    public void run() {
        UrlPage page = pullUrl(urlPage);
        if (null != page.getDocument()) {
            Set<String> parseSubLinks = parseSubLinks(page.getDocument());
            for (String sub : parseSubLinks) {
                getSubLinksQueue().offer(new UrlPage(sub,urlPage.getDeep()+1));
            }
            getUrlDataQueue().offer(new UrlData(page.getUrl(),page.getDocument()));
        }
        getUnFinishCounts().decrementAndGet();
    }
    /**
     * 获取url 的内容
     */
    public UrlPage pullUrl(UrlPage page) {
        try {
            Connection.Response res = getRes(page.getUrl(), true);
            if (res.statusCode() == 301) {
                String location = res.header("location");
                page.setUrl(location);
                page = pullUrl(page);
            } else if (res.statusCode() == 200) {
                page.setDocument(res.parse());
            } else {
                page.setDocument(Jsoup.parse(new URL(page.getUrl()), 10000));
            }

        } catch (Exception e) {
            System.err.println("页面加载失败:" + page.getUrl());
        }
        return page;
    }

    /**
     * 获取connection
     */
    public static Connection.Response getRes(String url, boolean flag) throws IOException {
        return Jsoup.connect(url).timeout(10000)
                .method(Connection.Method.GET)
                .followRedirects(flag).execute();
    }


    /**
     * 提取子链接
     *
     * @param doc
     * @return 获得链接
     */
    public static Set<String> parseSubLinks(final Document doc) {
        Elements links = doc.select("a[href]");
        Set<String> list = new HashSet<String>();
        String endWithsExlude = ".*?(.gif|.jpg|.png|.javascript|.css|.js|.exe|.apk|.zip|.7z|.tar|.gz|.mp4|.rmvb|.avi|.mp3|.doc|.docx|.xls|.xlsx|.ppt|.pdf|.PDF|.jar|.json)$";
        for (Element link : links) {
            String url = link.attr("abs:href");
            url = url.trim();
            if (url.matches(endWithsExlude)) {
                continue;
            }
            list.add(url);
        }
        return list;
    }

}
