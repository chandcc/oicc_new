package com.cnlive.oicc.service;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.entity.TCanhui;
import com.cnlive.oicc.mapper.TCanhuiMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CanHuiService {
	private static Log logger = LogFactory.getLog(CanHuiService.class);
	@Autowired
	TCanhuiMapper tCanhuiMapper;
	/**
	 * 通过手机号获取用户信息
	 * @param mobile
	 * @return
	 */
	public TCanhui getByMobile(String mobile) {
		TCanhui tCanhui = new TCanhui();
		tCanhui.setMobile(mobile);
		List<TCanhui> list = tCanhuiMapper.select(tCanhui);
		 if(list!=null&&list.size()>0){
			 return list.get(0);
		 }else{
			 return null;
		 }
		 	
	}
	
	/**
	 * 获取参会列表页面
	 * @param pageNo 
	 * @param query
	 * @return
	 */
	public PageInfo<TCanhui> getPage(int pageNo, String query) {
		int pageSize = 15;
		if(StringUtils.isEmpty(query)){
			List<TCanhui> canhuiList = tCanhuiMapper.paginate();
			PageHelper.startPage(pageNo,pageSize);
			PageInfo<TCanhui> canhuiPageInfo = new PageInfo<>(canhuiList);
			return	canhuiPageInfo;
		}else{
			List<TCanhui> canhuiList = tCanhuiMapper.paginate1(query);
			PageHelper.startPage(pageNo,pageSize);
			PageInfo<TCanhui> canhuiPageInfo = new PageInfo<>(canhuiList);
			return  canhuiPageInfo;
		}
	}

	/**
	 * 保存参会对象
	 * @param tCanhui
	 * @return
	 */
	public boolean save(TCanhui tCanhui){
	int i = tCanhuiMapper.insert(tCanhui);
	return i>0;

}



}
