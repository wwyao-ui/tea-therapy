package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Calculation;
import com.example.demo.mapper.CalculationMapper;
import com.example.demo.service.ICalculationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author he
 * @since 2024-07-10
 */
@Service
public class CalculationServiceImpl extends ServiceImpl<CalculationMapper, Calculation> implements ICalculationService {

}
