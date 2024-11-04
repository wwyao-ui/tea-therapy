package com.example.demo.controller;


import com.example.demo.common.ResultUtil;
import com.example.demo.entity.Question;
import com.example.demo.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author he
 * @since 2024-07-09
 */
@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionMapper questionMapper;
    @RequestMapping("select")
    @ResponseBody
    public ResultUtil select(){
        List<Question> questions = questionMapper.selectAllQuestion();
        return ResultUtil.ok(questions);
    }
}
