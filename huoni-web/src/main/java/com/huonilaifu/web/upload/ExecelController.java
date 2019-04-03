package com.huonilaifu.web.upload;

import com.huonilaifu.commons.pinyin.Pinyin4j;
import com.huonilaifu.upload.util.ExcelUploadTypeEnum;
import com.huonilaifu.web.util.Result;
import com.huonilaifu.web.util.ResultStatusEnum;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lirb
 * @date: 2019/4/1
 * @description:
 */
@Controller
@RequestMapping("/execel")
public class ExecelController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    private final static String EXCEL_TEMPLATE_PATH = "/WEB-INF/template/";
    private final static String EXCEL_SUFFIX = ".xlsx";
    private final static String EXCEL_SUFFIX02 = ".xls";


    @ResponseBody
    @RequestMapping("/downExecelTemplateByType")
    public Object downExecelTemplateByType(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "type" ,required = true) Integer type) throws IOException {

        ExcelUploadTypeEnum excelUploadTypeEnum = ExcelUploadTypeEnum.getByType(type);

        if(excelUploadTypeEnum == null) {
            return new Result<Object>(ResultStatusEnum.EXCEL_TYPE_NOT_SUPPORT);
        }

        String realPath = request.getSession().getServletContext().getRealPath(EXCEL_TEMPLATE_PATH);
        String suffix = EXCEL_SUFFIX;
        File file = new File(realPath + excelUploadTypeEnum.getTable() + suffix);
        if(!file.exists()){
            suffix = EXCEL_SUFFIX02;
            file=new File(realPath + excelUploadTypeEnum.getTable() + suffix);
        }
        if(!file.exists()){
            return new Result<Object>(ResultStatusEnum.EXCEL_FILE_NOT_EXIST);
        }
        byte[] data = FileUtils.readFileToByteArray(file);

        // 输出到客户端
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + Pinyin4j.convertChineseToPinyin(excelUploadTypeEnum.getTable()) + suffix + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        return null;
    }


}
