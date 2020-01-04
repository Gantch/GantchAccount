package com.gantch.mapper;

import com.gantch.model.UserMemberRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author lcw332
 * Date 2020-01-04-14:30
 * Description:  account , com.gantch.mapper
 **/
@Mapper
public interface UserRelationMapper {

    @Select("SELECT * FROM user_relation WHERE binder = #{binder} AND binded = #{binded}")
    UserMemberRelation getRelation(UserMemberRelation relation);

    @Update("UPDATE user_relation SET binder = #{binder} , binded = #{binded} , gateid = #{gateid} , remark = #{remark} WHERE id = #{id}")
    Integer updateByPrimaryKey(UserMemberRelation relation);

    @Insert("INSERT INTO user_relation(id,binder,binded,gateid,remark) VALUES(#{id},#{binder},#{binded},#{gateId},#{remark})")
    Integer insert(UserMemberRelation relation);
}
