package com.jerryl.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2017/4/1.
 * 获取当前线程的request、response、session
 */
public class SessionUtil {
    public static HttpSession getCurrentSession(){
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest()
                .getSession();
    }

    public static HttpServletRequest getCurrentRequest(){
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();
    }

    public static HttpServletResponse getCurrentResponse(){
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getResponse();
    }
}
