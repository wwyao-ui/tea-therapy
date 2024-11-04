package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.UserAnswers;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author he
 * @since 2024-07-09
 */
@Mapper
public interface UserAnswersMapper extends BaseMapper<UserAnswers> {
    public UserAnswers selectUserAnswersById(String userId);
}
