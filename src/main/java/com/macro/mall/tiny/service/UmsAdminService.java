package com.macro.mall.tiny.service;

import com.macro.mall.tiny.dto.AsideListResult;
import com.macro.mall.tiny.mbg.model.AsideList;
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

    /**
     * 获取侧边栏信息
     * @return
     */
    List<AsideListResult> getAsideList();

    /**
     * 获取所有用户信息
     * @return
     */
    List<UmsAdmin> getUsers();

    /**
     * 根据id修改用户的状态
     * @param id
     * @param status
     * @return
     */
    int modifyStatus(Long id, String status);

    /**
     * 根据参数username，进行模糊查询
     * @param username
     * @return
     */
    List<UmsAdmin> getUsersLikeName(String username);

    /**
     * 通过id获得对应的用户
     * @param id
     * @return
     */
    UmsAdmin getUserById(Long id);

    /**
     * 通过id修改邮箱
     * @param id
     * @param email
     * @return
     */
    int modifyUser(Long id, String email);

    /**
     * 通过id删除用户
     * @param id
     * @return
     */
    int deleteUser(Long id);
}
