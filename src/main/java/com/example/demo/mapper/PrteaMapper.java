package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Prtea;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
public interface PrteaMapper extends BaseMapper<Prtea> {
    @Update("UPDATE prtea SET rating = 5")
    void insertTea();

    @Update("UPDATE prtea SET rating = rating + 1 WHERE id = #{id}")
    void updateTeaRating(int id);

    @Select("SELECT * FROM prtea ORDER BY rating DESC")
    List<Prtea> selectAllTeas();
}
