package com.huonilaifu.upload.service.impl;

import com.huonilaifu.upload.dao.UserInfoMapper;
import com.huonilaifu.upload.model.UserInfo;
import com.huonilaifu.upload.service.UserService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lirb
 * @date: 2019/4/1
 * @description:
 */

@Service
public class UserServiceImpl implements UserService {

@Autowired
private UserInfoMapper userInfoMapper;



    public List<UserInfo> selectAll() {
        List<UserInfo> userInfos = userInfoMapper.selectAll();
        return userInfos;
    }

}
