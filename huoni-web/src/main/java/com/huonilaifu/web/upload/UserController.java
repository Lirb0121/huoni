package com.huonilaifu.web.upload;

import com.huonilaifu.upload.model.UserInfo;
import com.huonilaifu.upload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: lirb
 * @date: 2019/4/1
 * @description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/getAll" ,method = RequestMethod.GET,produces = {"application/json;charset=utf-8"})
    public  List<UserInfo> getAll(){
        List<UserInfo> userInfos = userService.selectAll();
        return userInfos;
    }
}
