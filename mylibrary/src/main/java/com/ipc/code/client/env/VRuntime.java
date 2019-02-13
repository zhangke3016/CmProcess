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
public class VRuntime {

    public static boolean isArt() {
        return System.getProperty("java.vm.version").startsWith("2");
    }
}
