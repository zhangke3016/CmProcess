package com.cmprocess.ipc.event;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public static synchronized void subscribe(String key, EventCallback callback) {
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
    public static synchronized void unsubscribe(String key) {
        subscribers.remove(key);
    }

    /**
     * Remove determined event listeners
     * @param callback
     */
    public static synchronized void unsubscribe(EventCallback callback) {
        for (Map.Entry<String, List<EventCallback>> entry : subscribers.entrySet()) {
            List<EventCallback> listeners = entry.getValue();
            for (EventCallback cb : listeners) {
                if (callback == cb) {
                    listeners.remove(cb);
                    break;
                }
            }
        }
    }

    public static synchronized void onEventReceive(String key, final Bundle event) {
        if (event == null){
            return;
        }
        if (key != null) {
            List<EventCallback> messageCallbacks = subscribers.get(key);
            if (messageCallbacks != null) {
                for (int i = messageCallbacks.size() - 1; i >= 0; --i) {
                    final EventCallback ec = messageCallbacks.get(i);
                    if (ec != null){
                        sHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ec.onEventCallBack(event);
                            }
                        });
                    }else {
                        messageCallbacks.remove(i);
                    }
                }
            }
        }
    }
}
