package com.cnlive.oicc.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.entity.TMember;
import com.cnlive.oicc.mapper.TMemberMapper;
import com.cnlive.oicc.service.MemberService;
import com.cnlive.oicc.utils.CommonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 保存用户token
 */
@AllArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {
    private final TMemberMapper tMemberMapper;
    private static Log logger = LogFactory.getLog(MemberServiceImpl.class);

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
            return tMemberMapper.updateByPrimaryKeySelective(member) > 0 ? true : false;
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

    public List<TMember> findAllidNotInEnrol(String tableName) {
        return tMemberMapper.findAllidNotInEnrol(tableName);
    }

    public List<TMember> findAllidNotInproduction(String tableName, String type) {
        return tMemberMapper.findAllidNotInproduction(tableName, type);
    }

    public PageInfo<Map<String, Object>> paginate(int pageNo, int pageSize, String tableName, String time, String mobile) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> paginatelist = tMemberMapper.paginate(tableName, time, mobile);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(paginatelist);
        return pageInfo;
    }

    public PageInfo<Map<String, Object>> paginate1(int pageNo, int pageSize, String tableName, String time, String mobile) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> paginatelist = tMemberMapper.paginate1(tableName, time, mobile);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(paginatelist);
        return pageInfo;
    }

    public PageInfo<Map<String, Object>> paginate2(int pageNo, int pageSize, String tableName, String time, String mobile) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> paginatelist = tMemberMapper.paginate2(tableName, time, mobile);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(paginatelist);
        return pageInfo;
    }

    public PageInfo<Map<String, Object>> paginate3(int pageNo, int pageSize, String tableName, String time, String mobile, String type) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> paginatelist = tMemberMapper.paginate3(tableName, time, mobile, type);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(paginatelist);
        return pageInfo;
    }

    public PageInfo<Map<String, Object>> paginate4(int pageNo, int pageSize, String tableName, String time, String mobile, int upload, String type) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> paginatelist = tMemberMapper.paginate4(tableName, time, mobile, upload, type);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(paginatelist);
        return pageInfo;
    }
}
