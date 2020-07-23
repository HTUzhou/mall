package com.macro.mall.tiny.mbg.mapper;

import com.macro.mall.tiny.mbg.model.AsideList;
import com.macro.mall.tiny.mbg.model.AsideListExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AsideListMapper {
    int countByExample(AsideListExample example);

    int deleteByExample(AsideListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AsideList record);

    int insertSelective(AsideList record);

    List<AsideList> selectByExample(AsideListExample example);

    AsideList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AsideList record, @Param("example") AsideListExample example);

    int updateByExample(@Param("record") AsideList record, @Param("example") AsideListExample example);

    int updateByPrimaryKeySelective(AsideList record);

    int updateByPrimaryKey(AsideList record);
}