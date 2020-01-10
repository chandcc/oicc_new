package com.cnlive.oicc.service;


import com.cnlive.oicc.entity.TUser;
import com.cnlive.oicc.mapper.TUserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *系统用户
 */
@SuppressWarnings("serial")
@Service
public class UserService {
	@Autowired
	TUserMapper tUserMapper;
	
	public PageInfo<TUser> paginateByOperator(int pageNumber, int pageSize , String role) {
		List<TUser> userList = tUserMapper.paginate1(role);
		PageHelper.startPage(pageNumber,pageSize);
		PageInfo<TUser> tUserPageInfo = new PageInfo<>(userList);
		return tUserPageInfo;
	}
	
	public PageInfo<TUser> paginate(int pageNumber, int pageSize) {
		List<TUser> userList = tUserMapper.paginate();
		PageHelper.startPage(pageNumber,pageSize);
		PageInfo<TUser> tUserPageInfo = new PageInfo<>(userList);
		return tUserPageInfo;
	}
	
	
}
