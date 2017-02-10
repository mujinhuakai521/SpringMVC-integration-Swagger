package com.albert.spring.mybatis.web;

import com.albert.spring.mybatis.entity.User;
import com.albert.spring.mybatis.service.UserService;
import com.albert.spring.mybatis.util.HttpTookit;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class TestUserAction {

    public static final String HOST = "http://localhost:8080";
    public static final String PROJECT = "mybatis";

    public String url(String relatePath) {
        if (relatePath.startsWith("/"))
            return HOST + "/" + PROJECT + relatePath;
        return HOST + "/" + PROJECT + "/" + relatePath;
    }

    /**
     * web层调用，service层不加锁
     * 必须先使用 mvn jetty:run启动项目再执行测试
     *
     * 实测会出现重复插入
     */
    @Test
    @Ignore
    public void testCheckAndInsert() throws Throwable{
        User user = new User();
        user.setAccount("albert");
        user.setName("cyh");
        //Runner数组，想当于并发多少个。
        TestRunnable[] trs = new TestRunnable[100];
        for(int i=0;i<trs.length;i++){
            trs[i] = new CheckAndInsertThread(user);
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 开发并发执行数组里定义的内容
        mttr.runTestRunnables();
    }

    /**
     * web层调用，service层加锁
     * 必须先使用 mvn jetty:run启动项目再执行测试
     *
     * 实测会出现重复插入
     */
    @Test
    public void testCheckAndInsertSync() throws Throwable{
        User user = new User();
        user.setAccount("albert");
        user.setName("cyh");
        //Runner数组，想当于并发多少个。
        TestRunnable[] trs = new TestRunnable[10];
        for(int i=0;i<trs.length;i++){
            trs[i] = new CheckAndInsertSyncThread(user);
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 开发并发执行数组里定义的内容
        mttr.runTestRunnables();
    }


    private class CheckAndInsertThread extends TestRunnable {

        private User user;

        public CheckAndInsertThread(User user){
            super();
            this.user = user;
        }

        @Override
        public void runTest() throws Throwable {
            Map<String, String> param = new HashMap<String, String>();
            param.put("account",user.getAccount());
            param.put("name", user.getName());
            String result = HttpTookit.doPost(url("/add"), param, "UTF-8");
            System.out.println(result);
        }
    }

    private class CheckAndInsertSyncThread extends TestRunnable{

        private User user;

        public CheckAndInsertSyncThread(User user){
            super();
            this.user = user;
        }

        @Override
        public void runTest() throws Throwable {
            Map<String, String> param = new HashMap<String, String>();
            param.put("account",user.getAccount());
            param.put("name", user.getName());
            String result = HttpTookit.doPost(url("/addSync"), param, "UTF-8");
            System.out.println(result);
        }
    }
}
