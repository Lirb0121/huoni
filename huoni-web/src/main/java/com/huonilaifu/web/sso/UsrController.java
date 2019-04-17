package com.huonilaifu.web.sso;

import com.huonilaifu.sso.model.User;
import com.huonilaifu.sso.service.UsrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest req, HttpServletResponse response,
                              @RequestParam(name = "username") String username,
                              @RequestParam(name = "password") String password) throws ServletException, IOException {
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.selectByUsernameAndPassword(username, password);
        PrintWriter out = response.getWriter();
        if (user != null) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(60*30);
            session.setAttribute("user",user.getUsername());
            Cookie cookie = new Cookie("token","asdf1234");
            response.addCookie(cookie);
            req.getRequestDispatcher("/WEB-INF/jsp/upload.jsp").forward(req,response);
//            modelAndView.setViewName("upload");
//            return modelAndView;
        }else {
            out.write("<br/>欢迎你，登陆成功，3秒跳转首页");
            response.setHeader("refresh", "1;url=/WEB-INF/jsp/login.jsp");
        }
//        modelAndView.setViewName("login");
//        return modelAndView;
    }
}
