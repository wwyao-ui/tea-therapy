package com.example.demo.service;

import com.example.demo.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getBlackComments();
    List<Comment> getRedComments();
    List<Comment> getGreenComments();
    List<Comment> getWhiteComments();
    List<Comment> getYellowComments();
    List<Comment> getWulongComments();
}
