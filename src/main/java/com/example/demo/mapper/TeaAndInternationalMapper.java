package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.TeaAndInternational;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface TeaAndInternationalMapper extends BaseMapper<TeaAndInternational> {
    @Select("SELECT * FROM teaandinternal")
    List<TeaAndInternational> selectAll();
}
