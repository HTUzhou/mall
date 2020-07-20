package com.macro.mall.tiny.service;

import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后台管理员service
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册后台管理员
     * @param umsAdminParam
     * @return
     */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * 后台管理员登录
     * @param username
     * @param password
     * @return 生成的JWT token
     */
    String login(String username, String password);

    /**
     * 获取管理员用户的权限（角色权限）
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(Long adminId);
}
