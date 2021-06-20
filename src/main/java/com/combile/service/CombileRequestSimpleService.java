package com.combile.service;

import com.combile.remote.CombileRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @program: combile-request
 * @description:
 * @author: sch
 * @create: 2021-06-14 20:09
 **/
@Service
public class CombileRequestSimpleService {
    @Autowired
    private CombileRemoteService combileRemoteService;
    @Autowired
    private TestTableService testTableService;

    public Map<String, Object> queryByCode(String code) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            testTableService.getMapByCode(code);
        }
        return testTableService.getMapByCode(code);
    }

}

