package com.gantch.service.impl;

import com.gantch.common.CommonResult;
import com.gantch.dto.UserRelationParam;
import com.gantch.mapper.UserMemberMapper;
import com.gantch.mapper.UserRelationMapper;
import com.gantch.model.UserMember;
import com.gantch.model.UserMemberRelation;
import com.gantch.service.UserMemberService;
import com.gantch.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lcw332
 * Date 2020-01-04-14:30
 * Description:  account , com.gantch.service.impl
 **/
@Service
public class UserRelationServiceImpl implements UserRelationService {

    @Autowired
    private UserRelationMapper relationMapper;

    @Autowired
    private UserMemberMapper memberMapper;



    @Override
    public CommonResult bindGate(UserRelationParam param) {
            String binded = param.getBinded();
            String phone = param.getPhone();
            String gateids = param.getGateids();
            String remark = param.getRemark();
            UserMember userMember = memberMapper.selectByPhoneNumber(phone);
            if (userMember == null) {
                return CommonResult.failed("该用户不存在");
            }
            UserMemberRelation relation = findRelationByBinderAndBinded(userMember.getId(), Integer.parseInt(binded));
            if (relation != null) {
                if (isShared(relation.getGateid(), gateids)) {
                    return CommonResult.failed("网关重复分享");
                } else {
                    String oldGatewayIds = relation.getGateid();
                    relation.setGateid(oldGatewayIds + "," + gateids);
                    updateRelation(relation);
                    return CommonResult.success("绑定成功");
                }
            }else {
                UserMemberRelation re = new UserMemberRelation();
                re.setBinded(Integer.parseInt(binded));
                re.setBinder(userMember.getId());
                re.setRemark(remark);
                saveRelation(re);
                return CommonResult.success("绑定成功");
            }
    }

    @Override
    public UserMemberRelation findRelationByBinderAndBinded(Integer binder, Integer binded) {
        UserMemberRelation relation = new UserMemberRelation();
        relation.setBinder(binder);
        relation.setBinded(binded);
        return relationMapper.getRelation(relation);
    }

    @Override
    public Boolean isShared(String oldGatewayIds, String newGatewayIds) {
        String [] gatewayIds =newGatewayIds.split(",");
        for (String gatewayId : gatewayIds){
            if (oldGatewayIds.contains(gatewayId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateRelation(UserMemberRelation relation) {
        relationMapper.updateByPrimaryKey(relation);
    }

    @Override
    public void saveRelation(UserMemberRelation relation) {
        relationMapper.insert(relation);
    }
}
