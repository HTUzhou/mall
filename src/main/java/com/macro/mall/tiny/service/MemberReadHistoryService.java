package com.macro.mall.tiny.service;

import com.macro.mall.tiny.nosql.mongo.document.MemberReadHistory;

import java.util.List;

/**
 * 会员浏览记录管理service
 */
public interface MemberReadHistoryService {
    /**
     * 生成浏览记录
     * @param memberReadHistory
     * @return
     */
    int create(MemberReadHistory memberReadHistory);

    /**
     * 批量删除浏览记录
     * @param ids
     * @return
     */
    int delete(List<String> ids);

    /**
     * 获取用户浏览历史记录
     * @param memberId
     * @return
     */
    List<MemberReadHistory> list(Long memberId);
}
