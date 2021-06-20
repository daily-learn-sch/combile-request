package com.combile.remote;

import com.combile.service.TestTableService;
import com.combile.util.JsonUtil;
import com.combile.util.RedisTools;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: combile-request
 * @description:
 * @author: sch
 * @create: 2021-06-14 20:07
 **/
@Service
public class CombileRemoteService {
    @Autowired
    private TestTableService testTableService;
    @Autowired
    private RedisTools redisTools;

    private static final String CODE_KEY = "CODE_KEY";

    public Map<String, Object> queryByCode(String code) {
//        for (int i = 0; i < 10; i++) {
//            testTableService.getMapByCode(code);
//        }
        return testTableService.getMapByCode(code);
//        String key = String.format("%s%s", CODE_KEY, code);
//        String value = redisTools.get(key);
//        if (StringUtils.isNullOrEmpty(value)) {
//            Map<String, Object> resultMap = testTableService.getMapByCode(code);
//            redisTools.set(key, JsonUtil.toJSON(resultMap));
//        }
//
//        return JsonUtil.parse(value, Map.class);

    }

    public List<Map<String, Object>> queryBatchCode(List<String> codes) {
        return testTableService.listMap(codes);
    }
}
