package com.gantch.service.impl;

import com.gantch.common.CommonResult;
import com.gantch.mapper.UserMemberMapper;
import com.gantch.pojo.MemberDetails;
import com.gantch.model.UserMember;
import com.gantch.service.RedisService;
import com.gantch.service.UserMemberService;
import com.gantch.utils.AliyunSmsUtils;
import com.gantch.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

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
    public UserMember getById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public CommonResult register(String username, String password, String telephone, String authCode) {
        //验证验证码
        if(!verifyAuthCode(authCode,telephone)){
            return CommonResult.failed("验证码错误");
        }
        //todo 查询是否已有该用户
        List<UserMember> userMemberList = memberMapper.selectByUsername(username);
        if (userMemberList.size()>0){
            return CommonResult.failed("该账号已被注册");
        }
        //没有该用户进行添加操作
        UserMember userMember =new UserMember();
        userMember.setUsername(username);
        userMember.setPassword(passwordEncoder.encode(password));
        userMember.setEmail(null);
        userMember.setPhone(telephone);
        userMember.setTenantId(1);//租户ID 1为冠川
        userMember.setOpenId(null);
        userMember.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userMember.setStatus(1);// 0——》禁用 1——》启用
        userMember.setBirthday(null);
        memberMapper.insert(userMember);
        return CommonResult.success(null,"注册成功");
    }

    @Override
    public CommonResult generateAuthCode(String telephone) {
        Integer mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        try {
             AliyunSmsUtils.sendSms(telephone, mobile_code);
        }catch (Exception e){
            System.out.println(e);
            return CommonResult.failed();
        }
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+telephone,mobile_code.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+telephone,AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(mobile_code.toString(),"获取验证码成功");
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserMember member = getByUsername(username);
        if (member !=null){
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String login(String username, String password) {
        String token =null;
        try{
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token= jwtTokenUtil.generateToken(userDetails);
        }catch (Exception e){
            LOGGER.warn("登录异常:{}",e.getMessage());
        }
        return token;
    }

    @Override
    public UserMember getByUsername(String username) {
        List<UserMember> memberList= memberMapper.selectByUsername(username);
        if (!CollectionUtils.isEmpty(memberList)){
            return memberList.get(0);
        }
        return null;
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
