package com.cnlive.oicc.controller;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.bean.ResultBean;
import com.cnlive.oicc.entity.TEmailPwd;
import com.cnlive.oicc.entity.TEmailType;
import com.cnlive.oicc.entity.TMember;
import com.cnlive.oicc.service.EmailPwdService;
import com.cnlive.oicc.service.EmailTypeService;
import com.cnlive.oicc.service.MemberService;
import com.cnlive.oicc.service.UserLogService;
import com.cnlive.oicc.utils.CommonUtils;
import com.cnlive.oicc.utils.Constants;
import com.cnlive.oicc.utils.UnifyPay;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * API接口 api
 */
@Controller
@RequestMapping("/api")
public class ApiServiceController {
    @Autowired
    UserLogService userLogService;
    @Autowired
    MemberService memberService;
    @Autowired
    EmailTypeService emailTypeService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;//用来输出页面的，一个接口既要返回数据又要返回页面的时候用
    @Autowired
    EmailPwdService emailPwdService;

    private Logger logger = Logger.getLogger(ApiServiceController.class);

    @RequestMapping("/index")
    public String index() {
        return "api/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 统一异常处理页面
     */
    @RequestMapping("/500")
    public String error() {
        return "common/500";
    }

    @RequestMapping("/getToken")
    @ResponseBody
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

    /**
     * 登录
     */
    @RequestMapping("/loginValidate")
    @ResponseBody
    public ResultBean loginValidate(HttpServletRequest request, HttpServletResponse response) {
        String userName = null;
        String userId = null;
        try {
            userName = request.getParameter("userName");
            String userPwd = request.getParameter("userPwd");
            Cookie[] cookies = request.getCookies();
            String uid = CommonUtils.getCookieValue("uid", cookies) != null ? CommonUtils.getCookieValue("uid", cookies) : "oicc";
            String frmId = request.getParameter("frmId");
            String vCode = request.getParameter("vCode");
            String type = request.getParameter("type");
            Map<String, Object> resMap = new HashMap<String, Object>();
            String errcode = "";
            String errorMsg = "";
            Date date1 = new Date();
            long time1 = date1.getTime();
            if (StringUtils.isEmpty(vCode)) {//密码登录
                resMap = userLogService.sendLoginRequest(userName, userPwd, uid, frmId);
                errcode = String.valueOf(resMap.get("errorCode"));
                errorMsg = String.valueOf(resMap.get("errorMessage"));
            } else {//短信快捷登录
                resMap = userLogService.sendMsgLoginRequest(userName, vCode, uid, frmId);
                errcode = String.valueOf(resMap.get("errorCode"));
                errorMsg = String.valueOf(resMap.get("errorMessage"));
            }
            Date date2 = new Date();
            long time2 = date2.getTime();
            logger.info("登录请求时间=====" + (time2 - time1));
            Map<String, String> data = (Map<String, String>) resMap.get("data");
            //登录成功
            if ("0".equals(errcode)) {
                userLogService.setUserLoginCookie(data, response);
                userId = data.get("uid");
                String token = data.get("token");
                TMember tmember = new TMember();
                tmember.setId(Integer.parseInt(userId));
                tmember.setToken(token);
                tmember.setMobile(userName);
                tmember.setEncryptID(UnifyPay.encrypt(userId));
                tmember.setNickName(URLDecoder.decode(data.get("nickName"), "utf-8"));
                if ("0".equals(type)) {
                    tmember.setWcbd(1);
                } else if ("1".equals(type)) {
                    tmember.setYcip(1);
                } else if ("2".equals(type)) {
                    tmember.setJscy(1);
                }
                memberService.saveOrUpdate(tmember, type);
                return new ResultBean(0, "登录成功", null);
            } else {
                return new ResultBean(-1, errorMsg, null);
            }
        } catch (Exception e) {
            logger.error("登录异常,用户名:" + userName, e);
            e.printStackTrace();
            return new ResultBean(-1, "登录异常", null);
        }

    }


    /**
     * 验证是否登录
     */
    @RequestMapping("/isLogin/{type}")
    @ResponseBody
    public ResultBean isLogin(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) {
        String uid = "";
        try {
            Cookie[] cookies = request.getCookies();
            uid = CommonUtils.getCookieValue("cnliveUserId", cookies);
            if (uid != null && uid !=""){
                TMember member = memberService.findByEncryptID(uid);
                if (member != null) {
                    Object tableValue = null;
                    if (!StringUtils.isEmpty(type)) {
                        String tableName = "";
                        if ("0".equals(type)) {
                            tableName = "wcbd";
                            tableValue = member.getWcbd();
                            if (tableValue == null || "0".equals(tableValue.toString())) {
                                member.setWcbd(1);
                                memberService.saveOrUpdate(member, type);
                            }
                        } else if ("1".equals(type)) {
                            tableName = "ycip";
                            tableValue = member.getYcip();
                            if (tableValue == null || "0".equals(tableValue.toString())) {
                                member.setYcip(1);
                                memberService.saveOrUpdate(member, type);
                            }
                        } else if ("2".equals(type)) {
                            tableName = "jscy";
                            tableValue = member.getJscy();
                            if (tableValue == null || "0".equals(tableValue.toString())) {
                                member.setJscy(1);
                                memberService.saveOrUpdate(member, type);
                            }
                        }

                    }
                    return new ResultBean(0, "已登录", member);
                } else {
                    return new ResultBean(-1, "未登录", null);
                }
            }
            return new ResultBean(-1, "未登录", null);
        } catch (Exception e) {
            logger.error("验证登录异常,token=:" + uid, e);
            e.printStackTrace();
            return new ResultBean(-1, "验证登录异常", null);
        }

    }


    /**
     * 登出
     */
    @RequestMapping("/outLogin")
    @ResponseBody
    public ResultBean outLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            userLogService.delteUserLoginCookie(response);
            return new ResultBean(0, "登出成功", null);
        } catch (Exception e) {
            logger.error("登出异常", e);
            return new ResultBean(-1, "登出异常", null);
        }

    }


    /**
     * 验证注册手机号
     */
    @RequestMapping("/validateMobile")
    @ResponseBody
    public ResultBean validateMobile(HttpServletRequest request, HttpServletResponse response, String mobile) {

        try {
            if (StringUtils.isEmpty(mobile))
                mobile = String.valueOf(request.getSession().getAttribute("userName"));

            Map<String, Object> map = userLogService.sendMobileVcode(mobile);
            String errorCode = String.valueOf(map.get("errorCode"));
            String errorMsg = String.valueOf(map.get("errorMessage"));

            if ("0".equals(errorCode)) {
                request.getSession().setAttribute("userName", mobile);
                request.getSession().setAttribute("registStep", "2");
                return new ResultBean(0, "发送验证码成功", null);
            } else {
                return new ResultBean(-1, errorMsg, null);
            }
        } catch (Exception e) {
            logger.error("验证手机号失败,mobile:" + mobile, e);
            return new ResultBean(-1, "服务异常，请稍后重试", null);
        }
    }

    /**
     * 注册
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/registValidate")
    @ResponseBody
    public ResultBean registValidate(HttpServletRequest request, HttpServletResponse response, String type, String vCode, String userPwd, String mobile, String uuid, String frmId, String nickname) {
        try {
            if (Constants.EMAIL_TYPE.equals(type)) {
                return userLogService.regByEmail(mobile, userPwd, nickname);
            } else {
                Map<String, Object> reponse = userLogService.sendRegistRequest(mobile, userPwd, vCode, uuid, frmId);
                String errorCode = String.valueOf(reponse.get("errorCode"));
                String errorMsg = String.valueOf(reponse.get("errorMessage"));

                if ("0".equals(errorCode)) {
                    Map<String, String> data = (Map<String, String>) reponse.get("data");
                    userLogService.setUserLoginCookie(data, response);// 设置cookie

                    // 保存会员信息
                    Integer userId = Integer.parseInt(data.get("uid"));
                    TMember tMember = new TMember();
                    tMember.setId(userId);
                    tMember.setNickName(nickname);
                    tMember.setToken(data.get("token"));
                    tMember.setMobile(data.get("mobile"));

                    memberService.saveOrUpdate(tMember, null);

                    request.getSession().setAttribute("registStep", "3");

                    return new ResultBean(0, "注册成功", null);
                } else {
                    return new ResultBean(-1, errorMsg, null);
                }
            }

        } catch (Exception e) {
            logger.error("注册失败,mobile:" + mobile, e);
            return new ResultBean(-1, "服务异常，请稍后重试", null);
        }
    }


    /**
     * 重置密码
     */
    @RequestMapping("/resetPwd")
    @ResponseBody
    public ResultBean resetPwd(HttpServletRequest request, HttpServletResponse response, String mobile, String vCode, String userPwd, String type) {
        try {
            if (Constants.EMAIL_TYPE.equals(type)) {
                return userLogService.resetPwd(userPwd, mobile);
            } else {
                if (StringUtils.isEmpty(mobile))
                    mobile = String.valueOf(request.getSession().getAttribute("userName"));

                Map<String, String> map = userLogService.resetPwd(vCode, userPwd, mobile);

                String errorCode = map.get("errorCode");
                String errorMsg = map.get("errorMessage");

                if ("0".equals(errorCode)) {
                    userLogService.delteUserLoginCookie(response);
                    return new ResultBean(0, "重置密码成功", null);

                } else {
                    return new ResultBean(-1, errorMsg, null);
                }
            }


        } catch (Exception e) {
            logger.error("修改密码失败,mobile:" + mobile, e);
            return new ResultBean(-1, "服务异常,请稍后重试", null);
        }
    }

    /**
     * 发送重置密码手机验证码
     */
    @RequestMapping("/sendResetPwdVcode")
    @ResponseBody
    public ResultBean sendResetPwdVcode(HttpServletRequest request, HttpServletResponse response, String mobile) {
        try {
            if (StringUtils.isEmpty(mobile))
                mobile = String.valueOf(request.getSession().getAttribute("userName"));
            Map<String, String> map = userLogService.sendResetPwdVcode(mobile);
            String errorCode = map.get("errorCode");
            String errorMsg = map.get("errorMessage");

            if ("0".equals(errorCode))
                return new ResultBean(0, "发送成功", null);
            else
                return new ResultBean(-1, errorMsg, null);

        } catch (Exception e) {
            logger.error("重置密码发送验证码失败,mobile:" + mobile, e);
            return new ResultBean(-1, "服务异常,请稍后重试", null);
        }
    }

    /**
     * 发送短信快捷登录手机验证码
     */
    @RequestMapping("/sendResetMsgLoginVcode")
    public ResultBean sendResetMsgLoginVcode(HttpServletRequest request, HttpServletResponse response, String mobile) {
        try {
            if (StringUtils.isEmpty(mobile))
                mobile = String.valueOf(request.getSession().getAttribute("userName"));
            Map<String, String> map = userLogService.sendResetLoginMsgVcode(mobile);
            String errorCode = map.get("errorCode");
            String errorMsg = map.get("errorMessage");

            if ("0".equals(errorCode))
                return new ResultBean(0, "发送成功", null);
            else
                return new ResultBean(-1, errorMsg, null);

        } catch (Exception e) {
            logger.error("短信登录发送验证码失败,mobile:" + mobile, e);
            return new ResultBean(-1, "服务异常,请稍后重试", null);
        }
    }

    /**
     * 激活邮箱，注册
     */
    @RequestMapping("/emailEffect")
    @ResponseBody
    public String emailEffect(HttpServletRequest request, HttpServletResponse response, String email, String vcode, Model model) {
        try {
            if (StringUtils.isEmpty(email) || StringUtils.isEmpty(vcode)) {
                //setAttr("msg","激活链接无效，请重新申请注册");
                request.setAttribute("msg", "The activation link is invalid, please re-apply for registration");
            } else {
                long date = new Date().getTime() - 1000 * 3600 * 24L;
                Timestamp timestamp = new Timestamp(date);
                TEmailType temailType = emailTypeService.findTemailType(email, vcode, timestamp);
                if (temailType != null) {
                    if (temailType.getType() == Constants.ACTIVE) {
                        //setAttr("msg","您的账号已激活，请勿重复请求");
                        request.setAttribute("msg", "Your account has been activated. Please do not repeat the request");
                    } else {

                        Cookie[] cookies = request.getCookies();
                        String uid = CommonUtils.getCookieValue("uid", cookies) != null ? CommonUtils.getCookieValue("uid", cookies) : "oicc";
                        Map<String, Object> reponse = userLogService.sendRegistRequest(email, temailType.getUserPwd(), null, uid, "oicc");
                        String errorCode = String.valueOf(reponse.get("errorCode"));
                        //	String errorMsg = String.valueOf(reponse.get("errorMessage"));
                        if ("0".equals(errorCode)) {
                            Map<String, String> data = (Map<String, String>) reponse.get("data");
                            userLogService.setUserLoginCookie(data, response);// 设置cookie
                            // 保存会员信息
                            Integer userId = Integer.parseInt(data.get("uid"));
                            TMember tMember = new TMember();
                            tMember.setId(userId);
                            tMember.setNickName(temailType.getNickname());
                            tMember.setToken(data.get("token"));
                            tMember.setMobile(email);
                            memberService.saveOrUpdate(tMember, null);
                            request.getSession().setAttribute("registStep", "3");
                            //更新邮箱激活状态
                            temailType.setType(Constants.ACTIVE);
                            boolean update = emailTypeService.update(temailType);
                            if (update == true) {
                                request.setAttribute("msg", "Activate the success");
                            }
                        } else {
                            String msg = getMsg(errorCode);
                            request.setAttribute("msg", msg);
                        }
                    }
                } else {
                    request.setAttribute("msg", "The activation link is invalid, please re-apply for registration");
                }

            }
            IWebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
            return thymeleafViewResolver.getTemplateEngine().process("emailMsg", ctx);
        } catch (Exception e) {
            return "{\"code\":\"-1\",\"message\":\"Service exception, please try again later\",\"data\" :\" \"}";
        }
    }


    /**
     * 激活邮箱，重置密码
     */
    @RequestMapping("/emailPwd")
    @ResponseBody
    public String emailPwd(HttpServletRequest request, HttpServletResponse response, String email, String vcode, Model model) {
        try {
            if (StringUtils.isEmpty(email) || StringUtils.isEmpty(vcode)) {
                //setAttr("msg","该链接无效");
                request.setAttribute("msg", "The activation link is invalid, please re-apply for registration");
            } else {
                long date = new Date().getTime() - 1000 * 3600 * 24L;
                Timestamp timestamp = new Timestamp(date);
                TEmailPwd temailPwd = emailPwdService.findTemailPwd(email, vcode, timestamp);
                if (temailPwd != null) {
                    if (temailPwd.getType() == Constants.ACTIVE) {
                        //setAttr("msg","您的密码已重置，请勿重复请求");
                        request.setAttribute("msg", "Your password has been reset. Do not repeat the request");
                    } else {
                        Map<String, String> map = userLogService.resetPwdByEmail(temailPwd.getUserPwd(), email);
                        String errorCode = map.get("errorCode");
                        //	String errorMsg = response.get("errorMessage");
                        if ("0".equals(errorCode)) {
                            userLogService.delteUserLoginCookie(response);
                            temailPwd.setType(Constants.ACTIVE);
                            emailPwdService.update(temailPwd);
                            //setAttr("msg","重置密码成功");
                            request.setAttribute("msg", "Reset password success");
                        } else {
                            String msg = getMsg(errorCode);
                            request.setAttribute("msg", msg);
                        }
                    }
                } else {
                    //setAttr("msg","链接无效");
                    request.setAttribute("msg", "The activation link is invalid, please re-apply for registration");
                }

            }
            IWebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
            return thymeleafViewResolver.getTemplateEngine().process("emailMsg", ctx);
        } catch (Exception e) {
            return "{\"code\":\"-1\",\"message\":\"Service exception, please try again later\",\"data\" :\" \"}";
        }
    }


    /**
     * 用户云接口，通过code获取英文提示
     *
     * @param code
     * @return
     */
    public static String getMsg(String code) {
        String result = "";
        switch (code) {
            //邮箱格式错误
            case "41017":
                result = "Email format error";
                break;
            //密码为6-20位字母或数字
            case "41004":
                result = "Passwords are 6-20 letters or Numbers";
                break;
            //密码不可与账号相同
            case "41051":
                result = "The password must not be the same as the account number";
                break;
            //密码不可为连续的字符串
            case "41052":
                result = "Passwords must not be consecutive strings";
                break;
            //密码不可为连续相同的字符串
            case "41053":
                result = "The password must not be the same string";
                break;
            //该邮箱未注册
            case "41054":
                result = "This mailbox is not registered";
                break;
            //用户名为手机号或邮箱
            case "41001":
                result = "The username must be the mailbox";
                break;
            //邮箱不能超过50位
            case "41003":
                result = "The mailbox must not exceed 50";
                break;
            //该邮箱已存在
            case "41007":
                result = "This mailbox already exists";
                break;
            //该昵称已存在
            case "41008":
                result = "The nickname already exists";
                break;
            default:
                result = "The activation link is invalid, please re-apply for registration";
                break;
        }

        return result;
    }


}
