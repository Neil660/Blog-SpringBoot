package com.my.blog.website.service;

import com.my.blog.website.model.Vo.UserVo;

/**
 * Created by BlueT on 2017/3/3.
 */
public interface IUserService {

    /**
     * 保存用户数据
     *
     * @param userVo 用户数据
     * @return 主键
     */

    Integer insertUser(UserVo userVo);

    /**
     * 通过uid查找对象
     * @param uid
     * @return
     */
    UserVo queryUserById(Integer uid);

    /**
     * 通过username查找对象
     * @param username
     * @return
     */
    UserVo queryUserByName(String username);

    /**
     * 用戶登录
     * @param username
     * @param password
     * @return
     */
    UserVo login(String username, String password);

    /**
     * 用戶注册
     * @param userVo
     * @return
     */
    Integer sign(UserVo userVo);

    /**
     * 根据主键更新user对象
     * @param userVo
     * @return
     */
    void updateByUid(UserVo userVo);
}
