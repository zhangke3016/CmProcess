package com.ipc.code.server;

import android.os.IBinder;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class ServiceCache {

    private static final Map<String, IBinder> sCache = new ArrayMap<>(5);

    public static void addService(String name, IBinder service) {
        sCache.put(name, service);
    }

    public static IBinder removeService(String name) {
        return sCache.remove(name);
    }

    public static IBinder getService(String name) {
        return sCache.get(name);
    }

}
