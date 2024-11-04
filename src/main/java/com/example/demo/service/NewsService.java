package com.example.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

//    public List<String> fetchNewsLinks(String url) {
//        List<String> links = new ArrayList<>();
//        try {
//            Document doc = Jsoup.connect(url).get();
//            Elements newsElements = doc.select("div.boxtabline3 a"); // 根据目标网站的HTML结构选择选择器
//
//            for (Element element : newsElements) {
//                Elements leftNewsElements = doc.select("news_left_line");
//                for (Element leftElement :leftNewsElements){
//                    String link = element.attr("href");
//                    String title = element.attr("title");
//                    links.add(title + " - " + link);
//                }
//                Elements rightNewsElements = doc.select("news_right_line");
//                for (Element rightElement :rightNewsElements){
//                    String link = element.attr("href");
//                    String title = element.attr("title");
//                    links.add(title + " - " + link);
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return links;
//    }
    public List<String> fetchLinks(String url) {
        List<String> links = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("div#dvscili1 a[href]");
            for (Element element : elements) {
                String link = element.attr("abs:href");
                links.add(link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }
}