package com.cnlive.oicc.controller;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.bean.ResultBean;
import com.cnlive.oicc.entity.TProduction;
import com.cnlive.oicc.entity.TUser;
import com.cnlive.oicc.service.MemberService;
import com.cnlive.oicc.service.ProductionService;
import com.cnlive.oicc.service.UserService;
import com.cnlive.oicc.service.impl.UserServiceImpl;
import com.cnlive.oicc.utils.CommonUtils;
import com.cnlive.oicc.utils.Constants;
import com.cnlive.oicc.utils.MD5;
import com.qiniu.util.Auth;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    MemberService memberService;
    @Autowired
    ProductionService productionService;
    private static Log logger = LogFactory.getLog(UserController.class);

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).removeAttribute("userSession");
        return "login";
    }
    @RequestMapping("/index")
    public String index() {
        return"index";
    }

    @RequestMapping("/tologin")
    @ResponseBody
    public Map tologin(Model model,HttpServletRequest request, HttpServletResponse response, String username, String password) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            result = userService.checkUser(username, MD5.sign(password));
            if (Integer.parseInt(String.valueOf(result.get("code"))) == 0) {
                TUser user = userService.getUser(username);
                request.getSession().setAttribute("userSession",user);
                result.put("role", user.getRole());
            }

        }
        return result;
    }


    /**
     * 上传作品前请求数据
     */
    @RequestMapping("/uploadFil/{type}")
    public ResultBean uploadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String type,int group,String title,String fileName) {
        Cookie[] cookies = request.getCookies();
        String userId = CommonUtils.getCookieValue("cnliveUserId", cookies);
        userId = memberService.getUserIDByEncryptID(userId);
        if (StringUtils.isEmpty(userId)) {
            return (new ResultBean(-1, "用户id为空", null));
        } else {
            TProduction production = productionService.getUpByUserId(userId, type);
            if (production != null) {
                logger.info("userId====" + userId + ",type====" + type + ",已上传成功，重复请求。。。。。。");
                return (new ResultBean(-1, "请勿重复上传作品", null));
            } else {
                String[] split = fileName.split("\\.");
                String suffix = split[split.length - 1];
                String domian = Constants.QINIU_DOMIAN;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                long date = new Date().getTime();
                String time = sdf.format(date);
                String[] strings = time.split("-");
                String path = Constants.QINIU_FILE_PATH + "/" + strings[0] + "/" + strings[1] + strings[2] + "/" + title + "_" + String.valueOf(date).substring(time.length() - 8, time.length()) + "." + suffix;
                boolean success = productionService.saveVideoSrc(type, domian, path, userId, group, title, Constants.NOTUPLOAD);
                if (success) {
                    return (new ResultBean(0, "内容创建成功", path));
                } else {
                    return (new ResultBean(-1, "内容保存失败", null));
                }
            }
        }
    }


    /**
     * 上传作品后，上传是否成功
     */
    @RequestMapping("/isUpFile/{type}")
    public ResultBean isUpFile(HttpServletRequest request, HttpServletResponse response,@PathVariable String type) {
        Cookie[] cookies = request.getCookies();
        String userId = CommonUtils.getCookieValue("cnliveUserId", cookies);
        userId = memberService.getUserIDByEncryptID(userId);
        if (StringUtils.isEmpty(userId)) {
            logger.info("作品更新状态，userId为空");
            return (new ResultBean(-1, "userId为空", null));
        } else {
            //已上传，更新文件上传状态
            TProduction production = productionService.getVideoByUserId(userId, type);
            if (production != null) {
                if (production.getIsUpload() == 1) {
                    logger.info("userId=" + userId + ",用户作品已上传成功，重复上传请求。。。。。。");
                    return (new ResultBean(-1, "请勿重复上传作品", null));
                } else {
                    int isUpload = production.getIsUpload();
                    production.setIsUpload( isUpload);
                    boolean isSuccess = productionService.update(production);
                    if (isSuccess) {
                        logger.info("userId=" + userId + ",用户作品信息状态更新成功");
                        return (new ResultBean(0, "状态更新成功", null));
                    } else {
                        logger.info("userId=" + userId + ",用户作品信息状态更新失败");
                        return (new ResultBean(-1, "状态更新失败", null));
                    }
                }

            } else {
                logger.info("userId=" + userId + ",用户作品信息未创建");
                return (new ResultBean(-1, "用户作品信息未创建", null));
            }
        }


    }
	
	
	/*@Clear(LoginInterceptor.class)
	public void download() {
		String id = getPara();
		Record production = ProductionService.service.getVideoByUserId(id);
		String path = production.get("file_path").toString();
		File file = new File(path);
		logger.info("下载文件，path====="+path+",存在==="+file.exists());
		renderFile(file);
	}*/


    @RequestMapping("/getToken")
    public ResultBean getToken(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        Map<String, String> map = new HashMap<String, String>();
        Auth auth = Auth.create(Constants.QINIU_ACCESS_KEY, Constants.QINIU_SECRE_KEY);
        String upToken = auth.uploadToken(Constants.QINIU_BUCKET);
        map.put("domian", Constants.QINIU_DOMIAN);
        map.put("token", upToken);
        return(new ResultBean(0, "成功", map));

    }


}
