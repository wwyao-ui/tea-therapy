# import json
# import os
# import ahocorasick
# import re
#
# class QuestionClassifier:
#     def __init__(self, config_path='./dict/config.json'):
#         with open(config_path, 'r', encoding='utf-8') as f:
#             config = json.load(f)
#
#         # 动态加载特征路径和问句关键词
#         self.feature_paths = config['feature_paths']
#         self.question_types = config['question_types']
#
#         # 加载特征词
#         self.region_words = set()
#         for label, path in self.feature_paths.items():
#             with open(path, encoding='utf-8') as file:
#                 words = [line.strip() for line in file if line.strip()]
#                 setattr(self, label, words)
#                 self.region_words.update(words)
#
#         # 构建自动机
#         self.region_tree = self.build_actree(list(self.region_words))
#
#         # 构建词典
#         self.wdtype_dict = self.build_wdtype_dict()
#
#         # 定义问句疑问词
#         self.description_qwds = [
#             '介绍', '描述', '特点', '什么是', '详细信息', '详细介绍', '是什么', '属于哪类', '基本信息', '特色'
#         ]
#
#         self.effect_qwds = [
#             '功效', '效果', '作用', '好处', '益处', '能带来什么', '对身体有什么好处', '有什么作用', '对健康的帮助',
#             '能治什么', '能预防什么'
#         ]
#
#         self.group_qwds = [
#             '人群', '适合人群', '适宜人群', '适合什么人', '哪些人适合', '哪些人群', '什么人可以喝', '适用人群', '适合哪类人群',
#             '谁适合', '谁可以喝', '适合的对象'
#         ]
#
#         self.constitution_qwds = [
#             '适合体质', '体质', '什么体质适合', '适合什么体质', '适宜体质', '体质分类', '对哪些体质好', '体质适应性',
#             '什么体质的人', '适合哪种体质'
#         ]
#
#         self.origin_qwds = [
#             '产地', '哪里生产', '哪里出产', '产自哪里', '生产地', '产地来源', '哪里有', '哪个省', '哪个市', '地理来源',
#             '主产区', '主产地'
#         ]
#
#         print('model init finished ......')
#
#     def classify(self, question):
#         data = {}
#         tea_dict = self.check_tea(question)
#         print("调试信息 - tea_dict:", tea_dict)  # 调试输出
#         if not tea_dict:
#             return {}
#         data['args'] = tea_dict
#
#         types = []
#         for type_ in tea_dict.values():
#             types += type_
#
#         question_types = []
#
#         # 判断是否为判断句
#         if self.is_judgment_question(question, tea_dict):
#             question_types.append("judgment")
#
#         # 茶叶描述
#         if self.check_words(self.description_qwds, question) and ('tea_name' in types or 'tea_category' in types):
#             question_types.append('tea_description')
#
#         # 茶叶功效
#         if self.check_words(self.effect_qwds, question) and ('tea_name' in types or 'tea_category' in types):
#             question_types.append('tea_effect')
#
#         # 特定功效的茶
#         if self.check_words(self.effect_qwds, question) and ('effect' in types):
#             question_types.append('effect_tea')
#
#         # 茶适合人群
#         if self.check_words(self.group_qwds, question) and ('tea_name' in types or 'tea_category' in types):
#             question_types.append('tea_suitable_group')
#
#         if self.check_words(self.group_qwds, question) and ('group' in types):
#             question_types.append('tea_suitable_group')
#
#         # 茶适合体质
#         if self.check_words(self.constitution_qwds, question) and ('tea_name' in types or 'tea_category' in types):
#             question_types.append('tea_suitable_constitution')
#
#         if self.check_words(self.constitution_qwds, question) and ('constitution' in types):
#             question_types.append('constitution_suitable_tea')
#
#         # 茶产地
#         if self.check_words(self.origin_qwds, question) and ('tea_name' in types or 'tea_category' in types):
#             question_types.append('tea_origin')
#
#         if self.check_words(self.origin_qwds, question) and ('origin' in types):
#             question_types.append('origin_tea')
#
#         # 默认描述信息
#         if not question_types and 'tea' in types:
#             question_types.append('tea_description')
#
#         data['question_types'] = question_types
#         return data
#
#     def is_judgment_question(self, question, tea_dict):
#         """使用正则表达式和特征词组合识别判断问句，并支持更多实体组合"""
#         judgment_keywords = r"(是|是否|能|可以|具有|能否|能不能|是不是|会不会)"
#
#         # 动态获取包含的实体类型
#         entities_in_question = {key for key, values in tea_dict.items() if values}
#
#         # 判断组合模式，基于实体组合设定查询类型
#         if 'tea_name' in entities_in_question and 'effect' in entities_in_question:
#             pattern = rf"{judgment_keywords}.*({self.effect_keywords_regex()})|({self.effect_keywords_regex()}).*{judgment_keywords}"
#         elif 'tea_name' in entities_in_question and 'origin' in entities_in_question:
#             pattern = rf"{judgment_keywords}.*(产地|来自|来源)|来自.*{judgment_keywords}"
#         elif 'tea_name' in entities_in_question and 'group' in entities_in_question:
#             pattern = rf"{judgment_keywords}.*(适合|适宜|适用于)|适合.*{judgment_keywords}"
#         elif 'tea_category' in entities_in_question and 'constitution' in entities_in_question:
#             pattern = rf"{judgment_keywords}.*(体质|体格|适应性)|适应.*{judgment_keywords}"
#         else:
#             return False
#
#         return bool(re.search(pattern, question))
#
#     def effect_keywords_regex(self):
#         """生成用于匹配功效的正则表达式"""
#         return '|'.join([re.escape(word) for word in self.effects])
#
#     def build_wdtype_dict(self):
#         wd_dict = dict()
#         for wd in self.region_words:
#             wd_dict[wd] = []
#             if wd in self.tea_name:
#                 wd_dict[wd].append('tea_name')
#             if wd in self.tea_category:
#                 wd_dict[wd].append('tea_category')
#             if wd in self.effect:
#                 wd_dict[wd].append('effect')
#             if wd in self.group:
#                 wd_dict[wd].append('group')
#             if wd in self.constitution:
#                 wd_dict[wd].append('constitution')
#             if wd in self.origin:
#                 wd_dict[wd].append('origin')
#         return wd_dict
#
#     def build_actree(self, word_list):
#         actree = ahocorasick.Automaton()
#         for index, word in enumerate(word_list):
#             actree.add_word(word, (index, word))
#         actree.make_automaton()
#         return actree
#
#     def check_tea(self, question):
#         region_wds = [i[1][1] for i in self.region_tree.iter(question)]
#         final_dict = {i: self.wdtype_dict.get(i) for i in region_wds}
#         return final_dict if final_dict else None
#
#     def check_words(self, wds, sent):
#         for wd in wds:
#             if wd in sent:
#                 return True
#         return False
#
#
# # 运行测试
# if __name__ == "__main__":
#     classifier = QuestionClassifier()
#     print("请输入一个句子进行分类 (输入 'exit' 退出)：")
#     while True:
#         question = input("用户: ")
#         if question.lower() == "exit":
#             break
#         result = classifier.classify(question)
#         print("分类结果:", result['question_types'])
