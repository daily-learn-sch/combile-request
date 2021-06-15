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
public class CombileRequestService {
    @Autowired
    private CombileRemoteService combileRemoteService;

    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    @Data
    class Request {
        private String code;
        private CompletableFuture<Map<String, Object>> future;
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int size = queue.size();
            if (size == 0) {
                return;
            }

            //合并请求入参
            List<Request> requestList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                requestList.add(queue.poll());
            }
            System.out.println("合并了:" + requestList.size() + "条数据");

            //调用remote接口
            List<String> codes = requestList.stream().map(Request::getCode).collect(Collectors.toList());
            List<Map<String, Object>> mapList = combileRemoteService.queryBatchCode(codes);

            //找出每个返回值对应的请求 code map
            Map<String, Map<String, Object>> responseMap = new HashMap<>();
            for (Map<String, Object> map : mapList) {
                responseMap.put(map.get("code").toString(), map);
            }

            //返回
            for (Request request : requestList) {
                Map<String, Object> response = responseMap.get(request.getCode());
                request.getFuture().complete(response);
            }




        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public Map<String, Object> queryByCode(String code) throws ExecutionException, InterruptedException {
//        return combileRemoteService.queryByCode(code);
        Request request = new Request();
        request.setCode(code);
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        request.setFuture(future);
        queue.add(request);

        return future.get();
    }

}

