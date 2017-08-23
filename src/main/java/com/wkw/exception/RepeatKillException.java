package com.wkw.exception;

/**
 * 重复秒杀异常（运行期间异常）
 * Created by 宽伟 on 2017/8/17.
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
