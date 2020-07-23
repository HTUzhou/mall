package com.macro.mall.tiny.dto;

import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.mbg.model.UmsRole;

import java.io.Serializable;
import java.util.List;

public class RoleDetails implements Serializable {
    private UmsRole role;
    private List<PermissionDetails> permissions;

    public RoleDetails() {
    }

    public RoleDetails(UmsRole role, List<PermissionDetails> permissions) {
        this.role = role;
        this.permissions = permissions;
    }

    public UmsRole getRole() {
        return role;
    }

    public void setRole(UmsRole role) {
        this.role = role;
    }

    public List<PermissionDetails> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDetails> permissions) {
        this.permissions = permissions;
    }
}
