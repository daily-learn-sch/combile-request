package com.combile;

import com.combile.service.CombileRequestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @program: sch-diy-parent
 * @description:
 * @author: sch
 * @create: 2021-06-13 22:07
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CombileRequestApplication.class})
public class HytrixDemoApplicationTest {
    long timeStart = 0l;

    @Before
    public void before() {
        System.out.println("开始测试...");
        timeStart = System.currentTimeMillis();
    }


    @After
    public void after() {
        System.out.println("线程结束，耗时:" + (System.currentTimeMillis() - timeStart) + "ms");
    }

    //模拟请求数量
    private static Integer THREAM_NUM = 1000;
    @Autowired
    private CombileRequestService commodityService;
    CountDownLatch countDownLatch = new CountDownLatch(THREAM_NUM);

    @Test
    public void benchmark() throws IOException, InterruptedException {
        //创建，并不是马上发起请求
        for (int i = 0; i < THREAM_NUM; i++) {
            final String code = "code-" + (i + 1);
            //多线程模拟用户查询请求
            Thread thread = new Thread(() -> {
                try {
                    //http请求，多线程调用这个方法
                    Map<String, Object> result = commodityService.queryByCode(code);
//                    System.out.println(Thread.currentThread().getName() + "查询结束，结果是:" + result);

                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "线程执行出现异常:" + e.getMessage());
                }
            });
            thread.setName("map-thread-" + code);
            thread.start();

            //减1,说明一个线程准备就绪
            countDownLatch.countDown();
        }

        //代码在这里等待，当countDownLatch为0，所有线程执行start方法
        countDownLatch.await();
        //主线程阻塞
        System.out.println("over...");
    }
}
