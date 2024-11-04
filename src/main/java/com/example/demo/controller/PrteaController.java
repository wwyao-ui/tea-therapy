package com.example.demo.controller;


import com.example.demo.entity.Prtea;
import com.example.demo.service.impl.PrteaServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author he
 * @since 2024-07-11
 */
@RestController
@RequestMapping("/prtea")
public class  PrteaController {
        @Resource
        PrteaServiceImpl prteaServiceImpl;
        public PrteaController(PrteaServiceImpl prteaServiceImpl) {
            this.prteaServiceImpl = prteaServiceImpl;
        }

        //初始化评分和茶叶页面
        @GetMapping("/initialize-teas")
        public void initializeTeas() {
            prteaServiceImpl.initializeTeas();
        }

        @PostMapping("/browse-tea")
        public void browseTea(HttpServletRequest request, @RequestParam int teaId) {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            prteaServiceImpl.browseTea(userId, teaId);
        }

        @GetMapping("/recommended-teas")
        public List<Prtea> getRecommendedTeas() {
            return prteaServiceImpl.getRecommendedTeas();
        }
    }




