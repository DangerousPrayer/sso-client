package com.jerryl.auth.service.model;

import java.util.Date;

/**
 * Created by liuruijie on 2017/4/6.
 */
public class Module {
    private Integer id;
    private String moduleKey;
    private String moduleName;
    private Integer sort;
    private String url;
    private Date createDate;
//    private String createPassport;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModuleKey() {
        return moduleKey;
    }

    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

//    public String getCreatePassport() {
//        return createPassport;
//    }
//
//    public void setCreatePassport(String createPassport) {
//        this.createPassport = createPassport;
//    }
}
