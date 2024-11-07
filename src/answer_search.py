from py2neo import Graph


class AnswerSearcher:
    def __init__(self):
        # 连接 Neo4j 数据库
        # 使用 URI 和 auth 参数来连接 Neo4j 数据库
        URI = "bolt://localhost:7687"
        USERNAME = "neo4j"
        PASSWORD = "wmy031230"  # 修改为您的密码

        # 将 Graph 实例赋值给 self.g，以便类的其他方法使用
        self.g = Graph(URI, auth=(USERNAME, PASSWORD))
        self.num_limit = 20

    def search_main(self, sqls):
        final_answers = []
        for sql_ in sqls:
            question_type = sql_['question_type']
            queries = sql_['sql']
            answers = []
            for query in queries:
                ress = self.g.run(query).data()
                answers += ress
            final_answer = self.answer_prettify(question_type, answers)
            if final_answer:
                final_answers.append(final_answer)
        return final_answers

    def answer_prettify(self, question_type, answers):
        if not answers:
            return '未找到相关信息。'

        final_answer = ''
        grouped_answers = {}

        # 将答案按茶叶名称分组
        for answer in answers:
            subject = answer['m.name']
            if subject not in grouped_answers:
                grouped_answers[subject] = []
            grouped_answers[subject].append(answer)

        # 根据问题类型生成最终的回答
        for subject, items in grouped_answers.items():
            # if question_type == 'tea_description':
            #     desc = [i['description'] for i in items]
            #     final_answer += '{0}的描述：{1}\n'.format(subject, '；'.join(desc[:self.num_limit]))

            if question_type == 'tea_effect':
                effects = [i['effect'] for i in items]
                final_answer += '{0}的功效包括：{1}\n'.format(subject, '；'.join(effects[:self.num_limit]))

            elif question_type == 'effect_tea':
                tea = [i['tea'] for i in items]
                final_answer += '功效是{0}的茶包括：{1}\n'.format(subject, '；'.join(tea[:self.num_limit]))

            elif question_type == 'tea_suitable_group':
                group = [i['group'] for i in items]
                final_answer += '{0}适合的人群包括：{1}\n'.format(subject, '；'.join(group[:self.num_limit]))

            elif question_type == 'group_suitable_tea':
                tea = [i['tea'] for i in items]
                final_answer += '{0}适合的包括：{1}\n'.format(subject, '；'.join(tea[:self.num_limit]))

            elif question_type == 'tea_suitable_constitution':
                constitution = [i['constitution'] for i in items]
                final_answer += '{0}适合的体质有：{1}\n'.format(subject, '；'.join(constitution[:self.num_limit]))

            elif question_type == 'constitution_suitable_tea':
                tea = [i['good_tea'] for i in items]
                final_answer += '{0}适合的有：{1}\n'.format(subject, '；'.join(tea[:self.num_limit]))
                tea = [i['tea'] for i in items]
                final_answer += '{0}不适合的有：{1}\n'.format(subject, '；'.join(tea[:self.num_limit]))

            elif question_type == 'tea_origin':
                origin = [i['origin'] for i in items]
                final_answer += '{0}的产地包括：{1}\n'.format(subject, '；'.join(origin[:self.num_limit]))

            elif question_type == 'origin_tea':
                tea = [i['tea'] for i in items]
                final_answer += '{0}的茶叶有：{1}\n'.format(subject, '；'.join(tea[:self.num_limit]))

            elif question_type == 'tea_category':
                effects = [i['tea_category'] for i in items]
                final_answer += '{0}的属于：{1}\n'.format(subject, '；'.join(effects[:self.num_limit]))

            elif question_type == 'category_tea':
                tea = [i['tea'] for i in items]
                final_answer += '{0}的包括：{1}\n'.format(subject, '；'.join(tea[:self.num_limit]))

            elif question_type == 'tea_taste':
                tastes = [i['taste'] for i in items]
                final_answer += '{0}的口感描述：{1}\n'.format(subject, '；'.join(tastes[:self.num_limit]))

        return final_answer.strip()  # 去除最后多余的换行符
