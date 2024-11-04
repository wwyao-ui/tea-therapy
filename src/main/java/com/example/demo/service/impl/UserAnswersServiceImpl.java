package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.UserAnswers;
import com.example.demo.mapper.UserAnswersMapper;
import com.example.demo.service.IUserAnswersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author he
 * @since 2024-07-09
 */
@Service
public class UserAnswersServiceImpl extends ServiceImpl<UserAnswersMapper, UserAnswers> implements IUserAnswersService {

}
