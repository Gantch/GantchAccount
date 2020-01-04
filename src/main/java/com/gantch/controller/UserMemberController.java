package com.gantch.controller;

import com.gantch.common.CommonResult;
import com.gantch.model.UserMember;
import com.gantch.model.UserMemberRelation;
import com.gantch.service.UserMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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
    private UserMemberService memberService;


    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal){
        String username = principal.getName();
        UserMember userMember = memberService.getByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("id",userMember.getId());
        data.put("username", userMember.getUsername());
        data.put("tenantId",userMember.getTenantId());
        data.put("status",userMember.getStatus());
        data.put("createTime",userMember.getCreateTime());
        data.put("phone",userMember.getPhone());
        data.put("email",userMember.getEmail());
        data.put("openId",userMember.getOpenId());
        data.put("name",userMember.getName());
        data.put("icon",userMember.getIcon());
        data.put("birthday",userMember.getBirthday());
        return CommonResult.success(data);
    }

    @ApiOperation("用户手机注册")
    @RequestMapping(value = "/phoneRegister", method = RequestMethod.POST)
    public CommonResult phoneRegister(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String telephone,
                                 @RequestParam String authCode){
        return memberService.register(username,password,telephone,authCode);
    }

    @ApiOperation("邮箱注册")
    @RequestMapping(value = "/emailRegister",method = RequestMethod.POST)
    public CommonResult emailRegister(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String email,
                                      @RequestParam String authCode){
        return null;
    }

    @ApiOperation("微信注册")
    @RequestMapping(value = "/weChatRegister",method = RequestMethod.POST)
    public CommonResult weChatRegister(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String openId,
                                      @RequestParam String authCode){
        return null;
    }

    @ApiOperation("微信登录")
    @RequestMapping(value = "/weChatLogin",method = RequestMethod.POST)
    public CommonResult weChatLogin(@RequestParam String username,
                                       @RequestParam String password,
                                       @RequestParam String openId,
                                       @RequestParam String authCode){
        return null;
    }
    @ApiOperation("手机登录")
    @RequestMapping(value = "/phoneLogin",method = RequestMethod.POST)
    public CommonResult phoneLogin(@RequestParam String username,
                                    @RequestParam String password){
        String token = memberService.login(username,password);
        if (token == null){
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String,String> tokenMap =new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    public CommonResult getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }


}
