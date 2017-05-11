package com.jerryl.auth.service;

/**
 * Created by liuruijie on 2017/4/7.
 */
public interface SSOClientAuthService {
    /**
     * 通过token获取用户信息，从而进行权限验证
     * @param token 票据
     * @param moduleKey 模块
     * @param actionId 操作
     * @return 是否权限通过
     */
    boolean checkPrivilege(String token, String moduleKey, Integer actionId);
}
