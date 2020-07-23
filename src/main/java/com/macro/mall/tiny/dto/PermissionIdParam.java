package com.macro.mall.tiny.dto;

import java.io.Serializable;

public class PermissionIdParam implements Serializable {
    String ids;

    public PermissionIdParam() {
    }

    public PermissionIdParam(String ids) {
        this.ids = ids;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "PermissionIdParam{" +
                "ids='" + ids + '\'' +
                '}';
    }
}
