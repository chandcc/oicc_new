package com.cnlive.oicc.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.cnlive.oicc.bean.ResultBean;
import com.cnlive.oicc.entity.TEmailType;
import com.cnlive.oicc.mapper.TEmailTypeMapper;
import com.cnlive.oicc.service.UserLogService;
import com.cnlive.oicc.utils.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户日志service
 *
 * @author fan xiao chun
 * @Date 2017年1月1日
 */
@AllArgsConstructor
@Service
@NoArgsConstructor
public class UserLogServiceImpl implements UserLogService {
    public static UserLogServiceImpl me;
    private TEmailTypeMapper tEmailTypeMapper;
    private Logger logger = Logger.getLogger(UserLogServiceImpl.class);

    /**
     * 设置用户登录cookie
     */
    public void setUserLoginCookie(Map<String, String> userInfo, HttpServletResponse response) throws UnsupportedEncodingException {

        setCookieOfCnliveUserId(UnifyPay.encrypt(userInfo.get("uid")), response);
        setCookieOfCnliveUserName(URLEncoder.encode(userInfo.get("nickName"), "utf-8"), response);
        setCookieOfCnliveUserSmallFacepath(userInfo.get("faceUrl"), response);
    }

    public void setCookieOfCnliveUserId(String userId, HttpServletResponse response) {
        Cookie idcookie = new Cookie("cnliveUserId", userId);
        //setDomain暂时设为localhost，这两个的作用刚看了一下博客https://www.jianshu.com/p/122606ffcc47
        idcookie.setDomain("localhost");
        idcookie.setPath("/");
        idcookie.setMaxAge(3600);
        response.addCookie(idcookie);
    }

    public void setCookieOfCnliveUserSmallFacepath(String cnliveUserSmallFacepath, HttpServletResponse response) {
        Cookie smallFacepathcookie = new Cookie("cnliveUserSmallFacepath", cnliveUserSmallFacepath);
        smallFacepathcookie.setDomain("localhost");
        smallFacepathcookie.setPath("/");
        smallFacepathcookie.setMaxAge(31536000);
        response.addCookie(smallFacepathcookie);
    }

    public void setCookieOfCnliveUserName(String cnliveUserName, HttpServletResponse response) {
        Cookie namecookie = new Cookie("cnliveUserName", cnliveUserName);
        namecookie.setDomain("localhost");
        namecookie.setPath("/");
        namecookie.setMaxAge(31536000);
        response.addCookie(namecookie);
    }

    public void delteUserLoginCookie(HttpServletResponse response) {
        Cookie idcookie = new Cookie("cnliveUserId", null);
        idcookie.setDomain("localhost");
        idcookie.setPath("/");
        idcookie.setMaxAge(0);
        response.addCookie(idcookie);

        Cookie smallFacepathcookie = new Cookie("cnliveUserSmallFacepath", null);
        smallFacepathcookie.setDomain("localhost");
        smallFacepathcookie.setPath("/");
        smallFacepathcookie.setMaxAge(0);
        response.addCookie(smallFacepathcookie);

        Cookie namecookie = new Cookie("cnliveUserName", null);
        namecookie.setDomain("localhost");
        namecookie.setPath("/");
        namecookie.setMaxAge(0);
        response.addCookie(namecookie);
    }


    /**
     * 发送登录请求(密码登录)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendLoginRequest(String userName, String userPwd, String uid, String frmId) throws Exception {
        Map<String, String> requestParams = new HashMap<String, String>();

        requestParams.put("appId", Constants.APPID);
        requestParams.put("userName", userName);
        requestParams.put("pwd", userPwd);
        requestParams.put("uuid", uid);
        requestParams.put("clientPlat", "w");

        if (StringUtils.isEmpty(frmId))
            frmId = "oicc";
        requestParams.put("frmId", frmId);

        String signUrl = UnifyPay.buildURL(Constants.API_LOGINURL, requestParams, Constants.APPKEY);

        String response = HttpReq.requestUrl(signUrl, "POST", null);
        logger.info("登录请求:" + signUrl + "===响应body:" + response);
        return JSON.parseObject(response, Map.class);
    }

    /**
     * 短信快捷登录
     */
    public Map<String, Object> sendMsgLoginRequest(String userName, String vCode, String uid, String frmId) throws Exception {
        Map<String, String> requestParams = new HashMap<String, String>();

        requestParams.put("appId", Constants.APPID);
        requestParams.put("userName", userName);
        requestParams.put("verificationCode", vCode);
        requestParams.put("uuid", uid);
        requestParams.put("clientPlat", "w");

        if (StringUtils.isEmpty(frmId))
            frmId = "oicc";
        requestParams.put("frmId", frmId);

        String signUrl = UnifyPay.buildURL(Constants.API_MSGLOGINURL, requestParams, Constants.APPKEY);
        String response = HttpReq.requestUrl(signUrl, "POST", null);
        logger.info("登录请求:" + signUrl + "===响应body:" + response);
        return JSON.parseObject(response, Map.class);
    }


    /**
     * 发送注册请求
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendRegistRequest(String mobile, String userPwd, String vcode, String uuid, String frmId) throws Exception {

        Map<String, String> requestParams = new HashMap<String, String>();

        requestParams.put("appId", Constants.APPID);
        requestParams.put("userName", mobile);
        requestParams.put("pwd", userPwd);
        requestParams.put("verificationCode", vcode);
        requestParams.put("uuid", uuid);
        requestParams.put("clientPlat", "w");
        if (!StringUtils.isEmpty(frmId))
            requestParams.put("frmId", frmId);

        String signUrl = UnifyPay.buildURL(Constants.API_REGISTURL, requestParams, Constants.APPKEY);

        String response = HttpReq.requestUrl(signUrl, "POST", null);
        logger.info("注册请求:" + signUrl + "===响应body:" + response);
        return JSON.parseObject(response, Map.class);

    }

    /**
     * 发送手机验证码
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendMobileVcode(String mobile) throws Exception {
        Map<String, String> requestParams = new HashMap<String, String>();

        requestParams.put("appId", Constants.APPID);
        requestParams.put("mobile", mobile);
        requestParams.put("clientPlat", "w");

        String signUrl = UnifyPay.buildURL(Constants.API_SENDVCODEURL, requestParams, Constants.APPKEY);

        String response = HttpReq.requestUrl(signUrl, "POST", null);
        logger.info("发送验证码请求:" + signUrl + "===响应body:" + response);
        return JSON.parseObject(response, Map.class);

    }


    /**
     * 重置密码，通过手机
     */
    public Map<String, String> resetPwd(String vCode, String userPwd, String mobile) throws Exception {

        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("appId", Constants.APPID);
        requestParams.put("mobile", mobile);
        requestParams.put("newPwd", userPwd);
        requestParams.put("verificationCode", vCode);
        requestParams.put("clientPlat", "w");

        String signUrl = UnifyPay.buildURL(Constants.API_RESETPWD, requestParams, Constants.APPKEY);

        String response = HttpReq.requestUrl(signUrl, "POST", null);

        logger.info("重置密码请求:" + signUrl + "===响应body:" + response);

        return JSON.parseObject(response, Map.class);
    }

    /**
     * 重置密码，通过邮箱
     */
    public Map<String, String> resetPwdByEmail(String userPwd, String email) throws Exception {

        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("appId", Constants.APPID);
        requestParams.put("email", email);
        requestParams.put("newPwd", userPwd);
        requestParams.put("clientPlat", "w");
        String signUrl = UnifyPay.buildURL(Constants.API_RESETPWDBYEMAIL, requestParams, Constants.APPKEY);

        String response = HttpReq.requestUrl(signUrl, "POST", null);

        logger.info("重置密码请求:" + signUrl + "===响应body:" + response);

        return JSON.parseObject(response, Map.class);
    }


    /**
     * 修改密码发送验证码
     */
    public Map<String, String> sendResetPwdVcode(String mobile) throws Exception {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("appId", Constants.APPID);
        requestParams.put("mobile", mobile);
        requestParams.put("clientPlat", "w");

        String signUrl = UnifyPay.buildURL(Constants.API_SENDRESETPWDVCODE, requestParams, Constants.APPKEY);

        String response = HttpReq.requestUrl(signUrl, "POST", null);

        logger.info("重置密码请求:" + signUrl + "===响应body:" + response);

        return JSON.parseObject(response, Map.class);

    }


    /**
     * 短信登录发送验证码
     */
    public Map<String, String> sendResetLoginMsgVcode(String mobile) throws Exception {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("appId", Constants.APPID);
        requestParams.put("mobile", mobile);
        requestParams.put("clientPlat", "w");
        String signUrl = UnifyPay.buildURL(Constants.API_SENDRESETLOGINMSGVCODE, requestParams, Constants.APPKEY);
        String response = HttpReq.requestUrl(signUrl, "POST", null);

        logger.info("短信登录请求:" + signUrl + "===响应body:" + response);

        return JSON.parseObject(response, Map.class);

    }

    /**
     * 通过邮箱注册，保存注册信息并发送激活邮件
     *
     * @param email
     * @param userPwd
     * @param nickname
     * @return
     */
    public ResultBean regByEmail(String email, String userPwd, String nickname) {
        TEmailType tEmailType = new TEmailType();
        tEmailType.setEmail(email);
        TEmailType tEmailType1 = tEmailTypeMapper.selectOne(tEmailType);
        if (tEmailType1 != null && Constants.ACTIVE == tEmailType1.getType()) {
            //邮箱已存在
            return new ResultBean(-1, "Mailbox already exists", null);
        }
        SendMail sendMail = new SendMail();
        String vcode = MD5.sign(email + UUID.randomUUID());
        sendMail.setTo(email);
        sendMail.setSubject("Helingeer Account registration");
/*		sendMail.setContent("尊敬的用户：<br/>您好，您已申请注册鄂市文创账户。请在24小时内点击这里激活账户："
				+ "http://oicc.cnlive.com/cnlive_oicc/api/emailEffect?email="+email+"&vcode="+vcode
				+"，如果您并未申请创建账户，请忽略此信息。</br>"
				+"<div style='float: right;'>鄂尔多斯国际文化创意大会</div>");*/
        sendMail.setContent("Dear users:<br/>Hello, you have applied for registration of the hicc accounts.lease click here to activate the account within 24 hours:"
                + "<a href= 'http://hicc.cnlive.com/cnlive_oicc/api/emailEffect?email=" + email + "&vcode=" + vcode + "'/>http://hicc.cnlive.com/cnlive_oicc/api/emailPwd?email=" + email + "&vcode=" + vcode + "</a>，if you do not apply for the creation of the account, please ignore this information.</br>"
                + "<div style='float: right;'>2020 Inner Mongolia Helinger new area international cultural and creative conference</div>");
        boolean success = sendMail.send();
        if (success) {
            if (tEmailType1 != null) {
                tEmailType1.setEmail(email);
                tEmailType1.setVcode(vcode);
                tEmailType1.setType(Constants.INACTIVE);
                tEmailType1.setUserPwd(userPwd);
                tEmailType1.setNickname(nickname);
                tEmailType1.setInsertDate(CommonUtils.getCurrentTimestamp());
                tEmailTypeMapper.updateByPrimaryKeySelective(tEmailType1);
            } else {
                tEmailType.setEmail(email);
                tEmailType.setVcode(vcode);
                tEmailType.setType(Constants.INACTIVE);
                tEmailType.setUserPwd(userPwd);
                tEmailType.setNickname(nickname);
                tEmailType.setInsertDate(CommonUtils.getCurrentTimestamp());
                tEmailTypeMapper.insert(tEmailType);
            }
        } else {
            //邮件发送失败，请稍等尝试
            return new ResultBean(-1, "Please wait for the email to fail", null);
        }
        //邮件发送成功，请前往激活
        return new ResultBean(0, "Email is successful, please go to activation", null);
    }

    /**
     * 重置密码，邮箱
     *
     * @param userPwd
     * @param email
     */
    public ResultBean resetPwd(String userPwd, String email) {
        SendMail sendMail = new SendMail();
        String vcode = MD5.sign(email + UUID.randomUUID());
        sendMail.setTo(email);
        sendMail.setSubject("Helingeer Password reset");
		/*sendMail.setContent("您申请了重置密码，点击这里完成重置http://oicc.cnlive.com/cnlive_oicc/api/emailPwd?email="
				+email+"&vcode="+vcode);*/
        sendMail.setContent("Dear users:<br/>Hello, you have applied for modification of the hicc account password.Please click here to confirm the change password within 24 hours:"
                + "<a href='http://hicc.cnlive.com/cnlive_oicc/api/emailPwd?email=" + email + "&vcode=" + vcode + "'>http://hicc.cnlive.com/cnlive_oicc/api/emailPwd?email=" + email + "&vcode=" + vcode + "</a>，if you do not apply for the password change, please ignore this information.</br>"
                + "<div style='float: right;'>2020 Inner Mongolia Helinger new area international cultural and creative conference</div>");
        boolean success = sendMail.send();
        if (success) {
            TEmailType tEmailType = new TEmailType();
            tEmailType.setEmail(email);
            TEmailType tEmailType1 = tEmailTypeMapper.selectOne(tEmailType);
            if (tEmailType1 == null) {
                tEmailType.setEmail(email);
                tEmailType.setUserPwd(userPwd);
                tEmailType.setType(Constants.INACTIVE);
                tEmailType.setVcode(vcode);
                tEmailType.setInsertDate(CommonUtils.getCurrentTimestamp());
                tEmailTypeMapper.insert(tEmailType);
            } else {
                tEmailType1.setEmail(email);
                tEmailType1.setUserPwd(userPwd);
                tEmailType1.setType(Constants.INACTIVE);
                tEmailType1.setVcode(vcode);
                tEmailType1.setInsertDate(CommonUtils.getCurrentTimestamp());
                tEmailTypeMapper.updateByPrimaryKeySelective(tEmailType1);
            }
        } else {
            //邮件发送失败，请稍等尝试
            return new ResultBean(-1, "Please wait for the email to fail", null);
        }
        //邮件已发送，请接收邮件并重置密码
        return new ResultBean(0, "The email has been sent. Please receive the email and reset the password", null);
    }

}
