package com.example.demo.mapper;

import com.example.demo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM black_tea")
    List<Comment> getBlackComments();

    @Select("SELECT * FROM red_tea")
    List<Comment> getRedComments();

    @Select("SELECT * FROM green_tea")
    List<Comment> getGreenComments();

    @Select("SELECT * FROM white_tea")
    List<Comment> getWhiteComments();

    @Select("SELECT * FROM yellow_tea")
    List<Comment> getYellowComments();

    @Select("SELECT * FROM wulong_tea")
    List<Comment> getWulongComments();
}
