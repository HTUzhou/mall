package com.macro.mall.tiny.dto;

import com.macro.mall.tiny.mbg.model.UmsPermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PermissionDetails implements Serializable {
    private Long id;

    @ApiModelProperty(value = "父级权限id")
    private Long pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "权限值")
    private String value;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    @ApiModelProperty(value = "前端资源路径")
    private String uri;

    @ApiModelProperty(value = "启用状态；0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "下级权限")
    private List<PermissionDetails> children = new ArrayList<>();

    public PermissionDetails(UmsPermission umsPermission) {
        this.id = umsPermission.getId();
        this.pid = umsPermission.getPid();
        this.name = umsPermission.getName();
        this.value = umsPermission.getValue();
        this.icon = umsPermission.getIcon();
        this.type = umsPermission.getType();
        this.uri = umsPermission.getUri();
        this.status = umsPermission.getStatus();
        this.createTime = umsPermission.getCreateTime();
        this.sort = umsPermission.getSort();
    }
}

