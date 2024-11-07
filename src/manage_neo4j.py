# -*- coding: utf-8 -*-

import pandas as pd
from py2neo import Graph, Node, Relationship

class Neo4jManager:
    def __init__(self):
        # 连接图数据库
        URI = "bolt://localhost:7687"
        USERNAME = "neo4j"
        PASSWORD = "wmy031230"  # 修改为您的密码
        self.graph = Graph(URI, auth=(USERNAME, PASSWORD))

    def add_node(self, label, **properties):
        """增加节点"""
        node = Node(label, **properties)
        self.graph.merge(node, label, "name")

    def update_node(self, label, node_name, **new_properties):
        """更新节点属性"""
        node = self.graph.nodes.match(label, name=node_name).first()
        for key, value in new_properties.items():
            node[key] = value
            self.graph.push(node)

    def delete_node(self, label, node_name):
        """删除节点"""
        node = self.graph.nodes.match(label, name=node_name).first()
        self.graph.delete(node)


    def add_relationship(self, node1_label, node1_name, relationship_type, node2_label, node2_name):
        """添加关系"""
        node1 = self.graph.nodes.match(node1_label, name=node1_name).first()
        node2 = self.graph.nodes.match(node2_label, name=node2_name).first()
        if node1 and node2:
            rel = Relationship(node1, relationship_type, node2)
            self.graph.merge(rel)
    def delete_relationship(self, node1_label, node1_name, relationship_type, node2_label, node2_name):
        """删除关系"""
        node1 = self.graph.nodes.match(node1_label, name=node1_name).first()
        node2 = self.graph.nodes.match(node2_label, name=node2_name).first()
        if node1 and node2:
            relationship = self.graph.match((node1, node2), r_type=relationship_type).first()
            if relationship:
                self.graph.separate(relationship)

    def query_node(self, label, node_name):
        """查询节点信息"""
        node = self.graph.nodes.match(label, name=node_name).first()

    def query_relationship(self, node1_label, node1_name, relationship_type, node2_label, node2_name):
        """查询关系"""
        node1 = self.graph.nodes.match(node1_label, name=node1_name).first()
        node2 = self.graph.nodes.match(node2_label, name=node2_name).first()
        if node1 and node2:
            relationship = self.graph.match((node1, node2), r_type=relationship_type).first()

if __name__ == '__main__':
    manager = Neo4jManager()

    # 示例用法
    # 添加一个节点
    manager.add_node("TeaType", name="新茶叶", origin="福建", effect="清热解毒", suitable_group="学生", avoid_group="孕妇")

    # 更新节点属性
    manager.update_node("TeaType", "新茶叶", effect="提神", suitable_group="学生")

    # 删除一个节点
    manager.delete_node("TeaType", "新茶叶")

    # 添加关系
    manager.add_relationship("TeaType", "西湖龙井", "HAS_SUITABLE", "Group", "办公族")

    # 删除关系
    manager.delete_relationship("TeaType", "西湖龙井", "HAS_SUITABLE", "Group", "办公族")

    # 查询节点信息
    manager.query_node("TeaType", "西湖龙井")

    # 查询关系信息
    manager.query_relationship("TeaType", "西湖龙井", "HAS_SUITABLE", "Group", "办公族")
