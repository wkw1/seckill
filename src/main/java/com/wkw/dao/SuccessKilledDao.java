package com.wkw.dao;

import com.wkw.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 宽伟 on 2017/8/16.
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据ID查询successKilled并携带秒杀对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill( @Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
