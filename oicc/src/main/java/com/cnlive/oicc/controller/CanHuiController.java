package com.cnlive.oicc.controller;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.bean.ResultBean;
import com.cnlive.oicc.entity.TCanhui;
import com.cnlive.oicc.service.CanHuiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 参会报名
 *
 */
@Controller
@RequestMapping("/canhui")
public class CanHuiController   {
	private Logger logger = Logger.getLogger(CanHuiController.class);

	@Autowired
	CanHuiService canHuiService;
	
	/**
	 * 参会页面
	 */
	@RequestMapping("/index/{pageNo}")
	public String index(HttpServletRequest request, HttpServletResponse response, @PathVariable int pageNo, String query, Model model){
		pageNo = pageNo== 0 ?1: pageNo;
		model.addAttribute("chPage", canHuiService.getPage(pageNo,query));
		model.addAttribute("query", query);
		return "canhui/canhui";
	}
	
	
	
	/**
	 * 参会报名申请
	 */
	@RequestMapping("apply")
	public ResultBean apply(HttpServletRequest request, HttpServletResponse response, String mobile, String email, String userName){
		if(StringUtils.isEmpty(mobile)){
			logger.info("参会报名，手机号为空");
			return (new ResultBean(-1,"手机号为空",null));
		}else if(StringUtils.isEmpty(userName)){
			logger.info("参会报名，用户名为空");
			return (new ResultBean(-1,"用户名为空",null));
		}else{
			TCanhui ch = canHuiService.getByMobile(mobile);
			if(ch!=null){
				logger.info("参会报名，重复报名，手机号==="+mobile);
				return (new ResultBean(-1,"您已报名，请勿重复提交信息",null));
			}else{
				TCanhui canHui = new TCanhui();
				canHui.setMobile( mobile);
				canHui.setEmail( email);
				canHui.setUserName( userName);
				boolean isSave = canHuiService.save(canHui);
				if(isSave){
					return (new ResultBean(0,"报名成功！",null));
				}else{
					return (new ResultBean(-1,"提交失败，请稍后尝试",null));
				}
			}
		}
	}
}
