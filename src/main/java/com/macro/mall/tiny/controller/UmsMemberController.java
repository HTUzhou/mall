package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员登录注册管理Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/sso")
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;

    /**
     * 根据手机号获取验证码
     * @param telephone
     * @return
     */
    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    public CommonResult getAuthCode(@RequestParam String telephone) {
        return umsMemberService.generateAuthCode(telephone);
    }

    /**
     * 通过验证码是否正确从而允许更新密码
     * @param telephone
     * @param authCode
     * @return
     */
    @ApiOperation("判断验证码是否正确")
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
    public CommonResult updatePassword(@RequestParam String telephone,
                                       @RequestParam String authCode) {
        return umsMemberService.verifyAuthCode(telephone, authCode);
    }



}
