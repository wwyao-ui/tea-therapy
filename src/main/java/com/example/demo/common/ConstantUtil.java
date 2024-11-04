package com.example.demo.common;

import com.example.demo.entity.User;

import javax.servlet.http.HttpSession;


public class ConstantUtil {
    //后台管理员登陆后存放session的变量名
    public static final String MANAGE_LOGIN_SERSSION = "loginUser";
    public static final String MANAGE_LOGIN_SERSSION_ID = "loginUserID";

    /**
     * 从session中获取当前登录的后台用户信息
     *
     * @param session
     * @return
     */
    public static User getManageLoginUser(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("Session cannot be null");
        }
        User user = (User) session.getAttribute(MANAGE_LOGIN_SERSSION);
        return user;
    }

    /**
     * 从session中获取当前登录的后台用户的ID
     *
     * @param session
     * @return
     */
    public static Integer getManageLoginUserID(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("Session cannot be null");
        }
        return (Integer) session.getAttribute(MANAGE_LOGIN_SERSSION_ID);
    }
}

