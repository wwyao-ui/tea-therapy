package com.example.demo.mapper;

import com.example.demo.entity.Tea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author he
 * @since 2024-07-11
 */
@Component
public interface TeaMapper extends BaseMapper<Tea> {
    @Select("SELECT* FROM Tea WHERE type = #{type}")
    List<Tea> selectTeaBytype(String type);
}
