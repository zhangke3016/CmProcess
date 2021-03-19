package com.ipc.code;

import android.os.Bundle;
import android.os.RemoteException;
import com.cmprocess.ipc.helper.utils.AppUtil;
import com.cmprocess.ipc.server.IPCCallback;

public class IPCPayManagerTest implements IPayManager {

    @Override
    public void pay(final int count, final IPCCallback callBack) {
        Bundle bundle = new Bundle();
        bundle.putString("pay", count + 100 + AppUtil.getAppName(App.getApp()));
        try {
            callBack.onSuccess(bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String pay(int count) {
        return count + 100 + "";
    }

}
