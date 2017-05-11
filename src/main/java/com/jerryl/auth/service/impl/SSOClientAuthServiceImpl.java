package com.jerryl.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.jerryl.Config;
import com.jerryl.auth.service.SSOClientAuthService;
import com.jerryl.common.Status;
import com.jerryl.common.ToWeb;
import com.jerryl.common.exception.BaseException;
import com.jerryl.util.HttpUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by liuruijie on 2017/4/7.
 */
@Service
public class SSOClientAuthServiceImpl implements SSOClientAuthService{


    /**
     * 将票据和模块等信息发送给认证服务器，交由认证服务器验证用户权限
     * @param token 票据
     * @param moduleKey 模块
     * @param actionId 操作
     * @return
     */
    @Override
    public boolean checkPrivilege(String token, String moduleKey, Integer actionId) {
        //准备发送的请求相关参数设置
        HttpUtil.RequestConfig config = new HttpUtil.RequestConfig(
                Config.AUTH_URL
                , "GET");
        config.addParam("token", token);
        config.addParam("moduleKey", moduleKey);
        config.addParam("actionId", actionId);

        final boolean[] result = {false};
        //发送请求
        HttpUtil.sendRequest(config, new HttpUtil.ResponseListener() {
            @Override
            public void afterResponse(String webResult) {
                //获取认证服务器的返回值，并解析为对象
                ToWeb toWeb = JSON.parseObject(webResult, ToWeb.class);
                System.out.println(webResult);
                //各种情况的处理
                if(toWeb.getStatus().equals(Status.NO_LOGIN)){
                    throw new BaseException("haven't login yet!", Status.NO_LOGIN);
                }
                else if(toWeb.getStatus().equals(Status.NO_PRIVILEGE)){
                    throw new BaseException("no privileges!", Status.NO_PRIVILEGE);
                }
                else if(((Map<String, Object>)toWeb.getData().get("extra"))
                        .get("isOk").equals(true)){
                    result[0] = true;
                }
            }
        });
        return result[0];
    }
}
