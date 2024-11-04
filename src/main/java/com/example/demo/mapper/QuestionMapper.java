package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author he
 * @since 2024-07-09
 */
@Mapper
@Component
public interface QuestionMapper extends BaseMapper<Question> {
    @Select("SELECT * FROM question order by questionnaireid")
    List<Question> selectAllQuestion();
}
