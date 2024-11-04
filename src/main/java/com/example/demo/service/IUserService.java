package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.ResultUtil;
import com.example.demo.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author he
 * @since 2024-07-06
 */
public interface IUserService extends IService<User> {
    ResultUtil register(User user);
    ResultUtil UserPage(Integer pageNum, Integer pageSize);
}
