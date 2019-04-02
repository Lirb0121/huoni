package com.huonilaifu.web.upload;

import com.huonilaifu.upload.model.UserInfo;
import com.huonilaifu.upload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: lirb
 * @date: 2019/4/1
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getAll")
    public Object getAll(){
        List<UserInfo> userInfos = userService.selectAll();
        return userInfos;
    }
}
