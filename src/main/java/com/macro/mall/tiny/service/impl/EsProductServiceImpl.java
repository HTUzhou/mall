package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.dao.EsProductDao;
import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import com.macro.mall.tiny.nosql.elasticsearch.repository.EsProductRepository;
import com.macro.mall.tiny.service.EsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EsProductServiceImpl implements EsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);
    @Autowired
    private EsProductDao esProductDao;
    @Autowired
    private EsProductRepository esProductRepository;
    @Override
    public int importAll() {
        List<EsProduct> allEsProductList = esProductDao.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = esProductRepository.saveAll(allEsProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> allEsProductList = esProductDao.getAllEsProductList(id);
        if (allEsProductList.size() > 0) {
            EsProduct esProduct = allEsProductList.get(0);
            result = esProductRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsProduct> esProducts = new ArrayList<>();
            for (Long id : ids) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProducts.add(esProduct);
            }
            esProductRepository.deleteAll(esProducts);
        }
    }

    /**
     * 分页查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        //springdata的分页查询，不是PageHelper
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        return esProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
