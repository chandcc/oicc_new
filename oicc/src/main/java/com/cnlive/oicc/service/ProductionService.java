package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TProduction;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ProductionService {

    /**
     * 更新
     *
     * @param tProduction
     * @return
     */
    boolean update(TProduction tProduction);

    PageInfo<Map<String, Object>> getAdminIndexByPage(Integer pageNo, String mobile, String group_id, String type);

    PageInfo<Map<String, Object>> paginateAdmin(int pageNumber, int pageSize, String mobile, String group_id, String type);


    /**
     * 单个作品
     *
     * @param userId
     * @return
     */
    TProduction getVideoByUserId(String userId, String type);

    /**
     * 获取上传成功作品
     */
    TProduction getUpByUserId(String userId, String type);

    /**
     * 根据id查询单条作品
     *
     * @param id
     * @return
     */
    TProduction getVideoById(String id);

    /**
     * 前台 获取个人所有参赛作品
     *
     * @param userId
     * @return
     */
    List<TProduction> getMyWorksByUid(int userId);


    /**
     * 判断是否已上传作品
     */
    boolean hasUploadFile(int userId);

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

    boolean saveVideoSrc(String type, String domian, String path, String userId, int group, String title, int isUpload);


    /**
     * 作品状态初始化
     *
     * @param id
     * @return
     */
    boolean del(String id);

    List<TProduction> findallByAnyWay(TProduction tProduction);


}
