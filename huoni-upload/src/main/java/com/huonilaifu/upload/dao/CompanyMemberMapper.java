package com.huonilaifu.upload.dao;

import com.huonilaifu.upload.model.CompanyMember;
import com.huonilaifu.upload.model.CompanyMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMemberMapper {
    int countByExample(CompanyMemberExample example);

    int deleteByExample(CompanyMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CompanyMember record);

    int insertSelective(CompanyMember record);

    List<CompanyMember> selectByExample(CompanyMemberExample example);

    CompanyMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CompanyMember record, @Param("example") CompanyMemberExample example);

    int updateByExample(@Param("record") CompanyMember record, @Param("example") CompanyMemberExample example);

    int updateByPrimaryKeySelective(CompanyMember record);

    int updateByPrimaryKey(CompanyMember record);

    void truncateTable();
}