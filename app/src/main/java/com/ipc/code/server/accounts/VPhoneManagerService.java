package com.ipc.code.server.accounts;

import com.ipc.code.client.core.VirtualCore;
import com.ipc.code.server.interfaces.IPhoneManager;

import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class VPhoneManagerService implements IPhoneManager{

    private static final AtomicReference<VPhoneManagerService> sInstance = new AtomicReference<>();

    public static VPhoneManagerService get() {
        return sInstance.get();
    }

    public static void systemReady() {
        VPhoneManagerService service = new VPhoneManagerService();
        sInstance.set(service);
    }

    @Override
    public String getPhone() throws RemoteException {
        Log.e("TAG", "getPhone: "+ VirtualCore.get().isServerProcess());
        return "123456789";
    }
}
