package com.gantch.pojo;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author lcw332
 * Date 2020-01-03-16:51
 * Description:  account , com.gantch.pojo
 **/
@Data
public class UserMember {

    private Integer id;

    private Integer tenantId;

    private String name;

    private String userName;

    private String passWord;

    private String phone;

    private String email;

    private String openId;

    private Timestamp createTime;
}
