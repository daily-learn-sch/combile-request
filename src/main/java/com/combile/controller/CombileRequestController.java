package com.combile.controller;

import com.combile.service.CombileRequestService;
import com.combile.service.CombileRequestSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("weimob")
public class CombileRequestController {
    @Autowired
    private CombileRequestService combileRequestService;
    @Autowired
    private CombileRequestSimpleService combileRequestSimpleService;

    @RequestMapping("hello")
    public Map<String, Object> hello(String code) throws ExecutionException, InterruptedException {
        return combileRequestSimpleService.queryByCode(code);
    }
}
