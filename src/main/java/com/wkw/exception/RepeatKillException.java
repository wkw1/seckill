package com.wkw.exception;

/**
 * �ظ���ɱ�쳣�������ڼ��쳣��
 * Created by ��ΰ on 2017/8/17.
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
