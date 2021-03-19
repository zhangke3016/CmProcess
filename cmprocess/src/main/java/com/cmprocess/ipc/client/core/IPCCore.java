package com.cmprocess.ipc.client.core;


import com.cmprocess.ipc.client.ipc.ServiceManagerNative;
import com.cmprocess.ipc.event.EventReceiver;
import com.cmprocess.ipc.helper.ipcbus.IPCBus;
import com.cmprocess.ipc.helper.ipcbus.IServerCache;
import com.cmprocess.ipc.helper.utils.AppUtil;
import com.cmprocess.ipc.server.ServiceCache;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public final class IPCCore {


    private static final String TAG = IPCCore.class.getSimpleName();

    private static IPCCore gCore = new IPCCore();

    private boolean isStartUp;

    private Context context;

    private IPCCore() {
    }

    public static IPCCore get() {
        return gCore;
    }

    public Context getContext() {
        return context;
    }

    public void startup(Context context) {
        if (!isStartUp) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw new IllegalStateException("VirtualCore.startup() must called in main thread.");
            }
            ServiceManagerNative.SERVICE_CP_AUTH = context.getPackageName() + "." + ServiceManagerNative.SERVICE_DEF_AUTH;
            this.context = context;
            IPCBus.initialize(new IServerCache() {
                @Override
                public void join(String serverName, IBinder binder) {
                    ServiceManagerNative.addService(serverName, binder);
                }

                @Override
                public void joinLocal(String serverName, Object object) {
                    ServiceCache.addLocalService(serverName,object);
                }

                @Override
                public void removeService(String serverName) {
                    ServiceManagerNative.removeService(serverName);
                }

                @Override
                public void removeLocalService(String serverName) {
                     ServiceCache.removeLocalService(serverName);
                }

                @Override
                public IBinder query(String serverName) {
                    return ServiceManagerNative.getService(serverName);
                }

                @Override
                public Object queryLocal(String serverName) {
                    return ServiceCache.getLocalService(serverName);
                }

                @Override
                public void post(String processName, String key,Bundle bundle) {
                    ServiceManagerNative.post(processName, key,bundle);
                }
            });
            ServiceManagerNative.addEventListener(AppUtil.getProcessName(context, Process.myPid()), EventReceiver.getInstance());
            isStartUp = true;
        }
    }

    public boolean isStartup() {
        return isStartUp;
    }
}
