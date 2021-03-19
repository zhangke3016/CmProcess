package com.cmprocess.ipc.server;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class ServiceCache {

    private static final Map<String, IBinder> sCache = new HashMap<>(5);
    private static final Map<String, IBinder> sEventCache = new HashMap<>(5);
    private static final Map<String, Object> sLocalCache = new HashMap<>(5);

    public static synchronized void addService(String name, IBinder service) {
        sCache.put(name, service);
    }

    public static synchronized IBinder removeService(String name) {
        return sCache.remove(name);
    }

    public static synchronized IBinder getService(String name) {
        return sCache.get(name);
    }

    public static synchronized void addLocalService(String name, Object service) {
        sLocalCache.put(name, service);
    }

    public static synchronized Object removeLocalService(String name) {
        return sLocalCache.remove(name);
    }

    public static synchronized Object getLocalService(String name) {
        return sLocalCache.get(name);
    }

    public static synchronized void addEventListener(String name, IBinder service) {
        sEventCache.put(name, service);
    }

    public static synchronized IBinder removeEventListener(String name) {
        return sEventCache.remove(name);
    }

    public static synchronized IBinder getEventListener(String name) {
        return sEventCache.get(name);
    }

    public static synchronized void sendEvent(String processName, String key,Bundle event){
        if (sEventCache.isEmpty()){
            return;
        }
        if (!TextUtils.isEmpty(processName)) {
            IBinder binder = sEventCache.get(processName);
            if (binder == null) {
                return;
            }
            onEventReceive(key, event, binder);
        } else {
            for (IBinder binder:sEventCache.values()){
                onEventReceive(key, event, binder);
            }
        }
    }

    /**
     * 处理接收事件
     */
    private static void onEventReceive(String key, Bundle event, IBinder binder) {
        IEventReceiver eventReceiver = IEventReceiver.Stub.asInterface(binder);
        try {
            eventReceiver.onEventReceive(key, event);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
