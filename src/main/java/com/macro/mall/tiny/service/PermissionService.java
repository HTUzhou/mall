package com.macro.mall.tiny.service;

import com.macro.mall.tiny.dto.PermissionDetails;
import com.macro.mall.tiny.dto.RoleDetails;
import com.macro.mall.tiny.mbg.model.UmsPermission;

import java.util.List;

/**
 * 权限管理Service
 */
public interface PermissionService {
    /**
     * 获取所有权限
     * @return
     */
    public List<UmsPermission> getAllPermissions();

    /**
     * 获取所有角色信息
     */
    public List<RoleDetails> getAllRoles();

    /**
     * 除去某个角色的权限
     * @param roleId
     * @param permissionId
     * @return
     */
    public int removePermission(Long roleId, Long permissionId);

    /**
     * 获取所有权限，以树形图返回
     * @return
     */
    public List<PermissionDetails> getPermissionTypeTree();

    /**
     * 给定一个权限列表，将其转成以树状图为结构的权限树
     * @param umsPermissions
     * @return
     */
    public List<PermissionDetails> getPermission2Tree(List<UmsPermission> umsPermissions);

    /**
     * 给指定的角色添加一系列权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    public int addPermissionForRole(Long roleId, List<Long> permissionIds);
}
