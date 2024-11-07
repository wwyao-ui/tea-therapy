class QuestionParser:
    '''构建实体节点'''

    def build_entitydict(self, args):
        entity_dict = {}
        for arg, types in args.items():
            for type in types:
                if type not in entity_dict:
                    entity_dict[type] = [arg]
                else:
                    entity_dict[type].append(arg)

        return entity_dict

    '''解析主函数'''

    def parser_main(self, res_classify):
        args = res_classify['args']
        entity_dict = self.build_entitydict(args)
        question_types = res_classify['question_types']
        sqls = []
        for question_type in question_types:
            sql_ = {}
            sql_['question_type'] = question_type
            sql = []
            if question_type == 'tea_description':
                sql = self.sql_transfer(question_type, entity_dict.get('tea_name') or entity_dict.get('tea_category'))

            elif question_type == 'tea_effect':
                sql = self.sql_transfer(question_type, entity_dict.get('tea_name') or entity_dict.get('tea_category'))

            elif question_type == 'effect_tea':
                sql = self.sql_transfer(question_type, entity_dict.get('effect'))

            elif question_type == 'tea_suitable_group':
                sql = self.sql_transfer(question_type, entity_dict.get('tea_name') or entity_dict.get('tea_category'))

            elif question_type == 'group_suitable_tea':
                sql = self.sql_transfer(question_type, entity_dict.get('group'))

            elif question_type == 'tea_suitable_constitution':
                sql = self.sql_transfer(question_type, entity_dict.get('tea_name') or entity_dict.get('tea_category'))

            elif question_type == 'constitution_suitable_tea':
                sql = self.sql_transfer(question_type, entity_dict.get('constitution'))



            elif question_type == 'tea_origin':
                sql = self.sql_transfer(question_type, entity_dict.get('tea_name') or entity_dict.get('tea_category'))

            elif question_type == 'origin_tea':
                sql = self.sql_transfer(question_type, entity_dict.get('origin'))

            elif question_type == 'tea_category':
                sql = self.sql_transfer(question_type, entity_dict.get('tea_name') or entity_dict.get('tea_category'))

            elif question_type == 'category_tea':
                sql = self.sql_transfer(question_type, entity_dict.get('tea_category'))

            if sql:
                sql_['sql'] = sql
                sqls.append(sql_)
        return sqls

    '''针对不同的问题，分开进行处理'''

    def sql_transfer(self, question_type, entities):
        if not entities:
            return []

        # 查询语句
        sql = []

        # 定义每种查询类型的模板
        query_templates = {
            'tea_description': ["MATCH (m:TeaType) WHERE m.name = '{0}' RETURN m.name, m.description",
                                "MATCH (m:Category) WHERE m.name = '{0}' RETURN m.name, m.description"
                                ],

            'tea_effect': ["MATCH (m:TeaType)-[r:具有功效]->(n:Effect) WHERE m.name = '{0}' RETURN m.name, n.name AS effect",
                           "MATCH (m:Category)-[r:具有功效]->(n:Effect) WHERE m.name = '{0}' RETURN m.name, n.name AS effect"],
            'effect_tea': ["MATCH (m:Effect)<-[r:具有功效]-(n:TeaType) WHERE m.name = '{0}' RETURN m.name, n.name AS tea",
                           "MATCH (m:Effect)<-[r:具有功效]-(n:Category) WHERE m.name = '{0}' RETURN m.name, n.name AS tea"],

            'tea_suitable_group': ["MATCH (m:TeaType)-[r:适宜]->(n:Group) WHERE m.name = '{0}' RETURN m.name, n.name AS group",
                                   "MATCH (m:Category)-[r:适宜]->(n:Group) WHERE m.name = '{0}' RETURN m.name, n.name AS group"],
            'group_suitable_tea': ["MATCH (m:Group)<-[r:适宜]-(n:TeaType) WHERE m.name = '{0}' RETURN m.name, n.name AS tea",
                                   "MATCH (m:Group)<-[r:适宜]-(n:Category) WHERE m.name = '{0}' RETURN m.name, n.name AS tea"],

            'tea_suitable_constitution': [
                "MATCH (m:Category)-[r:适合]->(n:Constitution) WHERE m.name = '{0}' RETURN m.name, n.name AS constitution",
                "MATCH (m:Category)-[r:不适合]->(n:Constitution) WHERE m.name = '{0}' RETURN m.name, n.name AS constitution"
            ],
            'constitution_suitable_tea': [
                "MATCH (m:Constitution)-[r:适饮]->(n:Category) WHERE m.name = '{0}' RETURN m.name, n.name AS good_tea",
                "MATCH (m:Constitution)-[r:不适饮]->(n:Category) WHERE m.name = '{0}' RETURN m.name, n.name AS tea"
            ],

            'tea_origin': "MATCH (m:TeaType)-[r:产自于]->(n:Origin) WHERE m.name = '{0}' RETURN m.name, n.name AS origin",
            'origin_tea': "MATCH (m:Origin)<-[r:产自于]-(n:TeaType) WHERE m.name = '{0}' RETURN m.name, n.name AS tea",

            'tea_category': "MATCH (m:TeaType)-[r:属于]->(n:Category) WHERE m.name = '{0}' RETURN m.name, n.name AS tea_category",
            'category_tea': "MATCH (m:Category)<-[r:属于]-(n:TeaType) WHERE m.name = '{0}' RETURN m.name, n.name AS tea"
        }

        # 使用模板生成 SQL 语句
        sql_template = query_templates.get(question_type)
        if sql_template:
            # 检查模板是否为列表
            if isinstance(sql_template, list):
                for template in sql_template:
                    sql.extend([template.format(entity) for entity in entities])
            else:
                sql = [sql_template.format(entity) for entity in entities]

        return sql
if __name__ == '__main__':
    handler = QuestionParser()
