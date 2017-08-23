package com.wkw.dao;

import com.wkw.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * Created by 宽伟 on 2017/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    /**
     * 第一次测试 insertCount=1
     * 第二次测试 insertCount=0
     * 不允许重复秒杀
     * @throws Exception
     */
    @Test
    public void insertSuccessKilled() throws Exception {
        long id=1001L;
        long phone=13051883222L;
        int insertCount=successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insertCount=" +insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id=1001L;
        long phone=13051883222L;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(id,phone);

        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}