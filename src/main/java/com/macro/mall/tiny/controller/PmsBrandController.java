package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 品牌管理Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/brand")
@Api(tags = "PmsBrandController", description = "商品品牌管理")
public class PmsBrandController {
    @Autowired
    private PmsBrandService demoService;

    /**
     * Logger必须作为类的静态变量使用。原因如下:
     * 1 使用static修饰的属性是归这个类使用的
     * 2 也就是说不论这个类实例化多少个，大家用的都是同一个static属性
     * 3 log4j记录的是当前类的日志，不是每个实例的日志
     * 4 所以只要有一个记录就可以了
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    /**
     * 获得所有的品牌信息
     */
    @ApiOperation("获取所有商品列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<List<PmsBrand>> getBrandList(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return CommonResult.success(demoService.listAllBrand());
    }

    /**
     * 创建一个品牌
     */
    @ApiOperation("添加品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('pms:brand:create')")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = demoService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("createBrand success:{}", pmsBrand);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    /**
     * 修改品牌
     */
    @ApiOperation("修改品牌")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('pms:brand:update')")
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto, BindResult result) {
        CommonResult commonResult;
        int count = demoService.updateBrand(id, pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.debug("updateBrand success:{}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("updateBrand failed:{}", pmsBrandDto);
        }
        return commonResult;
    }

    /**
     * 删除品牌
     */
    @ApiOperation("删除品牌")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        int count = demoService.deleteBrand(id);
        if (count == 1) {
            LOGGER.debug("deleteBrand success: id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed: id={}", id);
            return CommonResult.failed("操作失败");
        }
    }

    /**
     * 分页查询品牌信息
     */
    @ApiOperation("分页查询品牌信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<CommonPage<PmsBrand>> listBrand(
            @RequestParam(value = "pageNum", defaultValue = "1") @ApiParam("页码") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "3") @ApiParam("每页数量") Integer pageSize,
            HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<PmsBrand> brandList = demoService.listBrand(pageNum, pageSize);
        System.out.println(brandList);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    /**
     * 根据id查询品牌
     */
    @ApiOperation("根据id查询品牌")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        System.out.println(demoService);

        return CommonResult.success(demoService.getBrand(id));
    }








}
