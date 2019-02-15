package com.cmprocess.ipc.server;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
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
    private static final Map<String, IBinder> sEventCache = new ArrayMap<>(5);
    private static final Map<String, Object> sLocalCache = new ArrayMap<>(5);

    public static void addService(String name, IBinder service) {
        sCache.put(name, service);
    }

    public static IBinder removeService(String name) {
        return sCache.remove(name);
    }

    public static IBinder getService(String name) {
        return sCache.get(name);
    }

    public static void addLocalService(String name, Object service) {
        sLocalCache.put(name, service);
    }

    public static Object removeLocalService(String name) {
        return sLocalCache.remove(name);
    }

    public static Object getLocalService(String name) {
        return sLocalCache.get(name);
    }

    public static void addEventListener(String name, IBinder service) {
        sEventCache.put(name, service);
    }

    public static IBinder removeEventListener(String name) {
        return sEventCache.remove(name);
    }

    public static IBinder getEventListener(String name) {
        return sEventCache.get(name);
    }

    public static void sendEvent(String key,Bundle event){
        if (sEventCache.isEmpty()){
            return;
        }
        for (IBinder binder:sEventCache.values()){
            IEventReceiver eventReceiver = IEventReceiver.Stub.asInterface(binder);
            try {
                eventReceiver.onEventReceive(key, event);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
