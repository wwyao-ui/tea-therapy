#初始化分类器并加载特征词
import json
import os
import ahocorasick
class QuestionClassifier:

    def __init__(self, config_path='D:\\ahomework\\tea-therapy\\scripts\\config.json'):
        with open(config_path, 'r', encoding='utf-8') as f:
            config = json.load(f)

        # 动态加载特征路径和问句关键词
        self.feature_paths = config['feature_paths']
        self.question_types = config['question_types']

        # 加载特征词
        self.region_words = set()
        for label, path in self.feature_paths.items():
            with open(path, encoding='utf-8') as file:
                words = [line.strip() for line in file if line.strip()]
                setattr(self, label, words)
                self.region_words.update(words)

        # 构建自动机
        self.region_tree = self.build_actree(list(self.region_words))
        # 构建词典
        self.wdtype_dict = self.build_wdtype_dict()

        # 定义问句疑问词
        self.include_qwds=['下','有什么','包含','包括']
        self.belong_to_qwds = ['属于','什么茶系','茶系','什么种类','种类','什么品种','品种','什么类别','类别']
        self.description_qwds = [
            '介绍', '描述', '特点', '什么是', '详细信息', '详细介绍', '是什么', '属于哪类', '基本信息', '特色'
        ]

        self.effect_qwds = [
            '功效', '效果', '作用', '好处', '益处', '能带来什么', '对身体有什么好处', '有什么作用', '对健康的帮助',
            '能治什么', '能预防什么'
        ]

        self.group_qwds = [
            '人群','适合人群', '适宜人群', '适合什么人', '哪些人适合', '哪些人群', '什么人可以喝', '适用人群', '适合哪类人群',
            '谁适合', '谁可以喝', '适合的对象'
        ]

        self.constitution_qwds = [
            '适合体质', '体质', '什么体质适合', '适合什么体质', '适宜体质', '体质分类', '对哪些体质好', '体质适应性',
            '什么体质的人', '适合哪种体质'
        ]

        self.origin_qwds = [
            '产地', '哪里生产', '哪里出产', '产自哪里', '生产地', '产地来源', '哪里有', '哪个省', '哪个市', '地理来源',
            '主产区', '主产地'
        ]

    #构建问题分类逻辑
    #按照不同的问句疑问词和实体类型来确定问题的类型。
    # 根据用户的提问中是否包含这些关键词，判定问题类型
    #解释不同问题类型
    #茶叶描述：tea_description，用于回答用户询问某种茶叶的基本介绍。
    # 茶叶功效：tea_effect，用于回答某种茶叶的功效或对健康的作用。
    # 适合人群：tea_suitable_group，用于回答某种茶叶适合哪些人群。
    # 适合体质：tea_suitable_constitution，用于回答某种茶叶适合什么体质的人。
    # 产地：tea_origin，用于回答某种茶叶的产地信息
    def classify(self, question):
        data = {}
        tea_dict = self.check_tea(question)
        if not tea_dict:
            return {}
        data['args'] = tea_dict
        types = []
        for type_ in tea_dict.values():
            types += type_
        question_type = 'others'
        question_types = []

        # 茶叶描述
        if self.check_words(self.description_qwds, question) and ('tea_name' in types or 'tea_category' in types):
            question_type = 'tea_description'
            question_types.append(question_type)

        if self.check_words(self.include_qwds, question) and ( 'tea_category' in types):
            question_type = 'category_tea'
            question_types.append(question_type)

        if self.check_words(self.belong_to_qwds, question) and ('tea_name' in types):
            question_type = 'tea_category'
            question_types.append(question_type)

        # 当问题包含茶的名称并且询问其功效时，进入 tea_effect
        if self.check_words(self.effect_qwds, question) and ('tea_name' in types or 'tea_category' in types):
            question_type = 'tea_effect'
            question_types.append(question_type)
        # 当问题涉及特定功效时，进入 effect_tea
        if 'effect' in types:
            question_type = 'effect_tea'
            question_types.append(question_type)

        # 当问题包含茶的名称并且询问其适合人群时，进入 tea_suitable_group
        if self.check_words(self.group_qwds, question) and ('tea_name' in types or 'tea_category' in types):
            question_type = 'tea_suitable_group'
            question_types.append(question_type)
        # 当问题涉及特定功效时，进入 tea_suitable_group
        if 'group' in types:
            question_type = 'group_suitable_tea'
            question_types.append(question_type)


        # 当问题包含茶的名称并且询问其适合体质时，进入 tea_suitable_constitution
        if self.check_words(self.constitution_qwds, question) and ('tea_name' in types or 'tea_category' in types):
            question_type = 'tea_suitable_constitution'
            question_types.append(question_type)
        # 当问题涉及特定体质时，进入 constitution_suitable_tea
        if 'constitution' in types:
            question_type = 'constitution_suitable_tea'
            question_types.append(question_type)

        # 当问题包含茶的名称并且询问其产地时，进入 tea_origin
        if self.check_words(self.origin_qwds, question) and ('tea_name' in types or 'tea_category' in types):
            question_type = 'tea_origin'
            question_types.append(question_type)
        # 当问题涉及特定产地时
        if 'origin' in types:
            question_type = 'origin_tea'
            question_types.append(question_type)

        # 默认描述信息
        if question_types == [] and ('tea_name' in types or 'tea_category' in types):
            question_types = ['tea_description']

        data['question_types'] = question_types
        return data
    #构建词典和问句过滤
    # 在 build_wdtype_dict 中，
    # 将不同的茶叶实体（茶叶大类、功效、人群、体质、产地等）映射为其类型。
    # 在 check_tea 中使用 actree（Aho-Corasick 自动机）->
    # ->来快速定位问句中的关键词，识别出提问中的实体和特征词，以帮助判断问题类型。
    '''构造词对应的类型'''

    def build_wdtype_dict(self):
        wd_dict = dict()
        for wd in self.region_words:
            wd_dict[wd] = []
            if wd in self.tea_name:
                wd_dict[wd].append('tea_name')
            if wd in self.tea_category:
                wd_dict[wd].append('tea_category')
            if wd in self.effect:
                wd_dict[wd].append('effect')
            if wd in self.group:
                wd_dict[wd].append('group')
            if wd in self.constitution:
                wd_dict[wd].append('constitution')
            if wd in self.origin:
                wd_dict[wd].append('origin')
        return wd_dict

    '''基于特征词进行分类'''
    def check_words(self, wds, sent):
        for wd in wds:
            if wd in sent:
                return True
        return False
# 构建 Aho-Corasick 自动机
    def build_actree(self, word_list):
        actree = ahocorasick.Automaton()
        for index, word in enumerate(word_list):
            actree.add_word(word, (index, word))

        actree.make_automaton()
        return actree

    # 其他方法（保持不变）
    def check_tea(self, question):
        region_wds = []
        for i in self.region_tree.iter(question):
            wd = i[1][1]
            region_wds.append(wd)
        stop_wds = []
        for wd1 in region_wds:
            for wd2 in region_wds:
                if wd1 in wd2 and wd1 != wd2:
                    stop_wds.append(wd1)
        final_wds = [i for i in region_wds if i not in stop_wds]
        final_dict = {i:self.wdtype_dict.get(i) for i in final_wds}

        return final_dict
