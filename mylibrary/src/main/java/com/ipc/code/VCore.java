package com.ipc.code;

import com.ipc.code.client.core.VirtualCore;
import com.ipc.code.client.ipc.ServiceManagerNative;
import com.ipc.code.helper.ipcbus.IPCBus;
import com.ipc.code.helper.ipcbus.ServerInterface;
import com.ipc.code.helper.ipcbus.TransformBinder;

import android.app.ActivityManager;
import android.content.Context;
import android.os.IBinder;

import java.util.Iterator;
import java.util.List;

/**
 * @author zk
 * @date 创建时间：2019/2/11
 * @Description：
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
        return !getAppName(base).equals(base.getPackageName()+":v");
    }

    public VCore addService(Class<?> interfaceClass, Object server){

        if (VirtualCore.get().getContext() == null){
            return this;
        }

        Object o = IPCBus.get(interfaceClass);
        if (o != null){
            return this;
        }
        IBinder service = ServiceManagerNative.getService(interfaceClass.getName());
        if (service != null){
            return this;
        }
        ServerInterface serverInterface = new ServerInterface(interfaceClass);
        TransformBinder binder = new TransformBinder(serverInterface, server);
        ServiceManagerNative.addService(interfaceClass.getName(), binder);
        IPCBus.register(serverInterface, binder);
        return this;
    }

    public VCore removeService(Class<?> interfaceClass){
        IPCBus.removeService(interfaceClass.getName());
        ServiceManagerNative.removeService(interfaceClass.getName());
        return this;
    }

    public <T> T getService(Class<T> ipcClass){
        return VManager.get().getService(ipcClass);
    }

    /**
     * 获取当前进程的名字，一般就是当前app的包名
     *
     * @param context 当前上下文
     * @return 返回进程的名字
     */
    private static String getAppName(Context context)
    {
        int pid = android.os.Process.myPid(); // Returns the identifier of this process
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext())
        {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try
            {
                if (info.pid == pid)
                {
                    // 根据进程的信息获取当前进程的名字
                    return info.processName;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }
}
