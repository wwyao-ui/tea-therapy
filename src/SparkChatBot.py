# -*- coding: utf-8 -*-

import SparkApi

class SparkChatBot:
    def __init__(self, appid, api_key, api_secret, spark_url, domain,text):
        self.appid = appid
        self.api_key = api_key
        self.api_secret = api_secret
        self.spark_url = spark_url
        self.domain = domain
        self.text = text

    def get_answer(self, question):
        SparkApi.answer = ""
        # 将用户问题添加到上下文
        self.text.append({"role": "user", "content": question})
        SparkApi.main(self.appid, self.api_key, self.api_secret, self.spark_url, self.domain, self.text)

        # 获取 AI 的回答并添加到上下文
        answer = SparkApi.answer
        self.text.append({"role": "assistant", "content": answer})
        return answer
