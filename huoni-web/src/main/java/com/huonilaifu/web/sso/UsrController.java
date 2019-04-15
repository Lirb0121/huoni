package com.huonilaifu.web.sso;

import com.huonilaifu.sso.model.User;
import com.huonilaifu.sso.service.UsrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author: lirb
 * @date: 2019/4/15
 * @description: 实现用户登录
 */
@Controller
@RequestMapping("/usr")
public class UsrController {

    @Resource
    private UsrService userService;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(name = "username")String username,
                              @RequestParam(name = "password")String password){
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.selectByUsernameAndPassword(username, password);
        if(user != null){
            modelAndView.setViewName("success");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
