package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.TeaAndInternational;
import com.example.demo.mapper.TeaAndInternationalMapper;
import com.example.demo.service.ITeaAndInternationalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeaAndInternationalImpl extends ServiceImpl<TeaAndInternationalMapper, TeaAndInternational> implements ITeaAndInternationalService {
    // 如果有必要，实现ITeaAndInternalService中的抽象方法
    @Autowired
    TeaAndInternationalMapper teaAndInternationalMapper;

    @Override
    public List<TeaAndInternational> getTeaAndInternal() {
        return teaAndInternationalMapper.selectAll();
        // 方法的具体实现
    }
}