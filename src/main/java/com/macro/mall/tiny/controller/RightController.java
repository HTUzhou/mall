package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.PermissionDetails;
import com.macro.mall.tiny.dto.PermissionIdParam;
import com.macro.mall.tiny.dto.RoleDetails;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Api(tags = "RightController", description = "权限管理")
@RestController
@RequestMapping("/rights")
public class RightController {
    @Autowired
    private PermissionService service;

    @ApiOperation("获取所有权限")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult getAllPermissions() {
        List<UmsPermission> allPermissions = service.getAllPermissions();
        return CommonResult.success(allPermissions);
    }

    @ApiOperation("获取所有角色信息")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public CommonResult getAllRoles() {
        List<RoleDetails> allRoles = service.getAllRoles();
        return CommonResult.success(allRoles);
    }

    @ApiOperation("除去角色对应的某个权限")
    @RequestMapping(value = "/roles/{roleId}/permission/{permissionId}", method = RequestMethod.DELETE)
    public CommonResult removePermission(@PathVariable Long roleId, @PathVariable Long permissionId) {
        int i = service.removePermission(roleId, permissionId);
        List<RoleDetails> allRoles = service.getAllRoles();
        List<PermissionDetails> details = null;
        for (RoleDetails item : allRoles) {
            if (item.getRole().getId() == roleId) {
                details = item.getPermissions();
                break;
            }
        }
        if (i <= 0) {
            return CommonResult.failed("删除失败");
        }
        return CommonResult.success(details);
    }

    @ApiOperation("获取用户权限，以树形结构返回")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public CommonResult getAllPermissionTypeTree() {
        List<PermissionDetails> permissionTypeTree = service.getPermissionTypeTree();
        return CommonResult.success(permissionTypeTree);
    }

    @ApiOperation("增加角色权限")
    @RequestMapping(value = "/roles/{roleId}/rights", method = RequestMethod.POST)
    public CommonResult addPermissionForRole(@PathVariable Long roleId, @RequestBody PermissionIdParam ids) {
        String[] splits = ids.getIds().split(",");
        List<Long> permissionIds = new ArrayList<>();
        for (String id : splits) {
            permissionIds.add(new Long(id));
        }
        int sum = service.addPermissionForRole(roleId, permissionIds);
        return CommonResult.success("插入了" + sum + "条数据成功");
    }
}
