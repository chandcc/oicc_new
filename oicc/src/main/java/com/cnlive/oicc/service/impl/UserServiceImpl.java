package com.cnlive.oicc.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.oicc.entity.TUser;
import com.cnlive.oicc.mapper.TUserMapper;
import com.cnlive.oicc.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final TUserMapper tUserMapper;
    /**
     * 按照操作人的条件倒序时间查询
     *
     * @param pageNumber
     * @param pageSize
     * @param role
     * @return
     */
    public PageInfo<TUser> paginateByOperator(int pageNumber, int pageSize, String role) {
        List<TUser> userList = tUserMapper.paginate1(role);
        PageHelper.startPage(pageNumber, pageSize);
        PageInfo<TUser> tUserPageInfo = new PageInfo<>(userList);
        return tUserPageInfo;
    }

    /**
     * 倒序时间查询
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageInfo<TUser> paginate(int pageNumber, int pageSize) {
        List<TUser> userList = tUserMapper.paginate();
        PageHelper.startPage(pageNumber, pageSize);
        PageInfo<TUser> tUserPageInfo = new PageInfo<>(userList);
        return tUserPageInfo;
    }

    /**
     * 登陆用户保存或修改
     *
     * @param id
     * @param username
     * @param password
     * @param email
     * @param company
     * @param contacts
     * @param description
     * @return
     */
    public boolean saveOrUpdate(String id, String username, String password, String email, String company, String contacts, String description, String state) {
        boolean isTrue = false;
        if (StringUtils.isEmpty(id) || id == null) {
            TUser tUser = new TUser();
            tUser.setName(username);
            tUser.setName( username);
            tUser.setPassword( password);
//            tUser.setc("company", company)
            tUser.setContact( contacts);
            tUser.setRole( "user");
//            tUser.setCreateTime("description", description);
            tUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
            System.out.println("---regist-save----------");
            tUserMapper.insert(tUser);
        } else {
            TUser tUser = tUserMapper.selectByPrimaryKey(id);
            tUser.setName( username);
//            tUser.setco("company", company)
            tUser .setContact( contacts);
//            tUser.set("description", description);
            System.out.println("---regist-update----------");
            tUserMapper.updateByPrimaryKeySelective(tUser);
        }
        return isTrue;

    }

    /**
     * 获取用户昵称
     *
     * @param name
     * @return
     */
    public TUser getUser(String name) {
        TUser tUser = new TUser();
        tUser.setName(name);
        List<TUser> tUserList = tUserMapper.select(tUser);
        System.out.println("user-------" + tUserList.get(0));
        return tUserList.get(0);
    }

    /**
     * 用户是否存在
     *
     * @param name
     * @return
     */
    public boolean checkUser(String name) {
        TUser tUser = new TUser();
        tUser.setName(name);
        List<TUser> tUserList = tUserMapper.select(tUser);
        if (tUserList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 用户名和密码是否正确
     *
     * @param name
     * @param password
     * @return
     */
    public boolean checkUserAndPwd(String name, String password) {
        TUser tUser = new TUser();
        tUser.setName(name);
        tUser.setPassword(password);
        List<TUser> tUserList = tUserMapper.select(tUser);
        if (tUserList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * @param name
     * @param password
     * @return
     */
    public Map<String, Object> checkUser(String name, String password) {
        Map<String, Object> result = new HashMap<String, Object>();
        TUser tUser = new TUser();
        tUser.setName(name);
        tUser.setPassword(password);
        List<TUser> tUserList = tUserMapper.select(tUser);
        if (checkUser(name)) {
            if (checkUserAndPwd(name, password)) {
                if (tUserList.size() != 0) {
                    result.put("code", 0);
                    result.put("msg", "登陆成功");
                }
            } else {
                result.put("code", 2);
                result.put("msg", "密码错误");
            }
        } else {
            result.put("code", 1);
            result.put("msg", "账户不存在,请联系系统管理员");
        }
        return result;
    }


    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    public TUser getUserById(String id) {
        TUser tUser = tUserMapper.selectByPrimaryKey(id);

        return tUser;
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    public boolean delUserById(String id) {
        int i = tUserMapper.deleteByPrimaryKey(id);
        return i>0;
    }

    /**
     * 根据账户获取用户对象
     * @param username
     * @return
     */
    public TUser getUserByName(String username){
        return tUserMapper.getUserByName(username);
    }

}
