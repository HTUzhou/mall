package com.macro.mall.tiny.dto;

import com.macro.mall.tiny.mbg.model.AsideList;

import java.io.Serializable;
import java.util.List;

public class AsideListResult implements Serializable {
    private Integer id;

    private String authname;

    private Integer parentid;

    public AsideListResult() {
    }

    public AsideListResult(Integer id, String authname, Integer parentid, String path, List<AsideList> children) {
        this.id = id;
        this.authname = authname;
        this.parentid = parentid;
        this.path = path;
        this.children = children;
    }

    private String path;

    public List<AsideList> getChildren() {
        return children;
    }

    public void setChildren(List<AsideList> children) {
        this.children = children;
    }

    private List<AsideList> children;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthname() {
        return authname;
    }

    public void setAuthname(String authname) {
        this.authname = authname;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
