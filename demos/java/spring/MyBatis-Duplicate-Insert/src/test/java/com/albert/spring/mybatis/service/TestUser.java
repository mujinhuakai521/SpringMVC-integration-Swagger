package com.albert.spring.mybatis.service;

import com.albert.spring.mybatis.entity.User;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationContext.xml"})
public class TestUser {

    @Autowired
    UserService userService;

    /**
     * 没有同步会插入多条
     * @throws Throwable
     */
    @Test
    @Ignore
    public void testDuplicateInsert() throws Throwable {

        User user = new User();
        user.setAccount("albert");
        user.setName("cyh");
        //Runner数组，想当于并发多少个。
        TestRunnable[] trs = new TestRunnable[10];
        for(int i=0;i<trs.length;i++){
            trs[i] = new CheckAndInsertThread(user, userService);
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 开发并发执行数组里定义的内容
        mttr.runTestRunnables();
    }

    /**
     * service加锁进行同步, 实测可以保证插入唯一
     * @throws Throwable
     */
    @Test
    public void testDuplicateInsertSync() throws Throwable {

        User user = new User();
        user.setAccount("albert");
        user.setName("cyh");

        //Runner数组，想当于并发多少个。
        TestRunnable[] trs = new TestRunnable[10];
        for(int i=0;i<trs.length;i++){
            trs[i] = new CheckAndInsertSyncThread(user, userService);
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 开发并发执行数组里定义的内容
        mttr.runTestRunnables();
    }

    private class CheckAndInsertThread extends TestRunnable{

        private User user;

        private UserService userService;

        public CheckAndInsertThread(User user, UserService userService){
            super();
            this.user = user;
            this.userService = userService;
        }

        @Override
        public void runTest() throws Throwable {
            if(userService.getUser(user.getAccount()) == null){
                userService.addUser(user);
            }
        }
    }

    private class CheckAndInsertSyncThread extends TestRunnable{

        private User user;

        private UserService userService;

        public CheckAndInsertSyncThread(User user, UserService userService){
            super();
            this.user = user;
            this.userService = userService;
        }

        @Override
        public void runTest() throws Throwable {
            userService.addUserSync(user);

        }
    }


}
