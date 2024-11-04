package com.example.demo.mapper;

import com.example.demo.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author he
 * @since 2024-07-06
 */
@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {
    @Select("SELECT * FROM Message ORDER BY id DESC")
    List<Message> selectAllMessage();
    /*
     * 新增一条留言信息
     *
     * @param message
     * @return
     */
    @Insert("INSERT INTO Message(id,name,liuyan)" +
            "VALUES(#{id}, #{name}, #{liuyan})")
    int insertMessage(Message message);
    /**
     * 查询前3条记录，按id降序排列
     * @return 前3条记录的List
     */
    @Select("SELECT * FROM Message ORDER BY id DESC LIMIT 3")
    List<Message> selectTopThreeMessages();


}