package com.huonilaifu.web.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: lirb
 * @date: 2019/4/1
 * @description:
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.GET,produces = {"application/json;charset=utf-8"})
    public String upload(){
        return "upload";
    }

}
