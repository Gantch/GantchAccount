package com.gantch.service.impl;

import com.gantch.common.CommonResult;
import com.gantch.mapper.UserMemberMapper;
import com.gantch.pojo.UserMember;
import com.gantch.service.RedisService;
import com.gantch.service.UserMemberService;
import com.gantch.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * @author lcw332
 * Date 2019-12-30-9:41
 * Description:  account , com.gantch.service.impl
 **/
@Service
public class UserMemberServiceImpl implements UserMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserMemberServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMemberMapper memberMapper;


    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public CommonResult register(String username, String password, String telephone, String authCode) {
        //验证验证码
        if(!verifyAuthCode(authCode,telephone)){
            return CommonResult.failed("验证码错误");
        }
        //todo 查询是否已有该用户

        //没有该用户进行添加操作
        UserMember userMember =new UserMember();
        userMember.setUserName(username);
        userMember.setPassWord(passwordEncoder.encode(password));
        userMember.setEmail(null);
        userMember.setPhone(telephone);
        userMember.setTenantId(1);
        userMember.setOpenId(null);
        userMember.setCreateTime(new Timestamp(System.currentTimeMillis()));

        memberMapper.insert(userMember);

        return CommonResult.success(null,"注册成功");
    }

    @Override
    public CommonResult generateAuthCode(String telephone) {
        Integer mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+telephone,mobile_code.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+telephone,AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(mobile_code.toString(),"获取验证码成功");
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    //对输入的验证码进行校验
    private boolean verifyAuthCode(String authCode, String telephone){
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }
}
