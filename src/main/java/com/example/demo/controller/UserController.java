package com.example.demo.controller;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.common.ConstantUtil;
import com.example.demo.common.MD5Util;
import com.example.demo.common.ResultUtil;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    /* 注册*/
    @PostMapping("/register")
    @ResponseBody
    public ResultUtil save(@RequestBody User user, HttpSession session) {
        user.setPwd(MD5Util.getMd5(user.getPwd()));//加密密码
        try {
            userMapper.insert(user);
            System.out.println("注册成功");
            return ResultUtil.ok("注册成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResultUtil.error("注册失败");
        }
    }
    @GetMapping("/listPage")
    public ResultUtil page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize){
        System.out.println("页码："+pageNum);
        System.out.println("内容："+userService.UserPage(pageNum,pageSize));
        return userService.UserPage(pageNum,pageSize);
    }
    @Resource
    private UserMapper userMapper;
    /* 跳转到列表页面*/
    @RequestMapping("userinfoList")
    public String userinfoList() {
        return "user/userinfoList";
    }
    /*跳转到添加页面*/
    @RequestMapping("addUserinfo")
    public String addUserinfo(Model model) {
        return "user/saveUserinfo";
    }
    /*跳转到修改页面*/
    @RequestMapping("editUserinfo")
    public String editUserinfo(Integer id, Model model) {
        User user = userMapper.selectUserinfoById(id);
        model.addAttribute("user", user);
        return "user/saveUserinfo";
    }
    /*查看详情页面*/
    @RequestMapping("userinfoInfo")
    public String userinfoInfo(Integer id, Model model) {
        User user = userMapper.selectUserinfoById(id);
        model.addAttribute("user", user);
        return "user/userinfoInfo";
    }
    /*插入*/
    @RequestMapping("saveUserinfo")
    @ResponseBody
    public ResultUtil saveUserinfo(User user, HttpSession session) {
        try {
            userMapper.insertUserinfo(user);
            return ResultUtil.ok("添加用户信息成功");
        } catch (Exception e) {
            return ResultUtil.error("添加用户信息出错,稍后再试！");
        }
    }
    /*更新记录*/
//    @RequestMapping("updateUserinfo")
//    @ResponseBody
//    public ResultUtil updateUserinfo(User user, HttpSession session) {
//        try {
//            userMapper.updateUserinfo(user);
//            return ResultUtil.ok("修改用户信息成功");
//        } catch (Exception e) {
//            return ResultUtil.error("修改用户信息出错,稍后再试！");
//        }
//    }
    /*更新记录*/
    @RequestMapping("updateUserinfo")
    @ResponseBody
    public ResultUtil updateUserinfo(User user) {
        try {
            userMapper.updateUserinfo(user);
            //System.out.println("修改用户信息成功");
            return ResultUtil.ok("修改用户信息成功");
        } catch (Exception e) {
            //System.out.println("修改用户信息失败");
            return ResultUtil.error("修改用户信息出错,稍后再试！");
        }
    }
    /*根据ID删除*/
    @RequestMapping("deleteUserinfo")
    @ResponseBody
    public ResultUtil deleteUserinfoById(Integer id) {
        try {
            userMapper.deleteUserinfoById(id);
            System.out.println("删除用户信息成功");
            return ResultUtil.ok("删除用户信息成功");
        } catch (Exception e) {
            System.out.println("删除用户信息失败");
            return ResultUtil.error("删除用户信息出错,稍后再试！");
        }
    }
    /*根据ID批量删除*/
    @RequestMapping("deletesUserinfo")
    @ResponseBody
    public ResultUtil deletesUserinfo(String idsStr) {
        try {
            if (!StringUtils.isBlank(idsStr)) {
                String[] ids = idsStr.split(",");
                for (String id : ids) {
                    userMapper.deleteUserinfoById(Integer.parseInt(id));
                }
            }
            return ResultUtil.ok("批量删除用户信息成功");
        } catch (Exception e) {
            return ResultUtil.error("删除用户信息出错,稍后再试！");
        }
    }
    //登陆提交
    @RequestMapping("loginSubmit")
    @ResponseBody
    public ResultUtil loginSubmit(HttpServletResponse response,HttpServletRequest request, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "vcode") String vcode) {
        if (org.apache.commons.lang.StringUtils.isEmpty(vcode)) {
            return ResultUtil.error("验证码不能为空");
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(username)) {
            return ResultUtil.error("用户名不能为空");
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(password)) {
            return ResultUtil.error("登陆密码不能为空");
        }
        //获取session中的验证码
        HttpSession session = request.getSession();
        String sessionVcode = session.getAttribute("vcode").toString().toLowerCase();
        System.out.println("sessionVcode"+sessionVcode);
        if (!sessionVcode.equalsIgnoreCase(vcode)) {
            System.out.println("验证码不正确");
            return ResultUtil.error("验证码不正确");
        }
        Map map = new HashMap();
        System.out.println("username = " + username);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(username)) {
            map.put("name", username);
        }
        System.out.println("map = " + map);
        List<User> userList = userMapper.selectAll(map);
        System.out.println("userList = " + userList);
        if (userList.size() <= 0) {
            return new ResultUtil(1, "用户名或密码错误！");
        }
        User user = userList.get(0);
        System.out.println("user = " + user);
        if (user != null) {
            //判断密码是否相等
            password = MD5Util.getMd5(password); //加密密码  然后在进行比对
            System.out.println("数据库存储密码 = " + user.getPwd());
            System.out.println("登录密码加密 = " + password);
            if (user.getPwd().equals(password)) {
                // 登陆成功
                // 将密码置空
                user.setPwd(password);
                String sessionId = UUID.randomUUID().toString();
                session.setAttribute("loggedIn",true);
                session.setAttribute("username",user.getName());
                session.setAttribute("userId",user.getId());
                Cookie cookie=new Cookie("sessionId",sessionId);
                cookie.setPath("/");
                response.addCookie(cookie);
                return new ResultUtil(200);
            } else {
                return new ResultUtil(1, "用户名或密码错误！");
            }
        } else {
            return new ResultUtil(1, "用户名或密码错误！");
        }

    }
    //退出
    @RequestMapping("logout")
    public ResultUtil logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            session.removeAttribute("userId");
            session.removeAttribute("loggedIn");
        }
        return new ResultUtil(200, "退出成功");
    }
    //检查登录状态
    @GetMapping("/checkLogin")
    public boolean checkLogin(HttpServletRequest request) {
        boolean loggedIn =request.getSession().getAttribute("loggedIn")!=null;
        if (loggedIn) return true;
        else return false;
    }
}
