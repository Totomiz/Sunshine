package com.zt.tz.sunshine.utils;

/**
 * Created by zhangtong on 2017-05-06 21:07
 * QQ:xxxxxxxx
 */

public abstract class ResultWrapper<T> implements IResult<T> {
    @Override
    public T onSuccess(T t) {
        return t;
    }

    @Override
    public void onFaild() {

    }

    @Override
    public void onRetry() {

    }
}
