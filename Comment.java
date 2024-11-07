package com.example.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Comment {
    private String word;
    private Integer count;
}
