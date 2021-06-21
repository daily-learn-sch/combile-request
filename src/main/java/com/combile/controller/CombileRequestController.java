package com.combile.controller;

import com.combile.service.CombileRequestSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @program: combile-request
 * @description:
 * @author: sch
 * @create: 2021-06-19 14:32
 **/
@RestController
@RequestMapping("combile")
public class CombileRequestController {
    @Autowired
    private CombileRequestSimpleService combileRequestSimpleService;

    @RequestMapping("hello/{code}")
    public Map<String, Object> hello(@PathVariable("code") String code) throws ExecutionException, InterruptedException {
        Map<String, Object> map = combileRequestSimpleService.queryByCode(code);
        System.out.println("查询结果是code-:" + map);
        return map;
    }
}
