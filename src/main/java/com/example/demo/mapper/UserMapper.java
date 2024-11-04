package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

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
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询用户信息信息
     */
    public User selectUserinfoById(Integer id);


    /**
     * 查询用户信息列表
     */
    public List<User> selectUserinfoList(User user);

    /**
     * 查询所有用户信息
     */
    public List<User> selectAll(Map map);

    /**
     * 新增用户信息
     */
    public int insertUserinfo(User user);

    /**
     * 修改用户信息

     */
    public int updateUserinfo(User user);

    /**
     * 批量修改
     */
    public int batchUpdate(List<User> list);

    /**
     * 删除用户信息
     */
    public int deleteUserinfoById(Integer id);

    /**
     * 批量删除用户信息
     */
    public int batchDeleteUserinfo(Integer[] ids);

    /**
     * 批量添加

     */
    public int batchAdd(List<User> list);
}
