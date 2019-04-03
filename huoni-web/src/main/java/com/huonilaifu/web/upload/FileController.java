package com.huonilaifu.web.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lirb
 * @date: 2019/4/3
 * @description:
 */
@Controller
@RequestMapping("/upload")
public class FileController {

    private final static Logger logger = LoggerFactory.getLogger(FileController.class);


    @RequestMapping("file")
    public ModelAndView file(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upload");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/fileUpdate", method = RequestMethod.POST)
    public Object upload(@RequestParam(value = "file", required = true) MultipartFile multipartFile ) {
        Map<String, Object> result = new HashMap<String, Object>();
        BufferedReader bufferedReader = null;
        boolean empty = multipartFile.isEmpty();
        try {
            if (!empty) {
                String originalFilename = multipartFile.getOriginalFilename();
                bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
                StringBuffer sb = new StringBuffer();
                while((bufferedReader.readLine()) != null){
                    sb.append(bufferedReader.readLine()+"\n");
                }
                result.put("code", 0);
                result.put("desc", "success");
                result.put("content",sb);
                result.put("originalFilename", originalFilename);

            } else {
                result.put("code", 1);
                result.put("desc", "文件为空");
            }
        } catch (IOException e) {
            logger.error("上传文件操作发生错误：{}", e.getMessage());
            result.put("code", 1);
            result.put("desc", "fail");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("关闭读取流发生错误：{}", e.getMessage());
                }
            }

            return result;
        }
    }
}
