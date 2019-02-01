package com.ipc.code.server.accounts;

import com.ipc.code.server.interfaces.IAccountManager;

import android.os.RemoteException;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class VAccountManagerService implements IAccountManager{

    private static final AtomicReference<VAccountManagerService> sInstance = new AtomicReference<>();

    public static VAccountManagerService get() {
        return sInstance.get();
    }

    public static void systemReady() {
        VAccountManagerService service = new VAccountManagerService();
        sInstance.set(service);
    }

    @Override
    public String getPassword(String name) throws RemoteException {
        return name+" :123";
    }
}
