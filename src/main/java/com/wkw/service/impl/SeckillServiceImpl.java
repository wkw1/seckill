package com.wkw.service.impl;

import com.wkw.dao.SeckillDao;
import com.wkw.dao.SuccessKilledDao;
import com.wkw.dto.Exposer;
import com.wkw.dto.SeckillExecution;
import com.wkw.entity.Seckill;
import com.wkw.entity.SuccessKilled;
import com.wkw.enums.SeckillStatEnum;
import com.wkw.exception.RepeatKillException;
import com.wkw.exception.SeckillCloseException;
import com.wkw.exception.SeckillException;
import com.wkw.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by 宽伟 on 2017/8/17.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5言之字符串，用于混淆md5
    private final String slat="dhahfdksahifdsanfk87fkdja";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        //未查询到结果
        if(seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //当前系统的时间
        Date nowTime = new Date();
        //当前时间不在秒杀时间内
        if(nowTime.getTime()<startTime.getTime()
                ||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),
                    startTime.getTime(),endTime.getTime());
        }
        //转化特定字符串的过程，不可逆
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base = seckillId+"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制事务方法的优点
     * 1：开发团队达成约定一致，明确标注事务方法的编程的风格
     * 2：保证事务方法的执行时间尽可能短，不要穿插其他的缓存操作
     * 3：不是所有的方法都需要事务，比如只要一条修改操作或者只读操作
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws RepeatKillException, SeckillCloseException, SeckillException {
        if(md5==null||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite(数据被改写)");
        }
        //执行秒杀逻辑：减库存，记录购买行为
        Date nowTime = new Date();
        try{
            int updataCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updataCount<=0){
                //没有更新到记录,秒杀结束
                throw new SeckillCloseException("seckill is closed（秒杀结束）");
            }else{
                //记录购买行为（增加购买记录）
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,
                        userPhone);
                //唯一的SeckillID和userPhone
                if(insertCount==0){
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                }
                else{
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译期的异常转化为运行期的异常
            throw new SeckillException("seckill innner error:" + e.getMessage());
        }
    }
}
