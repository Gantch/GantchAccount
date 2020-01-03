package com.gantch.controller;

import com.gantch.common.CommonResult;
import com.gantch.service.UserMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lcw332
 * Date 2019-12-30-9:41
 * Description:  account , com.gantch.conntroller
 **/
@RestController
@Api(tags = "AccountUserController",description = "用户模块")
@RequestMapping("/api/v1/mobile")
public class UserMemberController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UserMemberService userService;


    @ApiOperation("用户手机注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult register(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String telephone,
                                 @RequestParam String authCode){

        return null;
    }
}
