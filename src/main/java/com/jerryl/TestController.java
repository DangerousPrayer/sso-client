package com.jerryl;

import com.jerryl.auth.service.aop.privilege.ActionType;
import com.jerryl.auth.service.aop.privilege.Privilege;
import com.jerryl.auth.service.model.Module;
import com.jerryl.common.ToWeb;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuruijie on 2017/5/11.
 */
@RestController
public class TestController {
    @RequestMapping("add")
    @Privilege(moduleType = "sso-client:test"
            , actionType = ActionType.ADD)
    public Object add(){
        return ToWeb.buildResult().msg("add ok!");
    }

    @RequestMapping("show")
    @Privilege(moduleType = "sso-client:test"
            , actionType = ActionType.SHOW)
    public Object show(){
        return ToWeb.buildResult().msg("show ok!");
    }

    @RequestMapping("update")
    @Privilege(moduleType = "sso-client:test"
            , actionType = ActionType.EDIT)
    public Object update(){
        return ToWeb.buildResult().msg("update ok!");
    }

    @RequestMapping("del")
    @Privilege(moduleType = "sso-client:test"
            , actionType = ActionType.DEL)
    public Object del(){
        return ToWeb.buildResult().msg("delete ok!");
    }

    @RequestMapping("metaData")
    public Object metaData(){
        Module module = new Module();
        module.setModuleKey("sso-client:test");
        module.setModuleName("测试模块");
        return module;
    }
}
