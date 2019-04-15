package com.huonilaifu.sso.service;

import com.huonilaifu.sso.model.User;

/**
 * @author: lirb
 * @date: 2019/4/15
 * @descrption: 用户service
*/
public interface UsrService {

    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    User selectByUsernameAndPassword(String username, String password);

}
