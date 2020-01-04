package com.gantch.service;

import com.gantch.common.CommonResult;
import com.gantch.dto.UserRelationParam;
import com.gantch.model.UserMemberRelation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lcw332
 * Date 2020-01-04-14:29
 * Description:  account , com.gantch.service
 **/
public interface UserRelationService {

    /**
     * 网关主人分享给他人
     */
    @Transactional
    CommonResult bindGate(UserRelationParam param);

    /**
     * 查找是否有绑定关系
     */
    UserMemberRelation findRelationByBinderAndBinded(Integer binder,Integer binded);

    /**
     * 是否绑定
     */
    Boolean isShared(String oldGatewayIds,String newGatewayIds);

    /**
     *
     */
    void updateRelation(UserMemberRelation relation);

    void saveRelation(UserMemberRelation relation);
}
