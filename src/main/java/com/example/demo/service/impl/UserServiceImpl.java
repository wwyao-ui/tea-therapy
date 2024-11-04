package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.ResultUtil;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author he
 * @since 2024-07-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public ResultUtil register(User user) {
        save(user);
        return ResultUtil.ok();
    }
    @Override
    public ResultUtil UserPage(Integer pageNum, Integer pageSize) {
        Page<User> page = page(new Page<>(pageNum, pageSize));
        return ResultUtil.ok(page);
    }
}
