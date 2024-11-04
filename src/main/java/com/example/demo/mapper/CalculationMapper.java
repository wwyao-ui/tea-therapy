package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Calculation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author he
 * @since 2024-07-10
 */
@Mapper
@Component
public interface CalculationMapper extends BaseMapper<Calculation> {

    @Insert("INSERT INTO Calculation(questionnaireid,lscore,uid)" +
            "VALUES(#{questionnaireid}, #{lscore},#{uid})")
    int insertlscore(@Param("questionnaireid")String questionnaireid, @Param("lscore")Integer lscore, @Param("uid") Integer uid);

    @Select("SELECT lscore FROM Calculation WHERE uid = #{uid} Order by questionnaireid")
    List<Integer> selectLscoreByUid(Integer uid);


}
