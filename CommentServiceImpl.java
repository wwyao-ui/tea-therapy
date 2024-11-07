package com.example.demo.service.impl;

import com.example.demo.entity.Comment;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> getBlackComments() {
        return commentMapper.getBlackComments();
    }

    @Override
    public List<Comment> getRedComments() {
        return commentMapper.getRedComments();
    }

    @Override
    public List<Comment> getGreenComments() {
        return commentMapper.getGreenComments();
    }

    @Override
    public List<Comment> getWhiteComments() {
        return commentMapper.getWhiteComments();
    }

    @Override
    public List<Comment> getYellowComments() {
        return commentMapper.getYellowComments();
    }

    @Override
    public List<Comment> getWulongComments() {
        return commentMapper.getWulongComments();
    }
}
