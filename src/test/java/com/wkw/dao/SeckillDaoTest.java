package com.wkw.dao;

import com.wkw.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置Spring和Junit整合，Junit 启动时加载SpringIOC容器
 *
 * Spring-test Junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit Spring配置文件
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {

    //注入dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception {
        long id=1000;
        Seckill seckill= seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /**
         1000元秒杀iPhone7
         Seckill{
         seckillId=1000,
         name='1000元秒杀iPhone7',
         number=100,
         startTime=Sun Nov 01 00:00:00 CST 2015, e
         ndTime=Mon Nov 02 00:00:00 CST 2015,
         createTime=Wed Aug 16 10:27:57 CST 2017
         */
    }

    /**
     *  List<Seckill> queryAll(int offset, int limit);
     *  Java没有保存形参的记录
     * @throws Exception
     */
    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0,100);
        for(Seckill seckill :seckills)
            System.out.println(seckill);
    }

    @Test
    public void reduceNumber() throws Exception {
        Date date = new Date();
        int updateData = seckillDao.reduceNumber(1000L,date);
        System.out.println("updateData="+updateData);
    }


}