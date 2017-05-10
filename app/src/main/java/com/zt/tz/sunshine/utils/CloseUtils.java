package com.zt.tz.sunshine.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by zhangtong on 2017-05-06 22:01
 * QQ:xxxxxxxx
 */

public class CloseUtils {
    public static void closeIO(Closeable closeable){
        if(closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
