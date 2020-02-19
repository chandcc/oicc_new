package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TEnrol;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 报名信息
 */
public interface EnrolService {

    PageInfo<TEnrol> getEnrolByPage(Integer pageNo, int pageSize, String query);


    PageInfo<TEnrol> getEnrolByPage(Integer pageNo, String query);

    TEnrol getOneByUserId(String memberId);

    TEnrol getOneByTel(String tel);

    boolean isUpTeam(String memberId);

    boolean save(TEnrol tEnrol);

    List<TEnrol> findAll();
}
