package com.jerryl.auth.service.aop.privilege;

/**
 * Created by liuruijie on 2017/4/7.
 * 各个操作，以及其对应的id
 */
public enum ActionType {
    ADD(1),EDIT(2),DEL(3),SHOW(4);
    int value;

    ActionType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
