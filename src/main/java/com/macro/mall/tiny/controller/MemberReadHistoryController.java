package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.nosql.mongo.document.MemberReadHistory;
import com.macro.mall.tiny.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员商品浏览记录管理Controller
 */
@CrossOrigin
@Api(tags = "MemberReadHistoryController", description = "会员商品浏览记录管理")
@RestController
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController {
    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation(value = "创建浏览记录")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            return  CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "删除浏览记录")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("ids")List<String> ids) {
        int count = memberReadHistoryService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "展示浏览记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<MemberReadHistory>> list(Long memberId) {
        List<MemberReadHistory> list = memberReadHistoryService.list(memberId);
        return CommonResult.success(list);
    }


}
