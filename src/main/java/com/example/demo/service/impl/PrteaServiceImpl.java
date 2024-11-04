package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Prtea;
import com.example.demo.entity.Userbrowsinghistory;
import com.example.demo.mapper.PrteaMapper;
import com.example.demo.mapper.UserbrowsinghistoryMapper;
import com.example.demo.service.IPrteaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author he
 * @since 2024-07-11
 */
@Service
public class PrteaServiceImpl extends ServiceImpl<PrteaMapper, Prtea> implements IPrteaService {

    @Autowired
    PrteaMapper prteaMapper;
    @Autowired
    UserbrowsinghistoryMapper userbrowsinghistoryMapper;

    public PrteaServiceImpl(PrteaMapper prteaMapper, UserbrowsinghistoryMapper userbrowsinghistoryMapper) {
        this.prteaMapper = prteaMapper;
        this.userbrowsinghistoryMapper = userbrowsinghistoryMapper;
    }

    List<String> teacontent = Arrays.asList("乌龙茶", "黑茶");

    //初始化茶叶百科茶叶及内容
    @Override
    public void initializeTeas() {
        prteaMapper.insertTea();

    }

    //浏览一次，rating加1
    @Override
    public void browseTea(int uid, int tid) {
        Userbrowsinghistory browsingHistory = new Userbrowsinghistory();
        browsingHistory.setUid(uid);
        browsingHistory.setTid(tid);
        userbrowsinghistoryMapper.insertBrowsingRecord(browsingHistory);
        prteaMapper.updateTeaRating(tid);
    }

    //查询所有茶
    @Override
    public List<Prtea> getRecommendedTeas() {
        return prteaMapper.selectAllTeas();
    }
}
