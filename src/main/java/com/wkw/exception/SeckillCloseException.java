package com.wkw.exception;

/**
 * ��ɱ�ر��쳣��ʱ�䵽�ˣ����û�ˣ�
 * Created by ��ΰ on 2017/8/17.
 */
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
