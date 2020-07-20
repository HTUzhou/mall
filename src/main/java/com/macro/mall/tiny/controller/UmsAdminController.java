package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.UmsAdminLoginParam;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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




}
