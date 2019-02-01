package com.ipc.code.client.env;

import com.ipc.code.client.core.VirtualCore;

import android.os.Process;
import android.os.RemoteException;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class VirtualRuntime {

    public static <T> T crash(RemoteException e) throws RuntimeException {
        e.printStackTrace();
        if (VirtualCore.get().isVAppProcess()) {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
        throw new DeadServerException(e);
    }


    public static boolean isArt() {
        return System.getProperty("java.vm.version").startsWith("2");
    }
}
