package com.combile.service;

import com.combile.remote.CombileRemoteService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @program: combile-request
 * @description:
 * @author: sch
 * @create: 2021-06-14 20:09
 **/
@Service
public class CombileRequestSimpleService {
    /**
     * 远程调用
     */
    @Autowired
    private CombileRemoteService combileRemoteService;

    public Map<String, Object> queryByCode(String code) throws ExecutionException, InterruptedException {
        //控制返回参数
        return combileRemoteService.queryByCode(code);
    }

}

