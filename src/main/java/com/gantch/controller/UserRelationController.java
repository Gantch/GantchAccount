package com.gantch.controller;

import com.gantch.common.CommonResult;
import com.gantch.dto.UserRelationParam;
import com.gantch.model.UserMemberRelation;
import com.gantch.service.UserRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lcw332
 * Date 2020-01-04-14:31
 * Description:  account , com.gantch.controller
 **/

@RestController
@Api(tags = "AccountUserController",description = "Zigbee用户关系模块")
@RequestMapping(value = "/api/v1/mobile/relation")
public class UserRelationController {
    @Autowired
    private UserRelationService relationService;

    @ApiOperation("分享网关")
    @RequestMapping(value = "/bindGate",method = RequestMethod.POST)
    public CommonResult bindGate(@RequestBody UserRelationParam relation){
        return relationService.bindGate(relation);
    }

    @ApiOperation("获取被分享的网关")
    @RequestMapping(value = "/getBinderGates",method = RequestMethod.GET)
    private CommonResult getBinderGates(){
        return CommonResult.failed();
    }

}
