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

    //2，请求放入队列中
    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    //1，创建请求对象
    @Data
    class Request {
        private String code;
        private CompletableFuture<Map<String, Object>> future;
    }

    //3,写一个定时任务，第10ms执行一次 -- 多线程实现
    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            //目的：封装参数，调rpc批量接口
            int size = queue.size();
            if (size == 0) {
                return;
            }

            //3.1封装参数
            List<Request> requests = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                requests.add(queue.poll());
            }

            //3.2调外部接口
            List<String> codes = requests.stream().map(Request::getCode).collect(Collectors.toList());
            List<Map<String, Object>> responseList = combileRemoteService.queryBatchCode(codes);
            System.out.println("合并了:" + size + "个请求");

            //3.3进行一一返回...
            //a，通过唯一值确定当前结果属于谁
            //code - Map<String,Object>   1 -- {name6=六六1, name5=小强1, name4=如花1, name3=lili1, code=1, name9=, name8=, name7=纸巾1, name=xiaoming1, id=5503, name2=daqiang1}
            //map.get(request.getCode())
            Map<String, Map<String, Object>> resultMap = new HashMap<>();
            for (Map<String, Object> response : responseList) {
                resultMap.put(response.get("code").toString(), response);
            }

            //b，一一返回:有什么办法把一个线程中的值传到另外一个线程 future
            for (Request request : requests) {
                Map<String, Object> response = resultMap.get(request.getCode());
                request.getFuture().complete(response);
            }

        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    //做请求合并处理
    public Map<String, Object> queryByCode(String code) throws ExecutionException, InterruptedException {
        Request request = new Request();
        request.setCode(code);
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        request.setFuture(future);
        queue.add(request);

        //控制返回参数，拿到值过后再去返回。
//        return combileRemoteService.queryByCode(code);
        return future.get();//阻塞直到有返回值
    }

    public Map<String, Object> queryByCode1(String code) throws ExecutionException, InterruptedException {
        //控制返回参数
        return combileRemoteService.queryByCode(code);
    }

}

