package com.wkw.dao;

import com.wkw.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ��ΰ on 2017/8/16.
 */
public interface SuccessKilledDao {
    /**
     * ���빺����ϸ���ɹ����ظ�
     * @param seckillId
     * @param userPhone
     * @return ���������
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * ����ID��ѯsuccessKilled��Я����ɱ����ʵ��
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill( @Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
