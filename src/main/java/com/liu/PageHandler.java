package com.liu;

import com.liu.bean.UrlData;

/**
 * @author icelly
 * 日期 2020/8/27 15:53
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public interface PageHandler {
    /**
     * 每下载一个url后，对url进行保存处理
     *
     */
    void save(UrlData data);

    void flush();

}
