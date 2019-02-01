package com.ipc.code.client.ipc;

import com.ipc.code.client.env.VirtualRuntime;
import com.ipc.code.helper.ipcbus.IPCSingleton;
import com.ipc.code.server.interfaces.IAccountManager;

import android.os.RemoteException;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class VAccountManager {

    private static final VAccountManager sAM = new VAccountManager();

    private IPCSingleton<IAccountManager> singleton = new IPCSingleton<>(IAccountManager.class);

    /**
     *
     * @return
     * @hide
     */
    public static VAccountManager get() {
        return sAM;
    }

    private IAccountManager getService() {
        return singleton.get();
    }

    public String getPassword(String name) {
        try {
            return getService().getPassword(name);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
        return null;
    }

}
