package com.gantch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author lcw332
 * Date 2020-01-03-16:51
 * Description:  account , com.gantch.pojo
 **/
@Data
public class UserMember {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "账户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "微信OpenId")
    private String openId;

    @ApiModelProperty(value = "账户创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "账户状态：0——》禁用 1——》启用")
    private Integer status;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
}
