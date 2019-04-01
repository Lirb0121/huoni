package com.huonilaifu.upload.service;

import com.huonilaifu.upload.model.UserInfo;

import java.util.List;

public interface UserService {

    /**
     * 查询所有用户
     * @return
     */
    List<UserInfo> selectAll();
}
