package com.huonilaifu.upload.service;

import com.huonilaifu.upload.util.ImportDataResponse;

import java.util.List;

public interface CompanyMemberService {

    ImportDataResponse importData(List<String[]> datas, boolean testOnly);
}
