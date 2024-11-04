package com.example.demo.service;

import com.example.demo.common.ResultUtil;
import com.example.demo.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author he
 * @since 2024-07-06
 */
@Service
public interface IMessageService extends IService<Message> {
    Map<String, Object> addMessage(Message message);
    List<Message> findAllMessage();
    ResultUtil messagePage(Integer pageNum, Integer pageSize);
//自己增加的代码
    List<Message> findTop3Message();
}
