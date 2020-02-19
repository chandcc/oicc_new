package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TCanhui;
import com.github.pagehelper.PageInfo;

public interface CanHuiService {
    /**
     * 通过手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    TCanhui getByMobile(String mobile);

    /**
     * 获取参会列表页面
     *
     * @param pageNo
     * @param query
     * @return
     */
    PageInfo<TCanhui> getPage(int pageNo, String query);

    /**
     * 保存参会对象
     *
     * @param tCanhui
     * @return
     */
    boolean save(TCanhui tCanhui);


}
