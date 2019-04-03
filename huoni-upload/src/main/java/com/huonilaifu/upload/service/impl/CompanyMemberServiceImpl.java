package com.huonilaifu.upload.service.impl;

import com.huonilaifu.upload.dao.CompanyMemberMapper;
import com.huonilaifu.upload.model.CompanyMember;
import com.huonilaifu.upload.service.CompanyMemberService;
import com.huonilaifu.upload.util.ExcelUploadTypeEnum;
import com.huonilaifu.upload.util.ImportDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lirb
 * @date: 2019/4/3
 * @description: 企业成员
 */
@Service
public class CompanyMemberServiceImpl implements CompanyMemberService {
    private final static Logger logger = LoggerFactory.getLogger(CompanyMemberServiceImpl.class);

    @Autowired
    private CompanyMemberMapper companyMemberMapper;


    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = new ImportDataResponse();
        int size = datas.size();
        CompanyMember cm = null;
        if (!testOnly) {
            companyMemberMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.COMPANY_MEMBER, response, cellIndex)) {
                continue;
            }

            cm = new CompanyMember();
            cm.setMember(strArr[0]);
            cm.setPinyin(strArr[1]);
            cm.setPhone(strArr[2]);
            cm.setCreateTime(strArr[3]);
            cm.setPlatAccount(strArr[4]);
            cm.setIdentity(strArr[5]);
            cm.setShiro(strArr[6]);
            cm.setIsIncumbency(strArr[7]);

            try {
                if (!testOnly) {
                    companyMemberMapper.insertSelective(cm);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.COMPANY_MEMBER.getTable(), cellIndex, e.getMessage());
                continue;
            }
            response.incrSuccess();

        }
        response.setDone(true);

        return response;
    }
}
