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
 * Created by ��ΰ on 2017/8/17.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5��֮�ַ��������ڻ���md5
    private final String slat="dhahfdksahifdsanfk87fkdja";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        //δ��ѯ�����
        if(seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //��ǰϵͳ��ʱ��
        Date nowTime = new Date();
        //��ǰʱ�䲻����ɱʱ����
        if(nowTime.getTime()<startTime.getTime()
                ||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),
                    startTime.getTime(),endTime.getTime());
        }
        //ת���ض��ַ����Ĺ��̣�������
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base = seckillId+"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * ʹ��ע��������񷽷����ŵ�
     * 1�������ŶӴ��Լ��һ�£���ȷ��ע���񷽷��ı�̵ķ��
     * 2����֤���񷽷���ִ��ʱ�価���̣ܶ���Ҫ���������Ļ������
     * 3���������еķ�������Ҫ���񣬱���ֻҪһ���޸Ĳ�������ֻ������
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws RepeatKillException, SeckillCloseException, SeckillException {
        if(md5==null||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite(���ݱ���д)");
        }
        //ִ����ɱ�߼�������棬��¼������Ϊ
        Date nowTime = new Date();
        try{
            int updataCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updataCount<=0){
                //û�и��µ���¼,��ɱ����
                throw new SeckillCloseException("seckill is closed����ɱ������");
            }else{
                //��¼������Ϊ�����ӹ����¼��
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,
                        userPhone);
                //Ψһ��SeckillID��userPhone
                if(insertCount==0){
                    //�ظ���ɱ
                    throw new RepeatKillException("seckill repeated");
                }
                else{
                    //��ɱ�ɹ�
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
            //���б����ڵ��쳣ת��Ϊ�����ڵ��쳣
            throw new SeckillException("seckill innner error:" + e.getMessage());
        }
    }
}
