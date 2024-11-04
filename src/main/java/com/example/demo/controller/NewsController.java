package com.example.demo.controller;
import com.example.demo.entity.TeaAndInternational;
import com.example.demo.service.impl.TeaAndInternationalImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import java.util.HashMap;
@RestController
@RequestMapping("/news")
public class NewsController {
    @GetMapping("/links")
    public ResponseEntity<Map<String, Object>> getWebContent() {
        List<Map<String, String>> links = new ArrayList<>();

        try {
            String url = "http://www.tricaas.com";
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("#dvscili1 a[href]");
            System.out.println("elements"+elements);
            for (Element element : elements) {
                System.out.println("进入循环");
                String link = element.attr("abs:href");
                String text = element.text();
                // 使用HashMap创建Map实例
                Map<String, String> linkData = new HashMap<>();
                linkData.put("link", link);
                linkData.put("text", text);
                links.add(linkData);
            }
            // 创建响应体
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("data", links);
            // 返回成功响应
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (IOException e) {
            // 创建错误响应体
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("error", "Failed to fetch web content");
            // 返回内部服务器错误响应
            return new ResponseEntity<>(errorResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/links2")
    public ResponseEntity<Map<String, Object>> getWebContent2() {
        List<Map<String, String>> links = new ArrayList<>();

        try {
            String url = "http://www.tricaas.com";
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("#dvscili2 a[href]");
            System.out.println("elements"+elements);
            for (Element element : elements) {
                System.out.println("进入循环");
                String link = element.attr("abs:href");
                String text = element.text();
                // 使用HashMap创建Map实例
                Map<String, String> linkData = new HashMap<>();
                linkData.put("link", link);
                linkData.put("text", text);
                links.add(linkData);
            }
            // 创建响应体
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("data", links);
            // 返回成功响应
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (IOException e) {
            // 创建错误响应体
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("error", "Failed to fetch web content");
            // 返回内部服务器错误响应
            return new ResponseEntity<>(errorResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Resource
    TeaAndInternationalImpl teaAndInternalImpl;
    @GetMapping("/teaAndInternational")
        public List<TeaAndInternational> getTeaWebContent() {
                return teaAndInternalImpl.getTeaAndInternal();
        }

}
