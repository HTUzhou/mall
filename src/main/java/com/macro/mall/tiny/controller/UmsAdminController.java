package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.AsideListResult;
import com.macro.mall.tiny.dto.UmsAdminLoginParam;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/admin")
@Api(tags = "UmsAdminController", description = "后台用户管理")
public class UmsAdminController {
    @Autowired
    private UmsAdminService adminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "后台用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam,
                                           BindingResult result) {
        UmsAdmin admin = adminService.register(umsAdminParam);
        if (admin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(admin);
    }

    @ApiOperation(value = "后台用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam,
                              BindingResult result) {
        //BindingResult需要放到@Validated后面,即pojo里面有@NotNull等注解
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取用户所有权限(包括+-权限)")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.POST)
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId) {
        List<UmsPermission> permissionList = adminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }

    @ApiOperation("自动获取用户的所有权限")
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public CommonResult getPermissionList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return CommonResult.success(authorities);
    }

    @ApiOperation("获取侧边栏的信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult getAsideList() {
        List<AsideListResult> asideList = adminService.getAsideList();
        return CommonResult.success(asideList);
    }

    @ApiOperation("获取所有用户信息")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public CommonResult getUsers() {
        List<UmsAdmin> users = adminService.getUsers();
        return CommonResult.success(users);
    }

    @ApiOperation("根据id修改用户状态")
    @RequestMapping(value = "/{id}/state/{status}", method = RequestMethod.PUT)
    public CommonResult modifyStatus(@PathVariable Long id, @PathVariable String status) {
        int count = adminService.modifyStatus(id, status);
        if (count == 0) {
            return CommonResult.failed("修改失败！");
        }
        return CommonResult.success("修改成功！");
    }

    @ApiOperation("通过username进行模糊查询")
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public CommonResult getUserLikeName(@PathVariable String username) {
        List<UmsAdmin> usersLikeName = adminService.getUsersLikeName(username);
        return CommonResult.success(usersLikeName);
    }

    @ApiOperation("通过id获取用户")
    @RequestMapping(value = "/users/get/{id}", method = RequestMethod.GET)
    public CommonResult getUserById(@PathVariable Long id) {
        UmsAdmin user = adminService.getUserById(id);
        return CommonResult.success(user);
    }

    @ApiOperation("通过id修改用户其他信息")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public CommonResult modifyUser(@PathVariable Long id, @PathParam("email") String email) {
        int i = adminService.modifyUser(id, email);
        if (i <= 0) {
            return CommonResult.failed("修改失败！");
        }
        return CommonResult.success("修改成功！");
    }

    @ApiOperation("通过id删除用户")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteUser(@PathVariable Long id) {
        int i = adminService.deleteUser(id);
        if (i <= 0) {
            return CommonResult.failed("删除失败！");
        }
        return CommonResult.success("删除成功！");
    }





}
