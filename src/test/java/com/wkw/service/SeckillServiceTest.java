package com.wkw.service;

import com.wkw.dto.Exposer;
import com.wkw.dto.SeckillExecution;
import com.wkw.entity.Seckill;
import com.wkw.exception.RepeatKillException;
import com.wkw.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 宽伟 on 2017/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id=1000L;
        Seckill seckill  =seckillService.getById(id);
        logger.info("seckill={}",seckill);
        System.out.println(seckill);

    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id=1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
        /**
         * exposed=true,
         * md5='be7c3fa8897cffcf5a14720612599500',
         * seckillId=1000,
         * now=0, start=0, end=0
         */
    }

    @Test
    public void executeSeckill() throws Exception {
        long id=1000;
        long phone =13532948303L;
        String md5 = "be7c3fa8897cffcf5a14720612599500";
        try{
            SeckillExecution execution=seckillService.executeSeckill(id,phone,md5);
            logger.info("result={}",execution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage());
        }catch (SeckillCloseException e1){
            logger.error(e1.getMessage());
        }
    }

    @Test
    public void testLogic(){
        long id=1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);

        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone =13532948303L;
            String md5 = "be7c3fa8897cffcf5a14720612599500";
            try{
                SeckillExecution execution=seckillService.executeSeckill(id,phone,md5);
                logger.info("result={}",execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e1){
                logger.error(e1.getMessage());
            }
        }
        else{
            //秒杀不可进行
            logger.warn("exposer={}",exposer);
        }
    }

    /**
     * 测试exception 和Junit
     * catch的错误不会被test中断
     */
    @Test
    public void testException(){
        try{
            int a=1/0;
        }catch(Exception e) {
            logger.info(e.getMessage());
        }

    }
}