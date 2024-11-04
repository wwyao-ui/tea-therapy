package com.example.demo.controller;


import com.example.demo.common.ConstantUtil;
import com.example.demo.common.ResultUtil;
import com.example.demo.common.TCMBodyConstitution;
import com.example.demo.entity.Tea;
import com.example.demo.mapper.CalculationMapper;
import com.example.demo.mapper.TeaMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author he
 * @since 2024-07-10
 */
@RestController
@RequestMapping("/calculation")
public class CalculationController {
    @Autowired
    CalculationMapper calculationMapper;
    @Autowired
    TeaMapper teaMapper;
    @RequestMapping("addlscore")
    @ResponseBody
    public ResultUtil addScore(HttpServletRequest request,@RequestParam(value="questionnaireid") String questionnaireid,@RequestParam(value ="lscore") Integer lscore){
//        Cookie[] cookies=request.getCookies();
//        String sessionId=null;
//        if(cookies != null){
//            for(Cookie cookie:cookies){
//                if("sessionId".equals(cookie.getName())){
//                    sessionId=cookie.getValue();
//                    break;
//                }
//            }
//        }
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        //Integer uesrId=ConstantUtil.getManageLoginUserID(request.getSession());
        System.out.println("success"+userId);
        int result= calculationMapper.insertlscore(questionnaireid,lscore,userId);
        if(result > 0){
            return ResultUtil.ok("分段分数插入成功");
        }else{
            return ResultUtil.error("分段分数插入失败");
        }
    }


    @RequestMapping("judge")
    @ResponseBody
    public ResultUtil judge(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        TCMBodyConstitution tcm = new TCMBodyConstitution();
        // 问卷ID列表
        List<String> questionnaireIds = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        //问卷ID对应的分数
        List<Integer> lscore = calculationMapper.selectLscoreByUid(userId);
        System.out.println(userId);
        //题目数
        List<Integer> items = Arrays.asList(8,8,7,8,8,7,7,7,7);
        //体质
        List<String> titles = Arrays.asList("平和质", "气虚质", "阳虚质", "阴虚质", "痰湿质", "湿热质", "血瘀质", "气郁质", "特禀质");

        for (String questionnaire : questionnaireIds) {
            System.out.println("当前问题ID: " + questionnaire);
            for (Integer zscore :lscore) {
                for (Integer item : items) {
                    int score = (zscore - item / (item * 4)) * 100;
                    //System.out.println("当前分数:"+score);
                    for (String title : titles) {
                        tcm.setScore(title, score);
                        System.out.println("当前title:"+title+"对应分数："+score);
                    }
                }
            }
        }
        List<Tea> s=teaMapper.selectTeaBytype(tcm.determineConstitution());
        Map<String,String> teas=new HashMap<>();
        for(Tea tea:s) {
            teas.put(tea.getName(),tea.getContent());
        }
        return  ResultUtil.ok(tcm.determineConstitution(),teas);
    }
    @GetMapping("/lscores")
    public List<Integer> getLscoresByUid(@RequestParam Integer uid) {
        return calculationMapper.selectLscoreByUid(uid);
    }

}
