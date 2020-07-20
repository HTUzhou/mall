package com.macro.mall.tiny.dao;

import com.macro.mall.tiny.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台用户与角色管理自定义dao
 */
@Repository
public interface UmsAdminRoleRelationDao {
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
