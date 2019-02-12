package com.ipc.code.client.core;

import com.ipc.code.client.ipc.ServiceManagerNative;
import com.ipc.code.helper.ipcbus.IPCBus;
import com.ipc.code.helper.ipcbus.IServerCache;
import com.ipc.code.server.ServiceCache;

import android.app.ActivityManager;
import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;

import java.util.List;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public final class VirtualCore {


    private static final String TAG = VirtualCore.class.getSimpleName();

    private static final String SERVER_PROCESS_NAME = ":v";

    private static VirtualCore gCore = new VirtualCore();

    private boolean isStartUp;

    private Context context;

    private ProcessType processType;

    private VirtualCore() {
    }

    public static VirtualCore get() {
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
                    ServiceCache.addService(serverName, binder);
                }

                @Override
                public IBinder removeService(String serverName) {
                    return ServiceCache.removeService(serverName);
                }

                @Override
                public IBinder query(String serverName) {
                    return ServiceManagerNative.getService(serverName);
                }
            });
            detectProcessType();
            isStartUp = true;
        }
    }

    private void detectProcessType() {

        String processName = getProcessName(context, Process.myPid());
        if (processName.endsWith(SERVER_PROCESS_NAME)) {
            processType = ProcessType.Server;
        }
        /*else if (processName.equals(context.getPackageName())){
            processType = ProcessType.Main;
        }*/

    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public boolean isStartup() {
        return isStartUp;
    }

    /**
     * @return If the current process is the server.
     */
    public boolean isServerProcess() {
        return ProcessType.Server == processType;
    }

    /**
     * Process type
     */
    private enum ProcessType {
        /**
         * Server process
         */
        Server,
//        /**
//         * Virtual app process
//         */
//        VAppClient,
//        /**
//         * Main process
//         */
//        Main,
//        /**
//         * Child process
//         */
//        CHILD
    }
}
