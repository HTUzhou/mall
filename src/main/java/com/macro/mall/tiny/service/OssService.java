package com.macro.mall.tiny.service;

import com.macro.mall.tiny.dto.OssCallbackResult;
import com.macro.mall.tiny.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * OSS上传文件管理Service
 */
public interface OssService {
    /**
     * oss上传策略生成
     * @return
     */
    OssPolicyResult policy();

    /**
     * oss上传成功回调
     * @param request
     * @return
     */
    OssCallbackResult callback(HttpServletRequest request);
}
