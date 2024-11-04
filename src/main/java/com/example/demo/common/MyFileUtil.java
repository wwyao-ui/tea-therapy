package com.example.demo.common;

import java.io.*;

public class MyFileUtil {
    //读取JSON文件To字符串
    public static String ReadFile(String path) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    //写文件
    public static void WriteFile(String path, String content) throws IOException {
        Clear(path);//清空文本内容
        File f = new File(path);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
        bw.write(content);
        bw.close();
    }

    /**
     * 先清空文件
     *
     * @param path
     * @throws IOException
     */
    public static void Clear(String path) throws IOException {
        File f = new File(path);
        //如果文件不存在 则创建文件
        if (!f.exists()) {
            System.out.println("正在创建文件"+f.getAbsolutePath());
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        fw.write("");
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        File f = new File("1.txt");
        f.createNewFile();
    }
}
