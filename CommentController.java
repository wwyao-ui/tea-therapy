package com.example.demo.controller;

import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping("/getBlackData")
    public Object getBlackData(){
        return commentService.getBlackComments();
    }

    @ResponseBody
    @RequestMapping("/getRedData")
    public Object getRedData(){
        return commentService.getRedComments();
    }

    @ResponseBody
    @RequestMapping("/getGreenData")
    public Object getGreenData(){
        return commentService.getGreenComments();
    }

    @ResponseBody
    @RequestMapping("/getWhiteData")
    public Object getWhiteData(){
        return commentService.getWhiteComments();
    }

    @ResponseBody
    @RequestMapping("/getYellowData")
    public Object getYellowData(){
        return commentService.getYellowComments();
    }

    @ResponseBody
    @RequestMapping("/getWulongData")
    public Object getWulongData(){
        return commentService.getWulongComments();
    }

}
