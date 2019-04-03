package com.huonilaifu.web.upload;


import com.huonilaifu.commons.ExcelReader;
import com.huonilaifu.commons.FileUtil;
import com.huonilaifu.commons.pinyin.Pinyin4j;
import com.huonilaifu.upload.service.CompanyMemberService;
import com.huonilaifu.upload.util.ExcelUploadTypeEnum;
import com.huonilaifu.upload.util.ImportDataResponse;
import com.huonilaifu.web.util.Result;
import com.huonilaifu.web.util.ResultStatusEnum;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Map;

/**
 * @author: lirb
 * @date: 2019/4/1
 * @description:  execel 下载 上传、读取功能
 */
@Controller
@RequestMapping("/execel")
public class ExecelController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    private final static String EXCEL_DATA_PATH = "/WEB-INF/excel";
    private final static String EXCEL_TEMPLATE_PATH = "/WEB-INF/template/";
    private final static String EXCEL_SUFFIX = ".xlsx";
    private final static String EXCEL_SUFFIX02 = ".xls";

    @Autowired
    private CompanyMemberService companyMemberService;

    /**
     * 下载模板
     * @param request
     * @param response
     * @param type
     * @return
     * @throws IOException
     */
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

    /**
     * 上传Execel 并验证
     * @param request
     * @param file
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "uploadExecel" ,method = RequestMethod.POST)
    public Object uploadExecel(HttpServletRequest request,
                               @RequestParam(value = "file" ,required = true)MultipartFile file,
                               @RequestParam(value="type",required = true)Integer type){
        String contentType = file.getContentType();
        String name = file.getName();
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        logger.warn("上传文件[表单名称:{},文件名称:{},文件类型:{},文件大小:{}byte]",
                new Object[] { name, originalFilename, contentType, String.valueOf(size) });

        // 判断文件类型
        if (!(FileUtil.isExcelContentType(contentType) && FileUtil.isExcelSuffixFile(originalFilename))) {
            return new Result<Object>(ResultStatusEnum.EXCEL_CONTENTYPE_ERROR);
        }

        // 判断上传的业务文件类型
        ExcelUploadTypeEnum typeEnum = ExcelUploadTypeEnum.getByType(type);
        if (typeEnum == null) {
            return new Result<Object>(ResultStatusEnum.EXCEL_TYPE_NOT_SUPPORT);
        }

        // 经过上面初步判断后，保存文件到本地
        String realPath = request.getSession().getServletContext().getRealPath(EXCEL_DATA_PATH);
        File saveFile = null;
        try {
            saveFile = FileUtil.saveFile(file.getInputStream(), realPath, originalFilename);
        } catch (IOException e1) {
            logger.debug("异常:" + e1.getMessage(), e1);
            return new Result<Object>(ResultStatusEnum.EXCEL_SAVE_FAIL);
        }

        ExcelReader excelReader = new ExcelReader();
        try {
            // 读取excel所有的数据
            List<String[]> excelContent = excelReader.readExcel(saveFile);
            // 判断数据和标题的正确性
            int dataRowSize = excelContent.size();
            if (dataRowSize < 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }
            if (!typeEnum.verifyTitleData(excelContent.get(0))) {
                return new Result<Object>(ResultStatusEnum.EXCEL_HEAD_VERIFY_FAIL);
            }
            if (dataRowSize == 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }

            ImportDataResponse importDataResponse = importData(typeEnum, excelContent.subList(1, dataRowSize), true);

            // 整理结果集并返回
            Result<Object> result = new Result<Object>();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("tmpFileName", saveFile.getName());
            data.put("type", typeEnum.getType());
            data.put("response", importDataResponse);
            result.setData(data);
            return result;
        } catch (EncryptedDocumentException  e) {
            logger.debug("异常:" + e.getMessage(), e);
            return new Result<Object>(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
        } catch ( InvalidFormatException e) {
            logger.debug("异常:" + e.getMessage(), e);
            return new Result<Object>(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
        } catch ( IOException e) {
            logger.debug("异常:" + e.getMessage(), e);
            return new Result<Object>(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
        }
    }

    /**
     * 导入excel数据
     *
     * @param request
     * @param fileName
     *            具体文件
     * @param type
     *            参考枚举类型com.erui.report.util.ExcelUploadTypeEnum中的值
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Object updateExcel(HttpServletRequest request,
                              @RequestParam(value = "fileName", required = true) String fileName,
                              @RequestParam(value = "type", required = true) Integer type) {
        Result<Object> result = new Result<Object>();


        // 判断上传的业务文件类型
        ExcelUploadTypeEnum typeEnum = ExcelUploadTypeEnum.getByType(type);
        if (typeEnum == null) {
            return result.setStatus(ResultStatusEnum.EXCEL_TYPE_NOT_SUPPORT);
        }

        String realPath = request.getSession().getServletContext().getRealPath(EXCEL_DATA_PATH);
        File file = new File(realPath, fileName);
        if (file.exists() && file.isFile()) {
            logger.info(String.format("导入数据到数据库{文件：%s,类型：%d}", fileName,type));
            System.out.println(String.format("导入数据到数据库{文件：%s,类型：%d}", fileName,type));

            ExcelReader excelReader = new ExcelReader();
            try {
                List<String[]> excelContent = excelReader.readExcel(file);
                if (!typeEnum.verifyTitleData(excelContent.get(0))) {
                    return result.setStatus(ResultStatusEnum.EXCEL_HEAD_VERIFY_FAIL);
                }
                ImportDataResponse importDataResponse = importData(typeEnum,
                        excelContent.subList(1, excelContent.size()), false);

                result.setData(importDataResponse);

                try {
                    // 删除数据导入成功的文件
                    FileUtils.forceDelete(file);
                } catch (IOException ex) {
                    logger.debug("异常:" + ex.getMessage(), ex);
                }
            } catch (EncryptedDocumentException e) {
                logger.debug("异常:" + e.getMessage(), e);
                return result.setStatus(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
            } catch (InvalidFormatException  e) {
                logger.debug("异常:" + e.getMessage(), e);
                return result.setStatus(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
            } catch ( IOException e) {
                logger.debug("异常:" + e.getMessage(), e);
                return result.setStatus(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
            }
        } else {
            return result.setStatus(ResultStatusEnum.EXCEL_FILE_NOT_EXIST);
        }

        return result;
    }
    /**
     * 选择具体使用什么业务类导入数据
     *
     * @param typeEnum
     * @param datas
     * @param testOnly
     *            true:只做数据测试 false:导入数据库
     */
    private ImportDataResponse importData(ExcelUploadTypeEnum typeEnum, List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = null;
        switch (typeEnum) {
            case COMPANY_MEMBER:
                logger.info("导入企业成员数据");
                response = companyMemberService.importData(datas, testOnly);
                break;
            default:
                response = new ImportDataResponse();
                response.setDone(true);
        }
        return response;
    }


}
