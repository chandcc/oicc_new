package com.cnlive.oicc.service;

import com.cnlive.oicc.bean.ResultBean;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 用户日志service
 *
 * @author fan xiao chun
 * @Date 2017年1月1日
 */
public interface UserLogService {

    /**
     * 设置用户登录cookie
     */
    void setUserLoginCookie(Map<String, String> userInfo, HttpServletResponse response) throws UnsupportedEncodingException;


    void setCookieOfCnliveUserId(String userId, HttpServletResponse response);

    void setCookieOfCnliveUserSmallFacepath(String cnliveUserSmallFacepath, HttpServletResponse response);

    void setCookieOfCnliveUserName(String cnliveUserName, HttpServletResponse response);

    void delteUserLoginCookie(HttpServletResponse response);


    /**
     * 发送登录请求(密码登录)
     */
    @SuppressWarnings("unchecked")
    Map<String, Object> sendLoginRequest(String userName, String userPwd, String uid, String frmId) throws Exception;

    /**
     * 短信快捷登录
     */
    Map<String, Object> sendMsgLoginRequest(String userName, String vCode, String uid, String frmId) throws Exception;

    /**
     * 发送注册请求
     */
    @SuppressWarnings("unchecked")
    Map<String, Object> sendRegistRequest(String mobile, String userPwd, String vcode, String uuid, String frmId) throws Exception;

    /**
     * 发送手机验证码
     */
    @SuppressWarnings("unchecked")
    Map<String, Object> sendMobileVcode(String mobile) throws Exception;


    /**
     * 重置密码，通过手机
     */
    Map<String, String> resetPwd(String vCode, String userPwd, String mobile) throws Exception;

    /**
     * 重置密码，通过邮箱
     */
    Map<String, String> resetPwdByEmail(String userPwd, String email) throws Exception;


    /**
     * 修改密码发送验证码
     */
    Map<String, String> sendResetPwdVcode(String mobile) throws Exception;


    /**
     * 短信登录发送验证码
     */
    Map<String, String> sendResetLoginMsgVcode(String mobile) throws Exception;

    /**
     * 通过邮箱注册，保存注册信息并发送激活邮件
     *
     * @param email
     * @param userPwd
     * @param nickname
     * @return
     */
    ResultBean regByEmail(String email, String userPwd, String nickname);

    /**
     * 重置密码，邮箱
     *
     * @param userPwd
     * @param email
     */
    ResultBean resetPwd(String userPwd, String email);

}
