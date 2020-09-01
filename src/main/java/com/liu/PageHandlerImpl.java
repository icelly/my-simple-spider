package com.liu;

import com.liu.bean.UrlData;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘路阳
 * 日期 2020/8/27 15:53
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public class PageHandlerImpl implements PageHandler {
    Pattern date = Pattern.compile("(?<=时间：)\\s?\\d{4}-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d");
    Pattern tt = Pattern.compile("(?<=郑政出)[^。]*(?=。)");
    StringBuffer buffer = new StringBuffer("");


    @Override
    public void save(UrlData data) {
        String url = data.getUrl();
        Document doc = data.getDoc();
        if(url.matches(".*tdcr/\\d{5,10}.jhtml")){

            Elements elements = doc.getElementsByClass("sub-content");
            String  htmlContent = getTextFromHtml(elements.get(0));
            List<String> tmp= new ArrayList<>();
            Matcher m = tt.matcher(htmlContent);
            while(m.find()){
                tmp.add("郑政出"+m.group());
            }
            if(tmp.isEmpty()){
                tmp.add(htmlContent);
            }
            String dateStr = "";
            Matcher m2 = date.matcher(htmlContent);
            if(m2.find()){
                dateStr = m2.group();
            }
            buffer.append( url+System.lineSeparator());
            buffer.append(dateStr+System.lineSeparator());
            buffer.append(String.join(System.lineSeparator(),tmp)+ System.lineSeparator());
            if(buffer.length()>1024){
                flush();
            }
        }else {
            System.out.println(url+"列表页处理完成");
        }

    }

    @Override
    public void flush(){
        File  file = new File( "D:\\data.txt");
        try {
            FileUtils.writeStringToFile(file,buffer.toString(),"utf-8",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer= new StringBuffer("");
    }


    /**
     * htmlStr 去掉html标签
     */
    public static String getTextFromHtml(Element doc) {

        String text = doc.text();
        StringBuilder builder = new StringBuilder(text);
        int index = 0;
        while (builder.length() > index) {
            char tmp = builder.charAt(index);
            if (Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)) {
                builder.setCharAt(index, ' ');
            }
            index++;
        }
        text = builder.toString().replaceAll(" +", " ").trim();
        return text;
    }

}