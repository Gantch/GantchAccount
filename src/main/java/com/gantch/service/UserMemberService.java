package com.gantch.service;

import com.gantch.common.CommonResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lcw332
 * Date 2019-12-30-9:41
 * Description:  account , com.gantch.service
 **/
public interface UserMemberService {

    /**
     * 用户注册
     */
    @Transactional
    CommonResult register(String username,String password,String telephone,String authCode);
    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);
    /**
     * 刷新token
     */
    String refreshToken(String token);
}
