package com.wkw.exception;

/**
 * √Î…±œ‡πÿ“Ï≥£
 * Created by øÌŒ∞ on 2017/8/17.
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
