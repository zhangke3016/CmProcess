package com.cmprocess.ipc;


import com.cmprocess.ipc.client.core.VirtualCore;
import com.cmprocess.ipc.client.ipc.ServiceManagerNative;
import com.cmprocess.ipc.helper.ipcbus.IPCBus;

import android.app.ActivityManager;
import android.content.Context;
import android.os.IBinder;

import java.util.Iterator;
import java.util.List;

/**
 * @author zk
 * @date 创建时间：2019/2/11
 * @Description： Cross-process communication core tool class.
 * @other 修改历史：
 */
public class VCore {

    private static final VCore V_CORE = new VCore();

    public static void init(Context context){
        if (isCanInit(context)){
            VirtualCore.get().startup(context);
        }
    }

    public static VCore getCore(){
        return V_CORE;
    }

    public static boolean isCanInit(Context base){
        return !getAppName(base).equals(base.getPackageName()+":vm");
    }

    /**
     * Register service
     * @param interfaceClass
     * @param server
     * @return
     */
    public VCore registerService(Class<?> interfaceClass, Object server){

        if (VirtualCore.get().getContext() == null){
            return this;
        }
        Object o = IPCBus.getLocalService(interfaceClass);
        IBinder service = ServiceManagerNative.getService(interfaceClass.getName());
        if (service != null && o != null){
            return this;
        }
        IPCBus.registerLocal(interfaceClass,server);
        IPCBus.register(interfaceClass,server);
        return this;
    }

    /**
     * Unregister service
     * @param interfaceClass
     * @return
     */
    public VCore unregisterService(Class<?> interfaceClass){
        IPCBus.unregisterLocal(interfaceClass);
        IPCBus.removeService(interfaceClass.getName());
        return this;
    }

    /**
     * Get service
     * @param ipcClass
     * @param <T>
     * @return
     */
    public <T> T getService(Class<T> ipcClass){
        T localService = IPCBus.getLocalService(ipcClass);
        if (localService != null){
            return localService;
        }
        return VManager.get().getService(ipcClass);
    }

    /**
     * Register local service
     * @param interfaceClass
     * @param server
     * @return
     */
    public VCore registerLocalService(Class<?> interfaceClass, Object server){
        Object o = IPCBus.getLocalService(interfaceClass);
        if (o != null){
            return this;
        }
        IPCBus.registerLocal(interfaceClass,server);
        return this;
    }

    /**
     * Unregister local service
     * @param interfaceClass
     * @return
     */
    public VCore unregisterLocalService(Class<?> interfaceClass){
        IPCBus.unregisterLocal(interfaceClass);
        return this;
    }

    /**
     * Get local service
     * @param ipcClass
     * @param <T>
     * @return
     */
    public <T> T getLocalService(Class<T> ipcClass){
        T localService = IPCBus.getLocalService(ipcClass);
        return localService;
    }

    /**
     * Get the name of the current process, generally the package name of the current app.
     *
     * @param context Current context
     * @return Return the name of the process
     */
    private static String getAppName(Context context) {
        int pid = android.os.Process.myPid(); // Returns the identifier of this process
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    return info.processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
