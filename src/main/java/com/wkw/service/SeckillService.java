package com.wkw.service;

/**
 * Created by ��ΰ on 2017/8/17.
 */

import com.wkw.dto.Exposer;
import com.wkw.dto.SeckillExecution;
import com.wkw.entity.Seckill;
import com.wkw.exception.RepeatKillException;
import com.wkw.exception.SeckillCloseException;
import com.wkw.exception.SeckillException;

import java.util.List;

/**
 * ҵ��ӿڣ�վ�ڡ�ʹ���ߡ��ĽǶ���ƽӿ�
 * ��������(��Ҫ̫��������Ҫ̫����)
 * 1 �����������ȣ����������Ҫ�ǳ���ȷ������ע����ʵ�֣�
 * 2 ����Ҫ����
 * 3 �������ͣ�return����Ҫ����������������Ҫ�Ѻð���������쳣
 * */
public interface SeckillService {
    /**
     * ��ѯ���е���ɱ��¼
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * ��ѯ��������ɱ��¼
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * ��ɱ����ʱ�����ɱ�ӿڵ�ַ��
     * �������ϵͳʱ�����ɱʱ��
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * ִ����ɱ����
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws RepeatKillException,SeckillCloseException,SeckillException;
}
