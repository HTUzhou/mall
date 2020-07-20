package com.macro.mall.tiny.service;

import com.macro.mall.tiny.common.api.CommonResult;

/**
 * 会员管理service
 */
public interface UmsMemberService {
    /**
     * 根据telephone生成验证码
     * @param telephone
     * @return
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     * @param telephone
     * @param authCode
     * @return
     */
    CommonResult verifyAuthCode(String telephone, String authCode);
}
