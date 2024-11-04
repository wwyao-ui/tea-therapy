package com.example.demo.controller;

import com.example.demo.common.ResultUtil;
import com.example.demo.entity.Message;
import com.example.demo.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    @Qualifier("messageServiceImpl")
    private IMessageService messageService;
    //留言查询
    @GetMapping("/list")
    public List<Message> list(){
        return messageService.list();
    }
    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody Message msg){
        return messageService.updateById(msg);
    }
    //新增或修改
    @PostMapping("/saveormod")
    public boolean saveormod(@RequestBody Message msg){
        return messageService.saveOrUpdate(msg);
    }
    //删除
    @PostMapping("/delete")
    public boolean delete(@RequestBody Integer id) {return messageService.removeById(id);}
//    @GetMapping("/delete")
//    public boolean delete(Integer id){
//        return messageService.removeById(id);
//    }
//接受留言数据封装并返回数据给前端

    @PostMapping("find")
    List<Message> findAllMessages() {
        return messageService.findAllMessage();
    }
    //新增留言
    @PostMapping("/save")
    public boolean save(@RequestBody Message msg){
        return messageService.save(msg);
    }
    //分页查询
    @GetMapping("/listPage")
    public ResultUtil page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize){
        System.out.println("页码："+pageNum);
        System.out.println("内容："+messageService.messagePage(pageNum,pageSize));
        return messageService.messagePage(pageNum,pageSize);
    }
    @GetMapping("/latest")
    public List<Message> getLatestMessages() {
        return messageService.findTop3Message();
    }

}
