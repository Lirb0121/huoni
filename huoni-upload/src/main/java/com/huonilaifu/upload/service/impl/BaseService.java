package com.huonilaifu.upload.service.impl;

import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: lirb
 * @date: 2019/4/2
 * @description:
 */
public class BaseService<E> {
    protected E sqlMapper;
    protected SqlSessionTemplate sqlSession;
    private Class<E> classType;

    public BaseService() {
        Class<? extends BaseService> class1 = this.getClass();
        Type genericSuperclass = class1.getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        classType = (Class<E>) actualTypeArguments[0];
    }

    @Resource(name = "sqlSession")
    public void setWriteMapper(SqlSessionTemplate sqlSession) {
        sqlSession = sqlSession;
        this.sqlMapper = sqlSession.getMapper(classType);
    }

}
