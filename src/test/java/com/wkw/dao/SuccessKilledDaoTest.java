package com.wkw.dao;

import com.wkw.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * Created by ��ΰ on 2017/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    /**
     * ��һ�β��� insertCount=1
     * �ڶ��β��� insertCount=0
     * �������ظ���ɱ
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