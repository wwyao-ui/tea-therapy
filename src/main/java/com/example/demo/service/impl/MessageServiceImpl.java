package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.ResultUtil;
import com.example.demo.entity.Message;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author he
 * @since 2024-07-06
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Resource  //注入MessageMapper接口
    private MessageMapper messageMapper;
    public Map<String, Object> addMessage(Message message){
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println(message.toString());
        //插入留言信息
        int result = messageMapper.insertMessage(message);
        if(result > 0){
            //留言信息插入成功
            resultMap.put("code" ,200);
            resultMap.put("message" , "信息发送成功！");
        }else{
            //留言信息插入失败
            resultMap.put("code" ,400);
            resultMap.put("message" , "！信息发送失败");
        }
        return resultMap;
    }
    @Override
    public List<Message> findAllMessage() {
        List<Message> messageList = messageMapper.selectAllMessage();
        return messageList;
    }
    @Override
    public ResultUtil messagePage(Integer pageNum, Integer pageSize) {
        Page<Message> page = page(new Page<>(pageNum, pageSize));
        return ResultUtil.ok(page);
    }
    @Override
    public List<Message> findTop3Message() {
        List<Message> messageList = messageMapper.selectTopThreeMessages();
        return messageList;
    }

}
