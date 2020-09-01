package com.liu;

/**
 * @author icelly
 * 日期 2020/9/1 16:08
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public class UrlCheckImpl  implements UrlChecker{
    @Override
    public boolean check(String url) {

        if (url.startsWith("http://zrzyhghj.zhengzhou.gov.cn/tdcr")) {
            if (url.matches(".*tdcr/(index_[12]?[0-9]|\\d{1,7}).jhtml")) {
                return true;
            } else {
                System.out.println("丢弃" + url);
                return false;
            }
        } else {
            return false;
        }

    }
}
