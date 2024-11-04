package com.example.demo.common;

public class TeaMatcher {

    public static String matchTea(int temperature) {
        if (temperature >= 25) {
            return "冰镇柠檬茶，薄荷茶，金银菊花茶，麦冬茶，洛神花茶，乌龙茶，绿茶，白茶，凉性花茶，苦丁茶，苦荞茶等具有清热解暑、生津止渴的功效，温馨提示：天气炎热请注意防暑降温";
        } else if (temperature > 20) {
            return "乌龙茶，茉莉花茶，桂花茶，玫瑰花茶，铁观音，大红袍，台湾高山茶等具有润肺止咳、滋阴养胃等功能";
        } else {
            return "热红茶";
        }
    }
}
