package com.wkw.service;

/**
 * Created by 宽伟 on 2017/8/17.
 */

import com.wkw.dto.Exposer;
import com.wkw.dto.SeckillExecution;
import com.wkw.entity.Seckill;
import com.wkw.exception.RepeatKillException;
import com.wkw.exception.SeckillCloseException;
import com.wkw.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在”使用者“的角度设计接口
 * 三个方面(不要太繁琐，不要太抽象)
 * 1 方法定义粒度，方法定义的要非常明确（不关注具体实现）
 * 2 参数要简练
 * 3 返回类型（return类型要清晰），返回类型要友好包括允许的异常
 * */
public interface SeckillService {
    /**
     * 查询所有的秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个的秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws RepeatKillException,SeckillCloseException,SeckillException;
}
