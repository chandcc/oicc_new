package com.cnlive.oicc.controller;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.bean.ResultBean;
import com.cnlive.oicc.entity.TProduction;
import com.cnlive.oicc.service.MemberService;
import com.cnlive.oicc.service.ProductionService;
import com.cnlive.oicc.utils.CommonUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 参赛作品管理
 */
@Controller
@RequestMapping("/production")
public class ProductionController {
    private Logger logger = Logger.getLogger(ProductionController.class);
    @Autowired
    ProductionService productionService;
    @Autowired
    MemberService memberService;
    @RequestMapping("/index/{type0}/{type1}")
    public String index(Model Model,HttpServletRequest request, HttpServletResponse response, String mobile, String group_id, @PathVariable int type0, @PathVariable String type1) {
        HashMap<String, String> userMap = new HashMap<String, String>();
        request.getSession().setAttribute("query", null);
        userMap.put("mobile", mobile);
        userMap.put("group", group_id);
        userMap.put("type", type1);
        PageInfo<TProduction> page = productionService.getAdminIndexByPage(type0, mobile, group_id, type1);
        Model.addAttribute("videoPage",page);
        Model.addAttribute("query",userMap);
        return "production/production";
    }


    /**
     * 获取用户提交作品
     */
    @RequestMapping("/getProduction")
    public Object getProduction(HttpServletRequest request, HttpServletResponse response,String type) {
        Cookie[] cookies = request.getCookies();
        String userId = CommonUtils.getCookieValue("cnliveUserId", cookies);
        logger.info("加密userID=====" + userId);
        userId = memberService.getUserIDByEncryptID(userId);
        logger.info("查询获取userID=====" + userId);
        if (!StringUtils.isEmpty(userId)) {
            TProduction production = productionService.getUpByUserId(userId, type);
            if (production != null) {
                return (production);
            } else {
                return (new ResultBean(-1, "未上传作品", null));
            }
        } else {
            return (new ResultBean(-1, "未登录", null));
        }

    }


    /**
     * 删除作品(初始化)
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(HttpServletRequest request, HttpServletResponse response,String id) {
        try {
            if (productionService.del(id)) {
                return ("0");
            } else {
                return ("-1");
            }
        } catch (Exception e) {
            return ("-1");
        }
    }
}
