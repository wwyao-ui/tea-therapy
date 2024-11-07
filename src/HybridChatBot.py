# -*- coding: utf-8 -*-

from answer_search import AnswerSearcher
from question_classifier import QuestionClassifier
from question_parser import QuestionParser


class ChatBotTeaGraph:
    def __init__(self):
        self.classifier = QuestionClassifier()
        self.parser = QuestionParser()
        self.searcher = AnswerSearcher()

    def get_answer(self, question):
        # 默认回答
        answer = '您好，我是您的茶叶知识助理，希望可以帮到您！如果问题未能解答，请联系专业人士。'

        # 第一步：分类问题
        res_classify = self.classifier.classify(question)
        if not res_classify:
            return None  # 返回 None 表示未能分类

        # 第二步：解析问题
        res_sql = self.parser.parser_main(res_classify)

        # 第三步：搜索答案
        final_answers = self.searcher.search_main(res_sql)
        if final_answers:
            return '\n'.join(final_answers)
        else:
            return None  # 返回 None 表示未找到答案
class HybridChatBot:
    def __init__(self, knowledge_bot, ai_bot):
        self.knowledge_bot = knowledge_bot
        self.ai_bot = ai_bot

    def get_answer(self, question):
        # 先尝试从知识图谱获取答案
        answer = self.knowledge_bot.get_answer(question)
        if answer:  # 如果知识图谱返回了有效答案
            return answer
        else:
            # 如果知识图谱无答案，调用星火 API 进行回答
            return self.ai_bot.get_answer(question)

