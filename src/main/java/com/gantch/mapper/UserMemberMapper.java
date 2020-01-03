package com.gantch.mapper;

import com.gantch.pojo.UserMember;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lcw332
 * Date 2020-01-03-16:46
 * Description:  account , com.gantch.mapper
 **/
@Mapper
public interface UserMemberMapper {

    Integer insert(UserMember userMember);
}
