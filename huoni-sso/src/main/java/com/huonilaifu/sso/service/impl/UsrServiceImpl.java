package com.huonilaifu.sso.service.impl;

import com.huonilaifu.sso.dao.UserMapper;
import com.huonilaifu.sso.model.User;
import com.huonilaifu.sso.model.UserExample;
import com.huonilaifu.sso.service.UsrService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: lirb
 * @date: 2019/4/15
 * @description:
 */
@Service
public class UsrServiceImpl implements UsrService {

    @Resource
    private UserMapper userMapper;

    public User selectByUsernameAndPassword(String username, String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isNotEmpty(users)&&users.size()==1){
            return users.get(0);
        }
        return null;
    }
}
