package com.gantch.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lcw332
 * Date 2020-01-04-14:24
 * Description:  account , com.gantch.model
 **/

@Data
public class UserMemberRelation {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "网关主人")
    private Integer binder;

    @ApiModelProperty(value = "被分享者")
    private Integer binded;

    @ApiModelProperty(value = "网关ID")
    private String gateid;

    @ApiModelProperty(value = "分享备注")
    private String remark;
}
