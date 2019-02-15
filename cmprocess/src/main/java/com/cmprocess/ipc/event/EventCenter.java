package com.cmprocess.ipc.event;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zk
 * @date 创建时间：2019/2/15
 * @Description： Event registry
 * @other 修改历史：
 */
public class EventCenter {

    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    private static ConcurrentHashMap<String, List<EventCallback>> subscribers = new ConcurrentHashMap<>();

    private EventCenter() {}

    /**
     * Subscription listener
     * @param key
     * @param callback
     */
    public static void subscribe(String key, EventCallback callback) {
        List<EventCallback> eventCallbacks = subscribers.get(key);
        if (eventCallbacks == null){
            eventCallbacks = new ArrayList<>(5);
        }
        eventCallbacks.add(callback);
        subscribers.put(key, eventCallbacks);
    }

    /**
     * Remove key all event callback listeners
     * @param key
     */
    public static void unsubscribe(String key) {
        subscribers.remove(key);
    }

    /**
     * Remove determined event listeners
     * @param key
     * @param callback
     */
    public static void unsubscribe(String key,EventCallback callback) {
        List<EventCallback> eventCallbacks = subscribers.get(key);
        eventCallbacks.remove(callback);
    }

    public static void onEventReceive(String key, final Bundle event) {
        if (event == null){
            return;
        }
        if (key != null) {
            List<EventCallback> messageCallbacks = subscribers.get(key);
            if (messageCallbacks != null) {
                for (final EventCallback eventCallback:messageCallbacks){
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            eventCallback.onEventCallBack(event);
                        }
                    });
                }
            }
        }
    }
}
