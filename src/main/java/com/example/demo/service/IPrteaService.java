package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Prtea;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author he
 * @since 2024-07-11
 */
@Service
public interface IPrteaService extends IService<Prtea> {

    void initializeTeas();

    void browseTea(int uid, int tid);

    List<Prtea> getRecommendedTeas();



}
