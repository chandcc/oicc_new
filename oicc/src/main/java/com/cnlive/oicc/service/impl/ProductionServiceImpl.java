package com.cnlive.oicc.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.entity.TProduction;
import com.cnlive.oicc.mapper.TProductionMapper;
import com.cnlive.oicc.service.ProductionService;
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

@AllArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductionServiceImpl implements ProductionService {
    private static Log logger = LogFactory.getLog(ProductionServiceImpl.class);
    private final TProductionMapper tProductionMapper;

    /**
     * 更新
     *
     * @param tProduction
     * @return
     */
    public boolean update(TProduction tProduction) {
        int i = tProductionMapper.updateByPrimaryKeySelective(tProduction);
        return i > 0 ? true : false;
    }

    public PageInfo<Map<String, Object>> getAdminIndexByPage(Integer pageNo, String mobile, String group_id, String type) {
        int pageSize = 15;
        PageInfo<Map<String, Object>> videoPage = paginateAdmin(pageNo, pageSize, mobile, group_id, type);
        return videoPage;
    }

    public PageInfo<Map<String, Object>> paginateAdmin(int pageNumber, int pageSize, String mobile, String group_id, String type) {
        String sql = "from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=? ";
        PageHelper.startPage(pageNumber, pageSize);
        boolean isNull_group = StringUtils.isEmpty(group_id);
        boolean isNull_mobile = StringUtils.isEmpty(mobile);
        if ("1".equals(type)) group_id = "0";
        if (isNull_group && isNull_mobile) {
            List<Map<String, Object>> paginate = tProductionMapper.paginate(type);
            PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<>(paginate);
            return mapPageInfo;
        } else if ((!isNull_group) && isNull_mobile) {
            List<Map<String, Object>> paginate = tProductionMapper.paginate_groupNotNull(type, group_id);
            PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<>(paginate);
            return mapPageInfo;
        } else if (isNull_group && (!isNull_mobile)) {
            List<Map<String, Object>> paginate = tProductionMapper.paginate_mobileNotNull(type, mobile);
            PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<>(paginate);
            return mapPageInfo;
        } else {
            List<Map<String, Object>> paginate = tProductionMapper.paginate1(type, group_id, mobile);
            PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<>(paginate);
            return mapPageInfo;
        }
    }


    /**
     * 单个作品
     *
     * @param userId
     * @return
     */
    public TProduction getVideoByUserId(String userId, String type) {
        TProduction tProduction = new TProduction();
        tProduction.setTUserId(Integer.parseInt(userId));
        tProduction.setType(Integer.parseInt(type));
        return tProductionMapper.selectOne(tProduction);
    }

    /**
     * 获取上传成功作品
     */
    public TProduction getUpByUserId(String userId, String type) {
        TProduction tProduction = new TProduction();
        tProduction.setTUserId(Integer.parseInt(userId));
        tProduction.setType(Integer.parseInt(type));
        tProduction.setIsUpload(1);
        return tProductionMapper.selectOne(tProduction);
    }

    /**
     * 根据id查询单条作品
     *
     * @param id
     * @return
     */
    public TProduction getVideoById(String id) {
        return tProductionMapper.selectByPrimaryKey(id);
    }

    /**
     * 前台 获取个人所有参赛作品
     *
     * @param userId
     * @return
     */
    public List<TProduction> getMyWorksByUid(int userId) {
        return tProductionMapper.getMyWorksByUidOrderBycreate_time(userId);
    }


    /**
     * 判断是否已上传作品
     */
    public boolean hasUploadFile(int userId) {
        TProduction tProduction = new TProduction();
        tProduction.setTUserId(userId);
        List<TProduction> list = tProductionMapper.select(tProduction);
        return list.size() > 0 ? true : false;
    }

    /**
     * 保存作品信息
     *
     * @param type     报名方式
     * @param domian   域名
     * @param path     路径
     * @param userId   用户id
     * @param group    所属领域
     * @param title    作品标题
     * @param isUpload 文件是否已上传
     * @return
     */

    public boolean saveVideoSrc(String type, String domian, String path, String userId, int group, String title, int isUpload) {
        boolean isSuccess = false;
        try {
            TProduction production = getVideoByUserId(String.valueOf(userId), type);
            if (production != null) {
                production.setInsertTime(CommonUtils.getCurrentTimestamp());
                production.setTUserId(Integer.parseInt(userId));
                production.setTitle(title);
                production.setGroupId(group);
                production.setIsUpload(isUpload);
                production.setFilePath(path);
                production.setDomain(domian);
                production.setType(Integer.parseInt(type));
                int i = tProductionMapper.updateByPrimaryKeySelective(production);
                return i > 0 ? true : false;
            } else {
                int i = tProductionMapper.insert(production);
                return i > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("保存作品文件是否成功：" + isSuccess);
        return isSuccess;
    }


    /**
     * 作品状态初始化
     *
     * @param id
     * @return
     */
    public boolean del(String id) {
        try {
            TProduction production = getVideoById(id);
            production.setIsUpload(0);
            int i = tProductionMapper.updateByPrimaryKeySelective(production);
            return i > 0 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    public List<TProduction> findallByAnyWay(TProduction tProduction) {
        return tProductionMapper.select(tProduction);
    }


}
