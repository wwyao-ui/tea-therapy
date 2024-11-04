package com.example.demo.controller;

import com.example.demo.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AnswerController {
    @Autowired
    private AnswerService bigModelService;

    @PostMapping("/ask")
    public String askModel(@RequestParam String question) {
        return bigModelService.askModel(question);
    }
}
