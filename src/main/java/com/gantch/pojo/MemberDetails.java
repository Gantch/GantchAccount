package com.gantch.pojo;

import com.gantch.model.UserMember;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author lcw332
 * Date 2020-01-04-8:44
 * Description:  account , com.gantch.pojo
 * 用户详情封装页
 **/
public class MemberDetails implements UserDetails {

    private UserMember userMember;

    public MemberDetails(UserMember userMember) {
        this.userMember = userMember;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return Arrays.asList(new SimpleGrantedAuthority("TEST"));
    }

    @Override
    public String getPassword() {
        return userMember.getPassword();
    }

    @Override
    public String getUsername() {
        return userMember.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userMember.getStatus()==1;
    }

    public UserMember getUserMember() {
        return userMember;
    }
}
