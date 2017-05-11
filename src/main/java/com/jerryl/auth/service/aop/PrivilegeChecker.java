package com.jerryl.auth.service.aop;

import com.jerryl.auth.service.SSOClientAuthService;
import com.jerryl.auth.service.aop.privilege.Privilege;
import com.jerryl.common.Status;
import com.jerryl.common.ToWeb;
import com.jerryl.common.exception.BaseException;
import com.jerryl.util.SessionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuruijie on 2017/4/7.
 */
@Service
@Aspect
public class PrivilegeChecker {
    @Autowired
    SSOClientAuthService authService;

    /**
     * 拦截@privilege注解，通过注解的参数来验证用户的权限
     * @param joinPoint
     * @param privilege
     * @return
     * @throws Throwable
     */
    @Around("@annotation(privilege)")
    public Object checkPrivilege(ProceedingJoinPoint joinPoint, Privilege privilege) throws Throwable {
        try {
            //获取token
            HttpServletRequest request = SessionUtil.getCurrentRequest();
            String authStr = request.getHeader("authorization");
            if (authStr == null) {
                throw new BaseException("haven't login yet!", Status.NO_LOGIN);
            }
            String token = authStr.split(" ")[1];
            //将token发送到认证服务器，验证用户权限
            if (authService.checkPrivilege(token
                    , privilege.moduleType()
                    , privilege.actionType().getValue())) {
                return joinPoint.proceed();
            } else {
                throw new BaseException("no privileges", Status.NO_PRIVILEGE);
            }
            //由于切面处理不会经过@ControllerAdvice中的异常处理方法，
            //需要进行单独的处理
        } catch (BaseException e) {
            return ToWeb.buildResult().status(e.getCode()).msg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ToWeb.buildResult().status(Status.FAIL).msg("系统错误");
        }
    }
}
