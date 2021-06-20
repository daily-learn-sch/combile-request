package com.combile.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.combile.entity.TestTable;
import com.combile.mapper.TestTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: combile-request
 * @description:
 * @author: sch
 * @create: 2021-06-19 15:43
 **/
@Service
public class TestTableServiceImple extends ServiceImpl<TestTableMapper, TestTable> implements TestTableService {

    @Override
    public List<TestTable> listTable(List<String> codes) {
        QueryWrapper<TestTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("code", codes);
        return list(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listMap(List<String> codes) {
        QueryWrapper<TestTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("code", codes);
        return listMaps(queryWrapper);
    }

    @Override
    public TestTable getOneByCode(String code) {
        QueryWrapper<TestTable> queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", code);

        return getOne(queryWrapper);
    }

    @Override
    public Map<String, Object> getMapByCode(String code) {
        QueryWrapper<TestTable> queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", code);

        return getMap(queryWrapper);
    }
}
