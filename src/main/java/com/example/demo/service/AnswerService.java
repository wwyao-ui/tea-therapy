package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class AnswerService extends WebSocketListener {
    private static final String HOST_URL = "https://spark-api.xf-yun.com/v1.1/chat";
    private static final String APP_ID = "63c46200";
    private static final String API_SECRET = "Mjg1NjU2NjhiMWUyYzc5Njc4MjdjYzU5";
    private static final String API_KEY = "c2ff56d45088b249734d46c53b301589";

    private static final Gson gson = new Gson();
    private static List<RoleContent> historyList = new ArrayList<>();
    private CompletableFuture<String> responseFuture;
    private StringBuilder responseBuilder;  // 用于累积所有分段响应



    private WebSocket webSocket;

    public String askModel(String question) {
        responseFuture = new CompletableFuture<>();
        responseBuilder = new StringBuilder();  // 初始化累积器

        // 发送问题给 WebSocket
        historyList.add(new RoleContent("user", question));
        OkHttpClient client = new OkHttpClient.Builder().build();
        String authUrl;
        try {
            authUrl = getAuthUrl(HOST_URL, API_KEY, API_SECRET);
        } catch (Exception e) {
            return "Error generating authentication URL: " + e.getMessage();
        }

        Request request = new Request.Builder().url(authUrl.replace("https://", "wss://")).build();
        webSocket = client.newWebSocket(request, this);

        try {
            // 等待 WebSocket 响应，设置超时时间为 10 秒
            return responseFuture.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            return "Request timed out.";
        } catch (Exception e) {
            return "Error occurred while waiting for response: " + e.getMessage();
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        sendMessage(webSocket);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Received WebSocket response: " + text);  // 输出完整响应内容
        // 处理 WebSocket 返回的数据
        JsonParse responseJson = gson.fromJson(text, JsonParse.class);
        if (responseJson.header.code == 0) {
            List<Text> texts = responseJson.payload.choices.text;

            // 累加每个分段响应的内容
            for (Text t : texts) {
                responseBuilder.append(t.content);
            }

            // 检查是否是最后一段响应
            if (responseJson.payload.choices.status == 2) {
                responseFuture.complete(responseBuilder.toString());  // 返回完整内容
            }
        } else {
            responseFuture.complete("Error Code: " + responseJson.header.code);
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        responseFuture.complete("WebSocket failed: " + t.getMessage());
    }

    private void sendMessage(WebSocket webSocket) {
        JSONObject requestJson = new JSONObject();

        // 构建 header 部分
        JSONObject header = new JSONObject();
        header.put("app_id", APP_ID);
        header.put("uid", UUID.randomUUID().toString().substring(0, 10));
        requestJson.put("header", header);

        // 构建 parameter 部分，设置 domain 参数
        JSONObject parameter = new JSONObject();
        JSONObject chat = new JSONObject();
        chat.put("domain", "lite"); // 设置 domain，您可以根据需要更改
        chat.put("temperature", 0.5); // 温度参数，控制回复的随机性
        chat.put("max_tokens", 4096); // 最大生成的 token 数量
        parameter.put("chat", chat);
        requestJson.put("parameter", parameter);

        // 构建 payload 部分
        JSONObject payload = new JSONObject();
        JSONObject message = new JSONObject();
        JSONArray text = new JSONArray();

        // 将对话历史记录添加到请求中
        if (!historyList.isEmpty()) {
            for (RoleContent roleContent : historyList) {
                text.add(JSON.toJSON(roleContent));
            }
        }

        // 使用实际的问题而不是硬编码内容
        RoleContent roleContent = historyList.get(historyList.size() - 1); // 获取用户的最新问题
        text.add(JSON.toJSON(roleContent));
        message.put("text", text);
        payload.put("message", message);

        requestJson.put("payload", payload);

        // 将构建好的 JSON 发送到 WebSocket
        webSocket.send(requestJson.toString());
    }


    // The rest of the methods (getAuthUrl, RoleContent, etc.) would remain similar to the original code.
    // 鉴权方法
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }
    //返回的json结果拆解
    class JsonParse {
        Header header;
        Payload payload;
    }

    class Header {
        int code;
        int status;
        String sid;
    }

    class Payload {
        Choices choices;
    }

    class Choices {
        int status; // 在这里定义 status 字段
        List<Text> text;
    }

    class Text {
        String role;
        String content;
    }
    class RoleContent{
        String role;
        String content;

        public RoleContent(String assistant, String content) {
            this.role = assistant;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

