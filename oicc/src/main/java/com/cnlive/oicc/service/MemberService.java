package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TMember;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


/**
 * 保存用户token
 */

public interface MemberService {

    boolean saveOrUpdate(TMember member, String type);

    TMember findByEncryptID(String encryptID);

    String getUserIDByEncryptID(String encryptID);

    List<TMember> findAllidNotInEnrol(String tableName);

    List<TMember> findAllidNotInproduction(String tableName, String type);

    PageInfo<Map<String, Object>> paginate(int pageNo, int pageSize, String tableName, String time, String mobile);

    PageInfo<Map<String, Object>> paginate1(int pageNo, int pageSize, String tableName, String time, String mobile);

    PageInfo<Map<String, Object>> paginate2(int pageNo, int pageSize, String tableName, String time, String mobile);

    PageInfo<Map<String, Object>> paginate3(int pageNo, int pageSize, String tableName, String time, String mobile, String type);

    PageInfo<Map<String, Object>> paginate4(int pageNo, int pageSize, String tableName, String time, String mobile, int upload, String type);
}
