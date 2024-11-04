package com.example.demo.controller;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class CaptchaController {

    @GetMapping("/captcha")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应类型为图片
        response.setContentType("image/jpeg");

        // 创建一个随机数生成器类
        Random randomGenerator = new Random();

        // 定义图像buffer
        int width = 60, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        // 设置背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设置字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        // 设置字体颜色
        g.setColor(getRandColor(160, 200));

        // 随机产生4个字符，存入数组中
        String str = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(randomGenerator.nextInt(10));
            str += rand;
            // 将产生的四个随机数组合在一起
            g.drawString(rand, 13 * i + 6, 16);
        }

        // 存入session
        HttpSession session = request.getSession();
        session.setAttribute("vcode", str);

        // 图像生效
        g.dispose();

        // 输出图像到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        Random random=new Random();
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
