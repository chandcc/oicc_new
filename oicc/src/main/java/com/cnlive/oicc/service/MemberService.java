package com.cnlive.oicc.service;
import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.entity.TMember;
import com.cnlive.oicc.mapper.TMemberMapper;
import com.cnlive.oicc.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * 保存用户token
 *
 */

@Service
public class MemberService {
    @Autowired
    TMemberMapper tMemberMapper;

    public boolean saveOrUpdate(TMember member, String type) {
        int userId = member.getId();
        TMember member1 = new TMember();
        member1.setId(userId);
        List<TMember> tMembers = tMemberMapper.select(member1);
        Long count = new Long((long) tMembers.size());
        if (count == 0L) {
            if ("0".equals(type)) {
                member.setInsertDateOicc(CommonUtils.getCurrentTimestamp());
                member.setCreateDateOicc(CommonUtils.getCurrentTimestamp());
            } else if ("1".equals(type)) {
                member.setInsertDateIp(CommonUtils.getCurrentTimestamp());
                member.setCreateDateIp(CommonUtils.getCurrentTimestamp());
            } else if ("2".equals(type)) {
                member.setInsertDateJs(CommonUtils.getCurrentTimestamp());
                member.setCreateDateJs(CommonUtils.getCurrentTimestamp());
            }
            int i = tMemberMapper.insert(member);
            return i > 0 ? true : false;
        } else {
            TMember tMember = tMembers.get(0);
            if ("0".equals(type)) {
                if (tMember.getCreateDateOicc() == null) {
                    member.setCreateDateOicc(CommonUtils.getCurrentTimestamp());
                }
            } else if ("1".equals(type)) {
                if (tMember.getCreateDateIp() == null) {
                    member.setCreateDateIp(CommonUtils.getCurrentTimestamp());
                }
            } else if ("2".equals(type)) {
                if (tMember.getCreateDateJs() == null) {
                    member.setCreateDateJs(CommonUtils.getCurrentTimestamp());
                }
            }
            return tMemberMapper.updateByPrimaryKeySelective(member) >0 ? true : false;
        }

    }

    public TMember findByEncryptID(String encryptID) {
        TMember tMember = new TMember();
        tMember.setEncryptID(encryptID);
        return tMemberMapper.selectOne(tMember);
    }

    public String getUserIDByEncryptID(String encryptID) {
        if (!StringUtils.isEmpty(encryptID)) {
            TMember member = findByEncryptID(encryptID);
            if (member != null) return String.valueOf(member.getId());
        }
        return null;

    }

}
