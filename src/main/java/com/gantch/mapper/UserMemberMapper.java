package com.gantch.mapper;

import com.gantch.model.UserMember;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lcw332
 * Date 2020-01-03-16:46
 * Description:  account , com.gantch.mapper
 **/
@Mapper
public interface UserMemberMapper {

    @Select("SELECT * FROM user_member WHERE username = #{username}")
    List<UserMember> selectByUsername(@Param("username")String username);

    @Insert("INSERT INTO user_member(tenant_id,name,username,password,phone,email,openid,create_time,status,icon,birthday) " +
            "VALUES (#{tenantId},#{name},#{username},#{password},#{phone},#{email},#{openId},#{createTime},#{status},#{icon},#{birthday})")
    @Options(keyProperty = "id",keyColumn = "id",useGeneratedKeys = true)
    Integer insert(UserMember userMember);

    @Select("SELECT * FROM user_member WHERE id= #{id}")
    UserMember selectByPrimaryKey(@Param("id") Integer id);

    @Select("SELECT * FROM user_member WHERE phone = #{phone}")
    UserMember selectByPhoneNumber(@Param("phone") String phone);
}
