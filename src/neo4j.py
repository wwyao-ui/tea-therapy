# -*- coding: utf-8 -*-
import pandas as pd
from py2neo import Graph, Node, Relationship
class Neo4j:
    # 读取csv文件
    tea_type_data = pd.read_csv("D:\\ahomework\\tea-therapy\\scripts\\data\\TeaType.csv")
    category_data = pd.read_csv("D:\\ahomework\\tea-therapy\\scripts\\data\\Category.csv")
    effect_data = pd.read_csv("D:\\ahomework\\tea-therapy\\scripts\\data\\Effect.csv")
    group_data = pd.read_csv("D:\\ahomework\\tea-therapy\\scripts\\data\\Group.csv")
    constitution_data = pd.read_csv("D:\\ahomework\\tea-therapy\\scripts\\data\\Constitution.csv")
    origin_data=pd.read_csv("D:\\ahomework\\tea-therapy\\scripts\\data\\Origin.csv")

    # 连接图数据库
    try:
        # 连接图数据库的信息
        URI = "bolt://localhost:7687"
        USERNAME = "neo4j"
        PASSWORD = "wmy031230"  # 修改为你的密码
        graph = Graph(URI, auth=(USERNAME, PASSWORD))

        # 清除之前数据
        graph.delete_all()

        # 创建地区实体
        for index, row in origin_data.iterrows():
            origin_node = Node(
                "Origin",
                name=row['Province'] # Assuming 'gname' column is present in Group.csv
            )
            graph.merge(origin_node, "Origin", "name")

        # 创建体质实体
        for index, row in constitution_data.iterrows():
            constitution_node = Node(
                "Constitution",
                name=row['name']  # Assuming 'coname' column is present in Constitution.csv
            )
            graph.merge(constitution_node, "Constitution", "name")

        # 创建人群实体
        for index, row in group_data.iterrows():
            group_node = Node(
                "Group",
                name=row['name']  # Assuming 'gname' column is present in Group.csv
            )
            graph.merge(group_node, "Group", "name")

        # 创建功效实体
        for index, row in effect_data.iterrows():
            effect_node = Node(
                "Effect",
                name=row['name']  # Assuming 'name' column is present in Effect.csv
            )
            graph.merge(effect_node, "Effect", "name")

        # 创建茶叶类别实体，具有名称，功效，适合体质，不适合体质等属性
        for index, row in category_data.iterrows():
            category_node = Node(
                "Category",
                name=row['name'],  # Assuming 'cname' column is present in Category.csv
                suitable=row.get('suitable', ''),
                avoid=row.get('avoid', ''),
                effect=row.get('effect', ''),
                suitable_group=row.get('suitable_group', ''),
                avoid_group=row.get('avoid_group', '')
            )
            graph.merge(category_node, "Category", "name")

            # 建立茶类别与体质的联系，例如<绿茶 适合 平和质>
            if pd.notna(row['suitable']):
                suitables = row['suitable'].split('、')
                for suitable in suitables:
                    suitable = suitable.strip()
                    suitable_node = graph.nodes.match("Constitution", name=suitable).first()
                    if suitable_node:
                        has_constitution_rel = Relationship(category_node, "适合", suitable_node)
                        has_constitution = Relationship(suitable_node, "适饮", category_node)
                        graph.merge(has_constitution_rel)
                        graph.merge(has_constitution)

            # 建立茶类别与体质的联系，例如<红茶 不适合 平和质>
            if pd.notna(row['avoid']):
                avoids = row['avoid'].split('、')
                for avoid in avoids:
                    avoid = avoid.strip()
                    avoid_node = graph.nodes.match("Constitution", name=avoid).first()
                    if avoid_node:
                        has_constitution_rel = Relationship(category_node, "不适合", avoid_node)
                        has_constitution = Relationship(avoid_node, "不适饮", category_node)
                        graph.merge(has_constitution_rel)
                        graph.merge(has_constitution)

            # 建立茶类别与功效的联系，例如<绿茶 具有功效 提神醒脑>
            if pd.notna(row['effect']):
                effects = row['effect'].split('、')
                for effect in effects:
                    effect = effect.strip()
                    effect_node = graph.nodes.match("Effect", name=effect).first()
                    if effect_node:
                        has_effect_rel = Relationship(category_node, "具有功效", effect_node)
                        has_effect = Relationship(effect_node, "适饮", category_node)
                        graph.merge(has_effect_rel)
                        graph.merge(has_effect)
        # 创建茶的实体类，具有姓名，产地，功效，适宜人群，禁忌人群，类别的属性
        for index, row in tea_type_data.iterrows():
            tea_node = Node(
                "TeaType",
                name=row['name'],
                origin=row.get('origin', ''),
                effect=row.get('effect', ''),
                suitable_group=row.get('suitable_group', ''),
                avoid_group=row.get('avoid_group', ''),
                category=row.get('category', '')
            )
            graph.merge(tea_node, "TeaType", "name")

            # 创建茶与产地的关系，例如<云南 特产 普洱茶>
            if pd.notna(row['origin']):
                origin_name = row['origin']
                origin_node = graph.nodes.match("Origin", name=origin_name).first()
                if origin_node:
                    belongs_to_rel = Relationship(origin_node, "特产", tea_node)
                    belongs_to = Relationship(tea_node, "产自于", origin_node)
                    graph.merge(belongs_to_rel)
                    graph.merge(belongs_to)

            # 创建茶与类别的"包含"关系
            if pd.notna(row['category']):
                category_name = row['category']
                category_node = graph.nodes.match("Category", name=category_name).first()
                if category_node:
                    contains_rel = Relationship(category_node, "包含", tea_node)
                    contains = Relationship(tea_node, "属于", category_node)
                    graph.merge(contains_rel)
                    graph.merge(contains)

            # 创建茶与功效的关系，例如<西湖龙井 具有功效 提神醒脑>
            if pd.notna(row['effect']):
                effects = row['effect'].split('、')
                for effect in effects:
                    effect = effect.strip()
                    effect_node = graph.nodes.match("Effect", name=effect).first()
                    if effect_node:
                        has_effect_rel = Relationship(tea_node, "具有功效", effect_node)
                        has_effect = Relationship(effect_node, "适饮", tea_node)
                        graph.merge(has_effect_rel)
                        graph.merge(has_effect)

            # 创建茶与适宜人群的关系，例如<龙井茶 适宜 办公族>
            if pd.notna(row['suitable_group']):
                groups = row['suitable_group'].split('、')
                for group in groups:
                    group = group.strip()
                    group_node = graph.nodes.match("Group", name=group).first()
                    if group_node:
                        suitable_for_rel = Relationship(tea_node, "适宜", group_node)
                        suitable_for = Relationship(group_node, "适饮", tea_node)
                        graph.merge(suitable_for_rel)
                        graph.merge(suitable_for)

            # 创建茶与禁忌人群的关系，例如<红茶 禁忌 胃寒人群>
            if pd.notna(row['avoid_group']):
                groups = row['avoid_group'].split('、')
                for group in groups:
                    group = group.strip()
                    group_node = graph.nodes.match("Group", name=group).first()
                    if group_node:
                        avoided_by_rel = Relationship(tea_node, "禁忌", group_node)
                        avoided_by = Relationship(group_node, "不适饮", tea_node)
                        graph.merge(avoided_by_rel)
                        graph.merge(avoided_by)

        print("Knowledge graph successfully created with tea information and relationships.")
    except KeyError as e:
        print(f"An error occurred: Missing expected column - {e}")
if __name__ == '__main__':
    handler = Neo4j()
