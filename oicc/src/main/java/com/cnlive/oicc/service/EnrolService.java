package com.cnlive.oicc.service;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.entity.TEnrol;
import com.cnlive.oicc.mapper.TEnrolMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报名信息
 */
@AllArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class EnrolService {
   private final TEnrolMapper tEnrolMapper;
    private static Log logger = LogFactory.getLog(EnrolService.class);

    public PageInfo<TEnrol> getEnrolByPage(Integer pageNo, int pageSize, String query) {

        PageHelper.startPage(pageNo, pageSize);
        if (StringUtils.isEmpty(query)) {
            List<TEnrol> tEnrolList = tEnrolMapper.getEnrolOrderbyinsertDate();
            PageInfo<TEnrol> enrolPageInfo = new PageInfo<>(tEnrolList);
            return enrolPageInfo;
        } else {
            List<TEnrol> tEnrolList = tEnrolMapper.getEnrolOrderbyinsertDate1(query);
            PageInfo<TEnrol> enrolPageInfo = new PageInfo<>(tEnrolList);
            return enrolPageInfo;
        }
    }

    public PageInfo<TEnrol> getEnrolByPage(Integer pageNo, String query) {
        logger.info("报名信息page查询pageNo=" + pageNo + ",query=" + query);
        int pageSize = 15;
        PageInfo<TEnrol> enrolByPage = getEnrolByPage(pageNo, pageSize, query);
        return enrolByPage;
    }

    public TEnrol getOneByUserId(String memberId) {
        logger.info("报名信息单条查询userId=" + memberId);
        TEnrol tEnrol = new TEnrol();
        tEnrol.setMemberId(Integer.parseInt(memberId));
        tEnrol.setIsTeam(0);
        List<TEnrol> enrolList = tEnrolMapper.select(tEnrol);
        return enrolList.get(0);
    }

    public TEnrol getOneByTel(String tel) {
        TEnrol tEnrol = new TEnrol();
        tEnrol.setTel(tel);
        List<TEnrol> enrolList = tEnrolMapper.select(tEnrol);
        return enrolList.get(0);
    }

    public boolean isUpTeam(String memberId) {
        TEnrol tEnrol = new TEnrol();
        tEnrol.setMemberId(Integer.parseInt(memberId));
        tEnrol.setIsTeam(1);
        List<TEnrol> enrolList = tEnrolMapper.select(tEnrol);
        if (enrolList.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean save(TEnrol tEnrol) {
        int i = tEnrolMapper.insert(tEnrol);
        if (i > 0) {
            return true;
        }
        return false;
    }
    public List<TEnrol> findAll(){
        return tEnrolMapper.selectAll();
    }
}
