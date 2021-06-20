package com.combile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.combile.entity.TestTable;

import java.util.List;
import java.util.Map;

public interface TestTableService extends IService<TestTable> {
    List<TestTable> listTable(List<String> codes);


    TestTable getOneByCode(String code);

    Map<String, Object> getMapByCode(String code);
}
