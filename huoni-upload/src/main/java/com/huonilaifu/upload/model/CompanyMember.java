package com.huonilaifu.upload.model;

public class CompanyMember {
    private Integer id;

    private String member;

    private String pinyin;

    private String phone;

    private String createTime;

    private String platAccount;

    private String identity;

    private String shiro;

    private String isIncumbency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member == null ? null : member.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getPlatAccount() {
        return platAccount;
    }

    public void setPlatAccount(String platAccount) {
        this.platAccount = platAccount == null ? null : platAccount.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getShiro() {
        return shiro;
    }

    public void setShiro(String shiro) {
        this.shiro = shiro == null ? null : shiro.trim();
    }

    public String getIsIncumbency() {
        return isIncumbency;
    }

    public void setIsIncumbency(String isIncumbency) {
        this.isIncumbency = isIncumbency == null ? null : isIncumbency.trim();
    }
}