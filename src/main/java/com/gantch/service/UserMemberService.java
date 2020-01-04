package com.gantch.service;

import com.gantch.common.CommonResult;
import com.gantch.model.UserMember;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lcw332
 * Date 2019-12-30-9:41
 * Description:  account , com.gantch.service
 **/
public interface UserMemberService {

    /**
     * 根据会员编号获取会员
     */
    UserMember getById(Integer id);
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
    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 登录后获取token
     */
    String login(String username, String password);
    /**
     * 根据用户名获取用户信息
     */
    UserMember getByUsername(String username);


}
