package com.combile.remote;

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
    public Map<String, Object> queryByCode(String code) {
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        map.put("code", code);
        map.put("name", "xiaoming");
        map.put("age", random.nextInt(28));
        map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
        return map;
    }

    public List<Map<String, Object>> queryBatchCode(List<String> codes) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        codes.stream().forEach(t -> {
            Map<String, Object> map = new HashMap<>();
            Random random = new Random();
            map.put("code", t);
            map.put("name", "xiaoming");
            map.put("age", random.nextInt(28));
            map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
            mapList.add(map);
        });

        return mapList;
    }
}
