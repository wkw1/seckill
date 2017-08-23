package com.wkw.exception;

/**
 * 秒杀关闭异常（时间到了，库存没了）
 * Created by 宽伟 on 2017/8/17.
 */
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
