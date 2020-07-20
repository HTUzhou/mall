package com.macro.mall.tiny.service;

import com.macro.mall.tiny.mbg.model.PmsBrand;

import java.util.List;

/**
 * 品牌管理service
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int updateBrand(Long id, PmsBrand brand);

    int deleteBrand(Long id);

//    分页查询
    List<PmsBrand> listBrand(int pageNum, int pageSize);

    PmsBrand getBrand(Long id);
}
