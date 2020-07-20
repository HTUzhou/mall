package com.macro.mall.tiny.service;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * 前台订单管理service
 */
public interface OmsPortalOrderService {
    /**
     * 根据提交信息生成订单
     * @param orderParam
     * @return
     */
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);

    /**
     * 取消单个超时订单
     * @param orderId
     */
    @Transactional
    void cancelOrder(Long orderId);
}
