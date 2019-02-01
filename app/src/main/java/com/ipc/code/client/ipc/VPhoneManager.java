package com.ipc.code.client.ipc;

import com.ipc.code.client.env.VirtualRuntime;
import com.ipc.code.helper.ipcbus.IPCSingleton;
import com.ipc.code.server.interfaces.IPhoneManager;

import android.os.RemoteException;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class VPhoneManager {

    private static final VPhoneManager sPM = new VPhoneManager();

    private IPCSingleton<IPhoneManager> singleton = new IPCSingleton<>(IPhoneManager.class);

    /**
     *
     * @return
     * @hide
     */
    public static VPhoneManager get() {
        return sPM;
    }

    private IPhoneManager getService() {
        return singleton.get();
    }

    public String getPhone() {
        try {
            return getService().getPhone();
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
        return null;
    }

}
