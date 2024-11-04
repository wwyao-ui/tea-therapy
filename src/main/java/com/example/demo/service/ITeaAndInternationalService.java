package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.TeaAndInternational;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITeaAndInternationalService extends IService<TeaAndInternational>{
    List<TeaAndInternational> getTeaAndInternal();
}
