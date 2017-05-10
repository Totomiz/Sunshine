package com.zt.tz.sunshine.utils;

/**
 * Created by zhangtong on 2017-05-06 21:05
 * QQ:xxxxxxxx
 */

public interface IResult<T> {
    T onSuccess(T t);
    void onFaild();
    void onRetry();
}
