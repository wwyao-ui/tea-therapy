# -*- coding: utf-8 -*-
import sys

import HybridChatBot
from SparkChatBot import SparkChatBot
# 强制设置标准输出和标准错误的编码为 UTF-8
sys.stdout.reconfigure(encoding='utf-8')
sys.stderr.reconfigure(encoding='utf-8')

if __name__ == '__main__':
    # 初始化知识图谱问答系统
    knowledge_bot = HybridChatBot.ChatBotTeaGraph()

    # 初始化星火 API 问答系统
    appid = "63c46200"
    api_key = "c2ff56d45088b249734d46c53b301589"
    api_secret = "Mjg1NjU2NjhiMWUyYzc5Njc4MjdjYzU5"
    spark_url = "wss://spark-api.xf-yun.com/v1.1/chat"  #测试版
    spark_url = "wss://spark-api.xf-yun.com/v4.0/chat"
    domain = "lite"  #测试版
    # domain = "4.0Ultra"
    text = [
        # {"role": "system", "content": "你现在扮演李白，你豪情万丈，狂放不羁；接下来请用李白的口吻和用户对话。"}
    ]
    ai_bot = SparkChatBot(appid, api_key, api_secret, spark_url, domain,text)

    # 初始化综合问答系统
    chatbot = HybridChatBot.HybridChatBot(knowledge_bot, ai_bot)
    if len(sys.argv) > 1:
        question = sys.argv[1]  # 从命令行获取问题参数
        answer = chatbot.get_answer(question)
        print(answer)  # 打印答案以便 Java 可以读取
    else:
        print("未提供问题参数")  # 如果没有提供参数，输出提示信息
