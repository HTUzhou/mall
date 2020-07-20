package com.macro.mall.tiny.nosql.mongo.repository;

import com.macro.mall.tiny.nosql.mongo.document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {
    /**
     * 根据会员id按时间倒序获取浏览记录
     * @param memberId
     * @return
     */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
