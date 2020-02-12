package com.cnlive.oicc.controller;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.entity.TEnrol;
import com.cnlive.oicc.entity.TMember;
import com.cnlive.oicc.entity.TProduction;
import com.cnlive.oicc.service.EnrolService;
import com.cnlive.oicc.service.MemberService;
import com.cnlive.oicc.service.ProductionService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    EnrolService enrolService;
    @Autowired
    ProductionService productionService;
    private Logger logger = Logger.getLogger(MemberController.class);


    /**
     * 用户信息列表
     */
    @RequestMapping("/index/{pageNo}/{type}")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable int pageNo, @PathVariable String type, String is_upload, String mobile) {
        String tableName = "";
        String time = "";
        mobile = mobile == null ? "" : mobile;
        is_upload = is_upload == null ? "" : is_upload;
        if ("0".equals(type)) {
            tableName = "wcbd";
            time = "createDate_oicc";
        } else if ("1".equals(type)) {
            tableName = "ycip";
            time = "createDate_ip";
        }

        if ("2".equals(type)) {
            tableName = "jscy";
            time = "createDate_js";
            List<TEnrol> tEnrols = enrolService.findAll();
            List<TMember> allidNotInEnrol = memberService.findAllidNotInEnrol(tableName);
            model.addAttribute("enrolList", tEnrols);
            model.addAttribute("noEnrol", allidNotInEnrol);
//            setAttr("enrolList", Db.find("select * from t_enrol"));
//            setAttr("noEnrol", Db.find("select * from  t_member where " + tableName + "=1 and  id not in (select memberId from t_enrol)"));
        }

        HashMap<String, String> userMap = new HashMap<String, String>();
        userMap.put("mobile", mobile);
        userMap.put("type", type);
//        select * from t_production where type=
        TProduction tProduction = new TProduction();
        tProduction.setType(Integer.parseInt(type));
        List<TProduction> tProductionList = productionService.findallByAnyWay(tProduction);
        List<TMember> allMemberidNotInproduction = memberService.findAllidNotInproduction(tableName, type);
        model.addAttribute("productionList", tProductionList);
        model.addAttribute("noProduction", allMemberidNotInproduction);
        int pageSize = 15;
        if (StringUtils.isEmpty(is_upload)) {
            PageInfo<Map<String, Object>> paginate = memberService.paginate(pageNo, pageSize, tableName, time, mobile);
            model.addAttribute("page", paginate);
            userMap.put("is_upload", is_upload);
        } else {
            int upload = Integer.parseInt(is_upload);
            if ("2".equals(type)) {
                if (upload == 1) {
                    PageInfo<Map<String, Object>> paginate = memberService.paginate1(pageNo, pageSize, tableName, time, mobile);
                    model.addAttribute("page", paginate);
                } else {
                    PageInfo<Map<String, Object>> paginate = memberService.paginate2(pageNo, pageSize, tableName, time, mobile);
                    model.addAttribute("page", paginate);
                }
            } else {
                if (upload == 2) {//未上传用户
                    PageInfo<Map<String, Object>> paginate = memberService.paginate3(pageNo, pageSize, tableName, time, mobile,type);
                    model.addAttribute("page", paginate);
                } else {
                    PageInfo<Map<String, Object>> paginate = memberService.paginate4(pageNo, pageSize, tableName, time, mobile, upload,type);
                    model.addAttribute("page", paginate);
                }
            }
            userMap.put("is_upload", is_upload);
        }
        model.addAttribute("query", userMap);
        return "member/member";
    }


}
