package com.wkw.exception;

/**
 * ��ɱ����쳣
 * Created by ��ΰ on 2017/8/17.
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
