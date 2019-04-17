package com.huonilaifu.sso.service.impl;

import com.huonilaifu.sso.dao.UserMapper;
import com.huonilaifu.sso.model.User;
import com.huonilaifu.sso.model.UserExample;
import com.huonilaifu.sso.service.UsrService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lirb
 * @date: 2019/4/15
 * @description:
 */
@Service
public class UsrServiceImpl implements UsrService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;

    public User selectByUsernameAndPassword(String username, String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isNotEmpty(users)&&users.size()==1){
            User user = users.get(0);
            String token = "asdf1234";
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("username",user.getUsername());
            dataMap.put("password", user.getPassword());
            redisTemplate.opsForValue().set(token+"token",token);
            redisTemplate.opsForHash().putAll(token,dataMap);
            return users.get(0);
        }
        return null;
    }
}
